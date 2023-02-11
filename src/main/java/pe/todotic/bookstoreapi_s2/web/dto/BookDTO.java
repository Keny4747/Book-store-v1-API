package pe.todotic.bookstoreapi_s2.web.dto;

import lombok.Data;

/**
 * Java 17 permite crear records util para los DTOs
 */
@Data
public class BookDTO {
    private String title;
    private Float price;
    private String slug;
    private String desc;
}
