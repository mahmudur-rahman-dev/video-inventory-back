package global.inventory.payload.response;

import global.inventory.enums.ActivityAction;
import global.inventory.model.ActivityLog;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityLogResponse {
    private Long id;
    private UserResponse user;
    private VideoResponse video;
    private ActivityAction action;
    private LocalDateTime timestamp;
    private String details;

    public static ActivityLogResponse from(ActivityLog log) {
        return ActivityLogResponse.builder()
                .id(log.getId())
//                .user(UserResponse.minimal(log.getUser()))
//                .video(VideoResponse.minimal(log.getVideo()))
                .action(log.getAction())
                .timestamp(log.getTimestamp())
                .details(log.getDetails())
                .build();
    }
}
