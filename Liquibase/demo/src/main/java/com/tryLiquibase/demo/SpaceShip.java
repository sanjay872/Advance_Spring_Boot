package com.tryLiquibase.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SpaceShip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String type;

    @Column
    private String captain;

    @Column
    private int fuel;
}
