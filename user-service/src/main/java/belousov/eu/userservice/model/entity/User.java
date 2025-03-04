package belousov.eu.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users",
        indexes = {@Index(name = "idx_users_last_name", columnList = "last_name"),
                @Index(name = "idx_users_company_id", columnList = "company_id")})
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "company_id")
    private int companyId;

}
