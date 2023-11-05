package com.example.junioroasisbackend.dtos.requests;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class PostRequestDTO {

    @NotBlank
    @Size(max = 1024, min= 8)
    private  String title ;

    @NotBlank
    @Size(max = 1024, min= 8)
    private  String body;

    @Size( max = 20)
    private List<String> tags;

}
