package belousov.eu.companyservice.model.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "companies", indexes = @Index(name = "idx_company_name", columnList = "name"))
@Getter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "budget")
    private long budget;

}
