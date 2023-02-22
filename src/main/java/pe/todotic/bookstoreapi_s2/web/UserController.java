package pe.todotic.bookstoreapi_s2.web;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import pe.todotic.bookstoreapi_s2.model.User;
import pe.todotic.bookstoreapi_s2.repository.UserRepository;
import pe.todotic.bookstoreapi_s2.web.dto.UserDTO;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    User get(@PathVariable Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("/search")
    List<User> searchByName(@RequestParam("q") String searchName) {
        return userRepository.findUser(searchName.toLowerCase());
    }

    @PostMapping
    User create(@RequestBody UserDTO userDTO) {
        User user = new ModelMapper().map(userDTO, User.class);
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    User update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        User user = userRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);


        new ModelMapper().map(userDTO, user);

        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }

    @GetMapping
    Page<User> paginate(@PageableDefault(sort = "fullName", size = 10) Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
