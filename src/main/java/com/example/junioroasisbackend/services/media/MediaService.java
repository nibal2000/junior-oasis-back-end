package com.example.junioroasisbackend.services.media;

import com.example.junioroasisbackend.entities.Media;
import com.example.junioroasisbackend.utils.enums.Mediable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    public Media storeMedia(MultipartFile multipartFile, Long entityID , Mediable entityType) throws Exception ;
    public  Media storeSingltonMedia(MultipartFile multipartFile, Long entityID , Mediable entityType) throws Exception;
    public void deleteMediaByID(Long ID);
    public List<Media> storeListMedia(List<MultipartFile> list, Long entityID , Mediable entityType) throws Exception;
    public void  deleteAllMediaEntity(Mediable entityType , Long entityID);
}
