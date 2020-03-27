package com.example.reactiveproducer.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author Jakub krhovjak
 */

@Data
@Accessors(chain = true)
@Document
public class Valuation  {

    @Id
    private String id;
    private String name;
    private String description;
    private Integer value;
    private String state;

}
