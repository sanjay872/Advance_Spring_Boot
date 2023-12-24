package com.reactive.crudmongodb.repository;

import com.reactive.crudmongodb.dto.ProductDto;
import com.reactive.crudmongodb.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product,String> {
    Flux<Product> findByPriceBetween(Range<Double> closed);
}
