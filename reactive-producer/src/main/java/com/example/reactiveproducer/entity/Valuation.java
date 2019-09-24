package com.example.reactiveproducer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * @author Jakub krhovjak
 */
//@Entity
@Data
@JsonFormat
public class Valuation  {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "valuation_id_seq")
//    @SequenceGenerator(allocationSize = 1, name = "valuation_id_seq", sequenceName = "valuation_id_seq")
    private Long id;
    private String name;
    private String description;
    private Integer value;
    private String state;

}
