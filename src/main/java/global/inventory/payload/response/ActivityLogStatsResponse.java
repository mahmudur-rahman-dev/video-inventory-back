package global.inventory.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ActivityLogStatsResponse {
    private long totalViews;
    private long uniqueUsers;
    private Map<String, Long> actionCounts;
}
