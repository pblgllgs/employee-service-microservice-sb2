package com.pblgllgs.employeeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Employee")
@Table(
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "email_UK",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            name = "first_name"
    )
    private String firstName;
    @Column(
            nullable = false,
            name = "last_name"
    )
    private String lastName;
    @Column(
            nullable = false,
            name = "email"
    )
    private String email;
}
