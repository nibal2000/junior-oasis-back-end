package com.example.junioroasisbackend.controllers;

import com.example.junioroasisbackend.entities.Media;
import com.example.junioroasisbackend.repositories.MediaRepository;
import com.example.junioroasisbackend.services.media.MediaService;
import com.example.junioroasisbackend.services.media.MediaServiceImp;
import com.example.junioroasisbackend.utils.enums.Mediable;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final MediaService mediaService;

    private final MediaRepository mediaRepository;

    @PostMapping("/media/{entityType}/{entityID}")
    public ResponseEntity<String> uploadFile(@RequestParam("media") MultipartFile multipartFile,
                                             @PathVariable Mediable entityType,
                                             @PathVariable Long entityID,
                                             @RequestParam(required = false ) boolean isSingleton){
        try {
            if (isSingleton)
                mediaService.storeSingltonMedia(multipartFile, entityID , entityType);
            else
                mediaService.storeMedia(multipartFile, entityID , entityType);
            return ResponseEntity.ok("Image stored successfully" );
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


    @GetMapping("/media/{mediaID}")
    @ResponseBody
    public ResponseEntity<?> getImageDynamicType(@PathVariable("mediaID") Long mediaID) throws FileNotFoundException {

        Optional<Media> mediaOptional = mediaRepository.findById(mediaID);

        if (mediaOptional.isPresent()){
            String fileName = mediaOptional.get().getEntityType() + "-" + mediaOptional.get().getEntityID() + "-" + mediaOptional.get().getOriginalName();

            if (mediaOptional.get().getContentType() != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.valueOf(mediaOptional.get().getContentType()))
                        .body(new InputStreamResource(new FileInputStream(new File(MediaServiceImp.UPLOAD_DIRECTORY + "/" + fileName))));
            }else {
                mediaRepository.deleteById(mediaID);
            }

        }

        return  ResponseEntity.notFound().build();

    }




}
