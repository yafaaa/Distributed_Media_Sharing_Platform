package com.platform.media.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.platform.media.model.MediaAsset;

public interface MediaRepository extends JpaRepository<MediaAsset, Long> {
    List<MediaAsset> findByOwnerId(Long ownerId);
}
