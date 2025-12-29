package com.sm.petwellnessplus.services.photo;

import com.sm.petwellnessplus.models.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface IPhotoService {
    Photo savePhoto(MultipartFile file, Long userId) throws IOException, SQLException;
    Optional<Photo> getPhotoById(Long id);
    void deletePhoto(Long id);
    Photo updatePhoto(Long id, byte[] imageData) throws SQLException;
    byte[] getImageDate(Long id) throws SQLException;
}
