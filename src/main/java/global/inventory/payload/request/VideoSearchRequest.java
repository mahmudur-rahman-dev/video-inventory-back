package global.inventory.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VideoSearchRequest {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean deleted;
}
