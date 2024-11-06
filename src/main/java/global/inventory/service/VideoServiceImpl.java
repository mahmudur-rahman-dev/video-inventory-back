package global.inventory.service;

import global.inventory.enums.ActivityAction;
import global.inventory.model.User;
import global.inventory.model.Video;
import global.inventory.model.VideoAssignment;
import global.inventory.payload.request.VideoUpdateRequest;
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
    private final ActivityLogService activityLogService;

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
        // Verify video exists and not deleted
        Video video = videoRepository.findById(videoId)
                .filter(v -> !v.isDeleted())
                .orElseThrow(() -> new RuntimeException("Video not found or deleted"));

        // Verify user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if assignment already exists
        if (videoAssignmentService.existsByVideoIdAndUserId(videoId, userId)) {
            throw new RuntimeException("Video already assigned to user");
        }

        // Create new assignment
        VideoAssignment assignment = VideoAssignment.builder()
                .user(user)
                .video(video)
                .assignedAt(LocalDateTime.now())
                .build();

        // Save assignment
        VideoAssignment savedAssignment = videoAssignmentService.save(assignment);

        // Log activity
        activityLogService.logActivity(
                UtilService.getRequesterUserIdFromSecurityContext(),
                videoId,
                ActivityAction.ASSIGNED
        );

        return savedAssignment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Video> getUserVideos(Long userId) {
        // Verify user exists
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        return videoRepository.findByVideoAssignments_User_Id(userId)
                .stream()
                .filter(video -> !video.isDeleted())
                .peek(video -> video.setVideoUrl(getVideoPublicUrl(video)))
                .toList();
    }

    @Override
    public Page<Video> getAllVideosForAdmin(Pageable pageable) {
        return videoRepository.findAllVideosForAdmin(pageable);
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        Video video = getVideoById(id);
        video.setDeleted(true);
        videoRepository.save(video);

        activityLogService.logActivity(
                UtilService.getRequesterUserIdFromSecurityContext(),
                id,
                ActivityAction.DELETED
        );
    }

    @Override
    public Page<VideoAssignment> getAllAssignedVideos(Pageable pageable) {
        return videoAssignmentService.getAllAssignments(pageable);
    }

    @Override
    public Video getVideoWithFullUrl(Long id) {
        if(!UtilService.isAdminUser()) {
            if(!videoAssignmentService.existsByVideoIdAndUserId(id,
                    UtilService.getRequesterUserIdFromSecurityContext())) {
                throw new RuntimeException("Video not found or access denied");
            }
        }

        Video video = getVideoById(id);
//        video.setVideoUrl(getVideoPublicUrl(video));

        if (!UtilService.isAdminUser()) {
            activityLogService.logActivity(
                    UtilService.getRequesterUserIdFromSecurityContext(),
                    id,
                    ActivityAction.VIEWED
            );
        }

        return video;
    }

    @Override
    @Transactional
    public Video updateVideo(Long id, VideoUpdateRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        if (request.getTitle() != null) {
            video.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            video.setDescription(request.getDescription());
        }

        return videoRepository.save(video);
    }

    @Override
    public boolean removeAssignment(Long videoId) {
        VideoAssignment assignment = videoAssignmentService.getById(videoId);
        videoAssignmentService.delete(assignment.getId());
        return true;
    }

    private Video getVideoById(Long id) {
        return videoRepository.findById(id)
                .filter(video -> !video.isDeleted())
                .orElseThrow(() -> new RuntimeException("Video not found or deleted"));
    }

    public String getVideoPublicUrl(Video video) {
        return videoStorageService.generatePublicUrl(video.getVideoUrl());
    }
}
