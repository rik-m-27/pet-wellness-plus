package com.sm.petwellnessplus.services.photo;

import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.models.Photo;
import com.sm.petwellnessplus.models.User;
import com.sm.petwellnessplus.repositories.PhotoRepository;
import com.sm.petwellnessplus.repositories.UserRepository;
import com.sm.petwellnessplus.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoService implements IPhotoService{

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Override
    public Photo savePhoto(MultipartFile file, Long userId) throws IOException, SQLException {
        Optional<User> theUser = userRepository.findById(userId);
        Photo photo = new Photo();
        if(file != null && !file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            photo.setImage(photoBlob);
            photo.setFileType(file.getContentType());
        }
        Photo savedPhoto = photoRepository.save(photo);
        theUser.ifPresent(user -> {user.setPhoto(savedPhoto);});
        userRepository.save(theUser.get());
        return savedPhoto;
    }

    @Override
    public Optional<Photo> getPhotoById(Long id) {
        return photoRepository.findById(id);
    }

    @Override
    public void deletePhoto(Long id) {
        photoRepository.findById(id).ifPresentOrElse(photoRepository::delete,()->{
            throw new ResourceNotFoundException(FeedBackMessage.PHOTO_NOT_FOUND);
        });
    }

    @Override
    public Photo updatePhoto(Long id, byte[] imageData) throws SQLException {
        Optional<Photo> thePhoto = getPhotoById(id);
        if(thePhoto.isPresent()){
            Photo photo = thePhoto.get();
            Blob photoBlob = new SerialBlob(imageData);
            photo.setImage(photoBlob);
            return photoRepository.save(photo);
        }
        throw new ResourceNotFoundException(FeedBackMessage.PHOTO_NOT_FOUND);
    }

    @Override
    public byte[] getImageDate(Long id) throws SQLException {
        Optional<Photo> thePhoto = getPhotoById(id);
        if(thePhoto.isPresent()){
            Blob photoBlob = thePhoto.get().getImage();
            int blobLength = (int)photoBlob.length();
            return new byte[blobLength];
        }
        return null;
    }
}
