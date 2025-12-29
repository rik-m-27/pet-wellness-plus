package com.sm.petwellnessplus.controllers;

import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.models.Photo;
import com.sm.petwellnessplus.response.ApiResponse;
import com.sm.petwellnessplus.services.photo.IPhotoService;
import com.sm.petwellnessplus.utils.FeedBackMessage;
import com.sm.petwellnessplus.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PhotoController {
    private final IPhotoService photoService;

    @PostMapping(UrlMapping.UPLOAD_PHOTO)
    public ResponseEntity<ApiResponse> uploadPhoto(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("userId") Long userId){

        try {
            Photo thePhoto = photoService.savePhoto(file,userId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(FeedBackMessage.PHOTO_UPLOAD_SUCCESS, null));
        } catch (SQLException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(FeedBackMessage.SERVER_ERROR, null));
        }

    }

    @PutMapping(UrlMapping.UPDATE_PHOTO)
    public ResponseEntity<ApiResponse> updatePhoto(Long photoId, byte[] photoBytes){
        try{
            Photo photo = photoService.updatePhoto(photoId, photoBytes);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(FeedBackMessage.PHOTO_UPDATE_SUCCESS, null));
        }catch (ResourceNotFoundException | SQLException se){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(FeedBackMessage.PHOTO_NOT_FOUND, null));
        }
    }


}
