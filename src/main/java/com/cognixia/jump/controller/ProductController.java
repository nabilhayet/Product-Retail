package com.cognixia.jump.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.service.ProductService;

@RequestMapping("/api")
@RestController
public class ProductController {
	
	@Autowired
	ProductService serv;
	
	@GetMapping("/products")
	public List<Product> getProducts(){
		return serv.getAllProducts();
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product, HttpServletRequest request){
		
		Product created = serv.createProduct(product, request);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@GetMapping("/products/{price}")
	public List<Product> getHighProducts(@PathVariable double price){
		return serv.getProductWithHighPrice(price);
	}
	
	@PutMapping("/products")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product) throws ResourceNotFoundException{
		
		if( serv.getProductWithId(product.getId()) != null){
			Product updated = serv.updateProduct(product);
			return ResponseEntity.status(200).body(updated);
		}
		
		throw new ResourceNotFoundException("Product with id: "+ product.getId()+ " was not found");
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable int id) {
		if(serv.deleteProduct(id) ) {
			return ResponseEntity.status(200).body("product id: "+ id + " was removed from the database");
		}
		
		return ResponseEntity.status(400).body("id not found");
	}
	
}
