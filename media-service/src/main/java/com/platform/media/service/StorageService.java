package com.platform.media.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface StorageService {
    void init();
    String store(MultipartFile file);
    Path load(String filename);
    Path getOriginalPath(String fileName);
    Path getProcessedPath(String fileName);
}
