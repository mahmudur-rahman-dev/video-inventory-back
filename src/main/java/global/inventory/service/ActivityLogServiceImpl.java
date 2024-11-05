package global.inventory.service;

import global.inventory.enums.ActivityAction;
import global.inventory.exception.ResourceNotFoundException;
import global.inventory.model.ActivityLog;
import global.inventory.model.User;
import global.inventory.model.Video;
import global.inventory.repository.ActivityLogRepository;
import global.inventory.repository.UserRepository;
import global.inventory.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityLogServiceImpl implements ActivityLogService {
    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    public ActivityLog logActivity(Long userId, Long videoId, ActivityAction action) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));


//        if (video.getAssignedToUser() == null || !video.getAssignedToUser().getId().equals(userId)) {
//            throw new AccessDeniedException("User does not have access to this video");
//        }

        ActivityLog log = ActivityLog.builder()
                .user(user)
                .video(video)
                .action(action)
                .timestamp(LocalDateTime.now())
                .details("User watched video: " + video.getTitle())
                .build();

        return activityLogRepository.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityLog> getVideoActivityLogs(Long videoId) {
        return activityLogRepository.findByVideo_Id(videoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityLog> getUserActivityLogs(Long userId) {
        return activityLogRepository.findByUser_Id(userId);
    }

    @Override
    public List<ActivityLog> getAllActivityLogs() {
        return new ArrayList<>();
    }
}

