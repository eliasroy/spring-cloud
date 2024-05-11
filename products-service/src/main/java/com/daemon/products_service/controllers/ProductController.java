package com.daemon.products_service.controllers;

import java.util.List;
import com.daemon.products_service.model.dtos.ProductRequest;
import com.daemon.products_service.model.dtos.ProductResponse;
import com.daemon.products_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        this.productService.addProduct(productRequest);
    }
    @GetMapping
    @ResponseStatus( HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return this.productService.getAllProducts();
    }

}
