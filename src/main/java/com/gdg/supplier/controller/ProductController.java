package com.gdg.supplier.controller;

import com.gdg.supplier.dto.ProductDto;
import com.gdg.supplier.service.impl.ProductServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {
    private ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Gets all products")
    @GetMapping
    public ResponseEntity getProducts() {
        return productService.getProducts();
    }

    @ApiOperation(value = "Gets product by given id")
    @GetMapping("{id}")
    public ResponseEntity getProduct(@PathVariable String id) {
        return new ResponseEntity(productService.getProductById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Adds product to database")
    @PostMapping("/add-product")
    public ResponseEntity addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @ApiOperation(value = "Deletes product by given id")
    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteProductById(@PathVariable String id) {
        return new ResponseEntity(productService.deleteProductById(id), HttpStatus.OK);
    }
}
