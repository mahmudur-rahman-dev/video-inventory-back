package global.inventory.repository;

import global.inventory.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("SELECT v FROM Video v order by v.createdAt desc")
    Page<Video> findAllVideosForAdmin(Pageable pageable);

//    List<Video> findByAssignedToUser_Id(Long userId);

//    @Query("SELECT v FROM Video v LEFT JOIN FETCH v.assignedToUser WHERE v.id = :id")
//    Optional<Video> findByIdWithUser(Long id);
}