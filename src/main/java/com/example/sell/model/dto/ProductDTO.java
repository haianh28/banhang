package com.example.sell.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String idProduct;
    private String idCategory;
    private int idSupplier;
    private String name;
    private double price;
    private String image;
    private String content;
    private int favorite;
    private int amount;
    private Boolean status;

}
