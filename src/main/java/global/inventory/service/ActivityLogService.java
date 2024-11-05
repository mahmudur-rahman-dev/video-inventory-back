package global.inventory.service;

import global.inventory.enums.ActivityAction;
import global.inventory.model.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityLogService {
    // Core logging functionality
    ActivityLog logActivity(Long userId, Long videoId, ActivityAction action);

    // Admin viewing capabilities
    List<ActivityLog> getAllActivityLogs();
    List<ActivityLog> getVideoActivityLogs(Long videoId);
    List<ActivityLog> getUserActivityLogs(Long userId);

    // Paginated versions for better performance
    Page<ActivityLog> getAllActivityLogs(Pageable pageable);
    Page<ActivityLog> getVideoActivityLogs(Long videoId, Pageable pageable);
    Page<ActivityLog> getUserActivityLogs(Long userId, Pageable pageable);
}
