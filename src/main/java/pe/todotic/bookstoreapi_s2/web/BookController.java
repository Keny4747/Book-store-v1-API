package pe.todotic.bookstoreapi_s2.web;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.todotic.bookstoreapi_s2.model.Book;
import pe.todotic.bookstoreapi_s2.repository.BookRepository;
import pe.todotic.bookstoreapi_s2.web.dto.BookDTO;

import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/list")
    List<Book> list() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    Book get(@PathVariable Integer id) {
        return bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Book create(@Validated @RequestBody BookDTO bookDTO) {
        // persistir en la BD y retornar.
        /*
        Book book = new Book();
        ModelMapper mapper = new ModelMapper();
         mapper.map(bookDTO,book);
        */
       Book book = new ModelMapper().map(bookDTO,Book.class);
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    Book update(@Validated
            @PathVariable Integer id,
            @RequestBody BookDTO bookDTO
    ) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        /*
        book.setTitle(form.getTitle());
        book.setSlug(form.getSlug());
        book.setPrice(form.getPrice());
        book.setDesc(form.getDesc());*/

        new ModelMapper().map(bookDTO,book);
        // actualiza el libro en la BD
        return bookRepository.save(book);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);

        //eliminar el libro de la BD
        bookRepository.delete(book);
    }

    @GetMapping
    Page<Book> paginate(
            /* sobreescribir la config. por defecto:
             *   ordenar por el título de forma ascendente
             *   y con un tamaño de 5 elementos por página. */
            @PageableDefault (sort = "title",direction = Sort.Direction.ASC,size = 5)Pageable pageable
    ) {
        // retorna la lista de libros de forma paginada
        return bookRepository.findAll(pageable);
    }

}
