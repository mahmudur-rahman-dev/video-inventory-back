package global.inventory.repository;


import global.inventory.model.VideoAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoAssignmentRepository extends JpaRepository<VideoAssignment, Long> {
    Boolean existsByVideoIdAndUserId(Long id, Long requesterUserIdFromSecurityContext);
}
