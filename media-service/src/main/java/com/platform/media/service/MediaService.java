package com.platform.media.service;

import com.platform.common.exception.ResourceNotFoundException;
import com.platform.media.model.MediaAsset;
import com.platform.media.repository.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final StorageService storageService;
    private final ProcessingService processingService;

    public MediaService(MediaRepository mediaRepository, 
                        StorageService storageService, 
                        ProcessingService processingService) {
        this.mediaRepository = mediaRepository;
        this.storageService = storageService;
        this.processingService = processingService;
    }

    public MediaAsset uploadMedia(MultipartFile file, Long ownerId) {
        String originalPath = storageService.store(file);

        MediaAsset asset = MediaAsset.builder()
                .fileName(file.getOriginalFilename())
                .originalPath(originalPath)
                .contentType(file.getContentType())
                .size(file.getSize())
                .ownerId(ownerId)
                .uploadDate(LocalDateTime.now())
                .build();

        MediaAsset savedAsset = mediaRepository.save(asset);
        
        // Trigger asynchronous processing
        processingService.processMedia(savedAsset.getId());

        return savedAsset;
    }

    public MediaAsset getMedia(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found"));
    }

    public List<MediaAsset> getUserMedia(Long ownerId) {
        return mediaRepository.findByOwnerId(ownerId);
    }
}
