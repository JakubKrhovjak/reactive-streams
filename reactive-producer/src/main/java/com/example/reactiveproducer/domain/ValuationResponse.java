package com.example.reactiveproducer.domain;

import lombok.Data;


/**
 * @author Jakub krhovjak
 */
@Data
public class ValuationResponse {

    private Long id;
    private String name;
    private String description;
    private int value;

}
