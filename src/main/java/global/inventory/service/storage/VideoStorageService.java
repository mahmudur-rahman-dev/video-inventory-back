package global.inventory.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface VideoStorageService {
    /**
     * Store a video file and return its relative storage path
     */
    String store(MultipartFile file);

    /**
     * Load a video file as a Resource
     */
    Resource loadAsResource(String storedPath);

    /**
     * Delete a video file and its empty parent directories
     */
    void delete(String storedPath);

    /**
     * Generate a public URL for accessing the video
     */
    String generatePublicUrl(String storedPath);
}