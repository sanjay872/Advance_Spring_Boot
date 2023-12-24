package com.reactive.crudmongodb;

import com.reactive.crudmongodb.controller.ProductController;
import com.reactive.crudmongodb.dto.ProductDto;
import com.reactive.crudmongodb.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
class CrudMongoDbApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService service;


	@Test
	public void saveProductTest(){
		Mono<ProductDto> productDto=Mono.just(new ProductDto("101","phone",1,1000));
		when(service.saveProduct(productDto)).thenReturn(productDto);
		webTestClient.post()
				.uri("/product")
				.body(Mono.just(productDto),ProductDto.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void getProductsTest(){
		Flux<ProductDto> productsDto=Flux
				.just(new ProductDto("101","phone",1,1000),
						new ProductDto("102","bag",1,2000),
						new ProductDto("103","bottle",2,200));
		when(service.getProducts()).thenReturn(productsDto);
		Flux<ProductDto> responseBody = webTestClient.get()
				.uri("/product/all")
				.exchange()
				.expectStatus().isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("101","phone",1,1000))
				.expectNext(new ProductDto("102","bag",1,2000))
				.expectNext(new ProductDto("103","bottle",2,200))
				.verifyComplete();
	}

	@Test
	public void getProductsByIdTest(){
		Mono<ProductDto> productDto=Mono.just(new ProductDto("101","phone",1,1000));
		when(service.getProduct(any())).thenReturn(productDto);
		Flux<ProductDto> responseBody = webTestClient.get()
				.uri("/product/101")
				.exchange()
				.expectStatus().isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getName().equals("phone"))
				.verifyComplete();
	}

	@Test
	public void updateProductTest(){
		Mono<ProductDto> productDto=Mono.just(new ProductDto("101","phone",1,1000));
		when(service.updateProduct(any(),any())).thenReturn(productDto);
		webTestClient.put()
				.uri("/product/update/101")
				.body(Mono.just(productDto),ProductDto.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void deleteProductTest(){
		given(service.deleteProduct(any())).willReturn(Mono.empty());
		webTestClient.delete().uri("/product/101")
				.exchange()
				.expectStatus().isOk();
	}

}
