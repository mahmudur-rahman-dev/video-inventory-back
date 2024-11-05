package global.inventory.repository;

import global.inventory.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByUser_Id(Long userId);
    List<ActivityLog> findByVideo_Id(Long videoId);

    @Query("SELECT al FROM ActivityLog al WHERE al.user.id = :userId AND al.video.id = :videoId")
    List<ActivityLog> findByUserIdAndVideoId(Long userId, Long videoId);
}
