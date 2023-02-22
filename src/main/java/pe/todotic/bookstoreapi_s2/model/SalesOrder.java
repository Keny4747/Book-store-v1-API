package pe.todotic.bookstoreapi_s2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float total;
    private LocalDateTime createdAt;
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private User customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<SalesItem> items;

    public enum PaymentStatus{
        PENDING,
        PAID
    }
}
