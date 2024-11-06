package global.inventory.service;

import global.inventory.model.Video;
import global.inventory.model.VideoAssignment;
import global.inventory.payload.request.VideoUpdateRequest;
import global.inventory.payload.request.VideoUploadRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoService {
    Video uploadVideo(VideoUploadRequest request);

    VideoAssignment assignVideoToUser(Long videoId, Long userId);

    List<Video> getUserVideos(Long userId);

    Page<Video> getAllVideosForAdmin(Pageable pageable);

    void deleteVideo(Long videoId);

    Page<VideoAssignment> getAllAssignedVideos(Pageable pageable);

    String getVideoPublicUrl(Video video);

    Video getVideoWithFullUrl(Long id);

    Video updateVideo(Long id, VideoUpdateRequest request);

    boolean removeAssignment(Long videoId);
}