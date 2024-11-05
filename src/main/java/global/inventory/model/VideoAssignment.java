package global.inventory.model;

import global.inventory.enums.AssignmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "video_assignments", indexes = {
        @Index(name = "idx_user_video", columnList = "user_id,video_id")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoAssignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;
}


