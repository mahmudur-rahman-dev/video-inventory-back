package global.inventory.service;

import global.inventory.enums.ActivityAction;
import global.inventory.model.ActivityLog;

import java.util.List;

public interface ActivityLogService {
    ActivityLog logActivity(Long userId, Long videoId, ActivityAction action);
    List<ActivityLog> getVideoActivityLogs(Long videoId);
    List<ActivityLog> getUserActivityLogs(Long userId);
    List<ActivityLog> getAllActivityLogs();
}
