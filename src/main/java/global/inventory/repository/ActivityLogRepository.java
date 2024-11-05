package global.inventory.repository;

import global.inventory.model.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // Non-paginated queries
    List<ActivityLog> findAllByOrderByTimestampDesc();
    List<ActivityLog> findByVideo_IdOrderByTimestampDesc(Long videoId);
    List<ActivityLog> findByUser_IdOrderByTimestampDesc(Long userId);

    // Paginated queries
    Page<ActivityLog> findAllByOrderByTimestampDesc(Pageable pageable);
    Page<ActivityLog> findByVideo_IdOrderByTimestampDesc(Long videoId, Pageable pageable);
    Page<ActivityLog> findByUser_IdOrderByTimestampDesc(Long userId, Pageable pageable);
}
