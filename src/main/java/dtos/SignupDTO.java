package dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SignupDTO {
    private Long id;

    private String name;

    private String email;

    private String password;
}
