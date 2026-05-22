package com.platform.media.controller;

import com.platform.common.dto.ApiResponse;
import com.platform.common.util.Constants;
import com.platform.media.model.MediaAsset;
import com.platform.media.service.MediaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<MediaAsset>> upload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        
        // In a real gateway setup, this ID comes from the X-Auth-User-Id header
        String userIdHeader = request.getHeader(Constants.USER_ID_HEADER);
        Long userId = (userIdHeader != null) ? Long.parseLong(userIdHeader) : 1L; // Fallback for dev

        MediaAsset asset = mediaService.uploadMedia(file, userId);
        return ResponseEntity.ok(ApiResponse.success("Media uploaded and processing started", asset));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MediaAsset>> getMedia(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Media retrieved", mediaService.getMedia(id)));
    }

    @GetMapping("/user/all")
    public ResponseEntity<ApiResponse<List<MediaAsset>>> getUserMedia(HttpServletRequest request) {
        String userIdHeader = request.getHeader(Constants.USER_ID_HEADER);
        Long userId = (userIdHeader != null) ? Long.parseLong(userIdHeader) : 1L;

        return ResponseEntity.ok(ApiResponse.success("User media retrieved", mediaService.getUserMedia(userId)));
    }
}
