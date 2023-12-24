package com.reactive.crudmongodb.controller;

import com.reactive.crudmongodb.dto.ProductDto;
import com.reactive.crudmongodb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/all")
    public Flux<ProductDto> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getProductById(@PathVariable("id") String id){
        return service.getProduct(id);
    }

    @GetMapping("/priceRange")
    public Flux<ProductDto> getProductsByPriceRange(@RequestParam double min,@RequestParam double max){
        return service.getProductsWithinPriceRange(min,max);
    }

    @PostMapping()
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDto){
        return service.saveProduct(productDto);
    }

    @PutMapping("/update/{id}")
    public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDto, @PathVariable String id){
        return  service.updateProduct(productDto,id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return service.deleteProduct(id);
    }

}
