package global.inventory.payload.request;

import global.inventory.enums.ActivityAction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityLogFilterRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ActivityAction action;
    private Long userId;
    private Long videoId;
}
