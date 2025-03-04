package belousov.eu.companyservice.model.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "employees")
@Getter
public class Employee {

    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
