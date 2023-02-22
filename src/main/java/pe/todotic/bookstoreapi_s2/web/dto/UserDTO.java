package pe.todotic.bookstoreapi_s2.web.dto;

import lombok.Data;
import pe.todotic.bookstoreapi_s2.model.User;
@Data
public class UserDTO {
    
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String password;
    private User.Role role;
}
