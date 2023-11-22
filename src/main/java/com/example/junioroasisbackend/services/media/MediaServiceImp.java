package com.example.junioroasisbackend.services.media;

import com.example.junioroasisbackend.entities.Media;
import com.example.junioroasisbackend.repositories.CommentRepository;
import com.example.junioroasisbackend.repositories.MediaRepository;
import com.example.junioroasisbackend.repositories.PostRepository;
import com.example.junioroasisbackend.repositories.UserRepository;
import com.example.junioroasisbackend.utils.enums.Mediable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaServiceImp  implements  MediaService{


    public final static String UPLOAD_DIRECTORY = System.getProperty("user.home") + "/Desktop/junior-oasis-storage";


    private final MediaRepository mediaRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Media storeMedia(MultipartFile multipartFile, Long entityID , Mediable entityType) throws Exception {
        if (this.isEntityExist(entityID, entityType)) {
            String fileName = entityType.toString() + "-" + entityID.toString() + "-" + multipartFile.getOriginalFilename();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
            Files.write(fileNameAndPath, multipartFile.getBytes());
            return mediaRepository.save(this.multipartFileToMedia(multipartFile , entityID , entityType));
        }

            throw new Exception("Entity not found");

    }


    public List<Media> storeListMedia(List<MultipartFile> list, Long entityID , Mediable entityType)  {

        if (this.isEntityExist(entityID, entityType)) {
            return mediaRepository.saveAll(list.stream().map((file) -> {
                String fileName = entityType.toString() + "-" + entityID.toString() + "-" + file.getOriginalFilename();
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
                try {
                    Files.write(fileNameAndPath, file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return this.multipartFileToMedia(file , entityID , entityType);
            }).collect(Collectors.toList()));
        }
        return null;
    }

    public  Media storeSingltonMedia(MultipartFile multipartFile, Long entityID , Mediable entityType) throws Exception {
        this.deleteAllMediaEntity(entityType, entityID);
        return this.storeMedia(multipartFile , entityID , entityType);


    }


    public void deleteMediaByID(Long ID){

        mediaRepository.findById(ID).ifPresent((media) -> {
            (new File(UPLOAD_DIRECTORY + "/" + media.getEntityType() + "-" + media.getEntityID() + "-" + media.getOriginalName())).delete();
            mediaRepository.delete(media);
        } );

    }

    public void  deleteAllMediaEntity(Mediable entityType , Long entityID){
        Collection<Media> allMedia = mediaRepository.findByTypeAndID(entityType.toString() , entityID);

        allMedia.forEach((media)-> {
                (new File(UPLOAD_DIRECTORY + "/" + entityType.toString() + "-" + entityID + "-" + media.getOriginalName())).delete();
        } );
        mediaRepository.deleteAll(allMedia);

    }


    public boolean isEntityExist(Long entityID , Mediable entityType){
        JpaRepository repository = null ;
        switch (entityType){
            case POST:
                repository = postRepository;
                break;
            case COMMENT:
                repository = commentRepository;
                break;
            case USER:
                repository = userRepository;
                break;
        }

        return repository != null && repository.existsById(entityID);

    }




    public Media multipartFileToMedia(MultipartFile multipartFile, Long entityID , Mediable entityType){
        Media media = new Media();
        media.setContentType(multipartFile.getContentType());
        media.setOriginalName(multipartFile.getOriginalFilename());
        media.setEntityType(entityType.toString());
        media.setEntityID(entityID);
       // throw new RuntimeException(multipartFile.getOriginalFilename() + "--" + multipartFile.getContentType() + "--" + entityID + "--" + entityType);
        return media;

    }

}
