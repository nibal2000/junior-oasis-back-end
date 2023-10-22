package com.example.junioroasisbackend.dtos.responses.users;


import com.example.junioroasisbackend.entities.User;
import lombok.Data;

@Data
public class OwnerResponseDTO {

    private  Long id;

    private String userName;

    public static OwnerResponseDTO mapToDto(User user){
        OwnerResponseDTO ownerDTO = new OwnerResponseDTO();
        ownerDTO.setId(user.getId());
        ownerDTO.setUserName(user.getName());
        return ownerDTO;
    }
}
