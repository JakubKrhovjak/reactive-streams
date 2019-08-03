package com.example.reactiveproducer.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Jakub krhovjak
 */
@Entity
@Data
public class Valuation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "valuation_id_seq")
    @SequenceGenerator(allocationSize = 1, name = "valuation_id_seq", sequenceName = "valuation_id_seq")
    private Long id;
    private String name;
    private String description;
    private int value;

}
