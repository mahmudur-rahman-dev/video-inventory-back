package global.inventory.service;

import global.inventory.enums.AssignmentStatus;
import global.inventory.model.User;
import global.inventory.model.Video;
import global.inventory.model.VideoAssignment;
import global.inventory.payload.request.VideoUploadRequest;
import global.inventory.repository.ActivityLogRepository;
import global.inventory.repository.UserRepository;
import global.inventory.repository.VideoRepository;
import global.inventory.service.storage.VideoStorageService;
import global.inventory.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final ActivityLogRepository activityLogRepository;
    private final VideoStorageService videoStorageService;
    private final VideoAssignmentService videoAssignmentService;

    @Override
    @Transactional
    public Video uploadVideo(VideoUploadRequest request) {
        String storedPath = videoStorageService.store(request.getFile());

        Video video = Video.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .videoUrl(storedPath)
                .build();

        return videoRepository.save(video);
    }

    @Override
    public VideoAssignment assignVideoToUser(Long videoId, Long userId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        VideoAssignment assignment = VideoAssignment.builder()
                .user(user)
                .video(video)
                .status(AssignmentStatus.ASSIGNED)
                .assignedAt(LocalDateTime.now())
                .build();
        return videoAssignmentService.save(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Video> getUserVideos(Long userId) {

        return new ArrayList<>();
    }

    @Override
    public Page<Video> getAllVideosForAdmin(Pageable pageable) {
        return videoRepository.findAllVideosForAdmin(pageable);
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        Video video = getVideoById(id);
        videoStorageService.delete(video.getVideoUrl());
        videoRepository.delete(video);
    }

    private Video getVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }

    public String getVideoPublicUrl(Video video) {
        return videoStorageService.generatePublicUrl(video.getVideoUrl());
    }

    @Override
    public Video getVideoWithFullUrl(Long id) {
        if(!UtilService.isAdminUser()) {
            if(!videoAssignmentService.existsByVideoIdAndUserId(id, UtilService.getRequesterUserIdFromSecurityContext())) {
                throw new RuntimeException("Video not found");
            }
        }

        Video video = getVideoById(id);
        video.setVideoUrl(getVideoPublicUrl(video));
        return video;
    }

    @Override
    public Page<VideoAssignment> getAllAssignedVideos(Pageable pageable) {
        return videoAssignmentService.getAllAssignments(pageable);
    }
}
