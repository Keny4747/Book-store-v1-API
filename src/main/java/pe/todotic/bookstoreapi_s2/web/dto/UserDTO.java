package pe.todotic.bookstoreapi_s2.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @NotNull
    @Size(min=5)
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private User.Role role;
}
