package global.inventory.payload.request;

import global.inventory.enums.AssignmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserVideoResponse {
    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private AssignmentStatus status;
    private LocalDateTime assignedAt;
    private LocalDateTime lastViewed;
}
