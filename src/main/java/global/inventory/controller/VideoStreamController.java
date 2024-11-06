package global.inventory.controller;

import global.inventory.service.storage.VideoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class VideoStreamController {
    private final VideoStorageService videoStorageService;

    @GetMapping("/**")
    public void streamVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI().substring("/uploads/".length());
        Resource video = videoStorageService.loadAsResource(path);

        String contentType = request.getServletContext().getMimeType(video.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + video.getFilename() + "\"");
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");

        try (InputStream in = video.getInputStream();
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}