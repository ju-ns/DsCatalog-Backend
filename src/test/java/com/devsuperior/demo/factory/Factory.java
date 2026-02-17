package com.devsuperior.demo.factory;

import com.devsuperior.demo.dto.ProductDTO;
import com.devsuperior.demo.entities.Category;
import com.devsuperior.demo.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L,"Phone", 800.0, "Good Phone", "https://img.com/img.png", Instant.parse("2020-07-14T10:00:00Z"));
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory(){
        return new Category(1L, "Eletronics");
    }
}
