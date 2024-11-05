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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        ActivityLog log = ActivityLog.builder()
                .user(user)
                .video(video)
                .action(action)
                .timestamp(LocalDateTime.now())
                .details(generateActivityDetails(action, video.getTitle()))
                .build();

        return activityLogRepository.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAllByOrderByTimestampDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityLog> getVideoActivityLogs(Long videoId) {
        return activityLogRepository.findByVideo_IdOrderByTimestampDesc(videoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityLog> getUserActivityLogs(Long userId) {
        return activityLogRepository.findByUser_IdOrderByTimestampDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLog> getAllActivityLogs(Pageable pageable) {
        return activityLogRepository.findAllByOrderByTimestampDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLog> getVideoActivityLogs(Long videoId, Pageable pageable) {
        return activityLogRepository.findByVideo_IdOrderByTimestampDesc(videoId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLog> getUserActivityLogs(Long userId, Pageable pageable) {
        return activityLogRepository.findByUser_IdOrderByTimestampDesc(userId, pageable);
    }

    private String generateActivityDetails(ActivityAction action, String videoTitle) {
        return switch (action) {
            case VIEWED -> String.format("User viewed video: %s", videoTitle);
            case UPDATED -> String.format("Admin updated video: %s", videoTitle);
            case ASSIGNED -> String.format("Video assigned: %s", videoTitle);
            case DELETED -> String.format("Video deleted: %s", videoTitle);
        };
    }
}

