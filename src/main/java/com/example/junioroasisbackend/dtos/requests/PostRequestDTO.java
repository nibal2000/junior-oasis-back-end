package com.example.junioroasisbackend.dtos.requests;


import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.entities.User;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostRequestDTO {

    @NotNull
    @Min(3)
    @Max(256)
    private  String title ;

    @NotNull
    @Min(8)
    @Max(1024)
    private  String body;


    @Size( max = 20)
    private List<String> tags;


}
