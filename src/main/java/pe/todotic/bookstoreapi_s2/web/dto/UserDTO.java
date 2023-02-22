package pe.todotic.bookstoreapi_s2.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pe.todotic.bookstoreapi_s2.model.User;
@Data
public class UserDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String fullName;

    @NotNull
    private String email;

    @NotNull
    @Size(min=5)
    private String password;

    @NotNull
    private User.Role role;
}
