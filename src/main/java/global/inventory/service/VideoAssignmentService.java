package global.inventory.service;

import global.inventory.model.Video;
import global.inventory.model.VideoAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoAssignmentService {
    void assignVideoToUser(Long videoId, Long userId, Long adminId);
    Page<VideoAssignment> getAssignedVideosForUser(Long userId, Pageable pageable);
    void deleteAssignment(Long assignmentId, Long adminId);

    Page<VideoAssignment> getAllAssignments(Pageable pageable);

    VideoAssignment save(VideoAssignment videoAssignment);

    Boolean existsByVideoIdAndUserId(Long id, Long requesterUserIdFromSecurityContext);
}
