package com.reactive.crudmongodb.service;

import com.reactive.crudmongodb.dto.ProductDto;
import com.reactive.crudmongodb.repository.ProductRepository;
import com.reactive.crudmongodb.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Flux<ProductDto> getProducts(){
        return repository
                .findAll()
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProduct(String id){
        return repository
                .findById(id)
                .map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductsWithinPriceRange(double min, double max){
        return repository
                .findByPriceBetween(Range.closed(min,max))
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto){
        return productDto
                .map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDto, String id){
        return repository.findById(id)
                .flatMap(p->productDto.map(AppUtils::dtoToEntity))
                .doOnNext(d->d.setId(id))
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }

}
