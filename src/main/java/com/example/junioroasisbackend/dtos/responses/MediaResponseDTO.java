package com.example.junioroasisbackend.dtos.responses;


import com.example.junioroasisbackend.entities.Media;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MediaResponseDTO {

    private Long id;

    private String uri;

    public static MediaResponseDTO mapToDTO(Media media) {
        if (media != null ){
            MediaResponseDTO mediaResponseDTO = new MediaResponseDTO();
            mediaResponseDTO.setId(media.getId());
            mediaResponseDTO.setUri("/media/" + media.getId().toString() );
            return mediaResponseDTO;
        }
        return null ;
    }


    public static List<MediaResponseDTO> mapListToDTO(List<Media> list){
        if (list != null)
            return  list.stream().map(MediaResponseDTO::mapToDTO).collect(Collectors.toList());
        return null ;
    }

}
