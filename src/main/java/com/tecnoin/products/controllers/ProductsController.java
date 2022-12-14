package com.tecnoin.products.controllers;

import com.sun.istack.NotNull;
import com.tecnoin.products.models.dto.ProductDTO;
import com.tecnoin.products.models.dto.ResponseDTO;
import com.tecnoin.products.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    ProductsService productsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getProducts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        ResponseEntity<ResponseDTO> response = productsService.getProducts(page, size);

        return response;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createProduct(
        @RequestBody @NotNull @Validated ProductDTO product
    ) {
        ResponseEntity<ResponseDTO> response = productsService.createProduct(product);

        return response;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateProduct(
            @PathVariable(value = "id") Long id,
            @RequestBody @NotNull @Validated ProductDTO product
    ) {
        ResponseEntity<ResponseDTO> response = productsService.updateProduct(id, product);

        return response;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> deleteProduct(
            @PathVariable(value = "id") Long id
    ) {
        ResponseEntity<ResponseDTO> response = productsService.deleteProduct(id);

        return response;
    }
}