package global.inventory.model;

import global.inventory.enums.ActivityAction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "activity_logs", indexes = {
        @Index(name = "idx_user_video", columnList = "user_id,video_id"),
        @Index(name = "idx_action_timestamp", columnList = "action,timestamp"),
        @Index(name = "idx_user_timestamp", columnList = "user_id,timestamp")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @NotNull
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ActivityAction action;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 500)
    private String details;
}