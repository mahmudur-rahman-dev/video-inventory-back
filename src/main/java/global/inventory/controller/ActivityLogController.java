package global.inventory.controller;

import global.inventory.model.ActivityLog;
import global.inventory.payload.response.ActivityLogResponse;
import global.inventory.payload.response.generic.InventoryResponse;
import global.inventory.service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity-logs")
@RequiredArgsConstructor
public class ActivityLogController {
    private final ActivityLogService activityLogService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponse<List<ActivityLogResponse>>> getActivityLogs() {
        List<ActivityLog> logs = activityLogService.getAllActivityLogs();
        return ResponseEntity.ok(new InventoryResponse<>(
                logs.stream().map(ActivityLogResponse::from).toList()
        ));
    }

    @GetMapping("/video/{videoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponse<List<ActivityLogResponse>>> getVideoActivityLogs(
            @PathVariable Long videoId
    ) {
        List<ActivityLog> logs = activityLogService.getVideoActivityLogs(videoId);
        return ResponseEntity.ok(new InventoryResponse<>(
                logs.stream().map(ActivityLogResponse::from).toList()
        ));
    }
}
