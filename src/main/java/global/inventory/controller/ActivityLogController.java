package global.inventory.controller;

import global.inventory.mapper.ActivityLogMapper;
import global.inventory.model.ActivityLog;
import global.inventory.payload.response.ActivityLogResponse;
import global.inventory.payload.response.generic.InventoryResponse;
import global.inventory.payload.response.generic.PageInfo;
import global.inventory.service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<InventoryResponse<List<ActivityLogResponse>>> getActivityLogs(
            @PageableDefault Pageable pageable
    ) {
        var logs = activityLogService.getAllActivityLogs(pageable);
        return ResponseEntity.ok(new InventoryResponse<>(
                ActivityLogMapper.INSTANCE.activityLogListToDtoList(logs.getContent()),
                PageInfo.of(logs)
        ));
    }

    @GetMapping("/video/{videoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponse<List<ActivityLogResponse>>> getVideoActivityLogs(
            @PathVariable Long videoId,
            @PageableDefault Pageable pageable
    ) {
        var logs = activityLogService.getVideoActivityLogs(videoId, pageable);
        return ResponseEntity.ok(new InventoryResponse<>(
                ActivityLogMapper.INSTANCE.activityLogListToDtoList(logs.getContent()),
                PageInfo.of(logs)
        ));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponse<List<ActivityLogResponse>>> getUserActivityLogs(
            @PathVariable Long userId,
            @PageableDefault Pageable pageable
    ) {
        var logs = activityLogService.getUserActivityLogs(userId, pageable);
        return ResponseEntity.ok(new InventoryResponse<>(
                ActivityLogMapper.INSTANCE.activityLogListToDtoList(logs.getContent()),
                PageInfo.of(logs)
        ));
    }
}
