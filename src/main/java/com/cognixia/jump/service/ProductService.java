package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.ProductRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository repo;
	
	@Autowired
	UserRepository user_repo;
	
	@Autowired
	JwtUtil jwtUtil;

	
	public List<Product>getAllProducts() {
		return repo.findAll();
	}
	
	public Product createProduct(Product product, HttpServletRequest request) {
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String jwt = null;
		String username = null;
		
		jwt = authorizationHeader.substring(7);
		username = jwtUtil.extractUsername(jwt);

		product.setId(null);

		product.setUser(user_repo.findByUsername(username).get());

		return repo.save(product);

	}
	
	public Product findProductById(int id ) {
		
		Optional<Product> product = repo.findById(id);
		
		return product.get();
	}

	
	public List<Product> getProductWithHighPrice(double price){
		return repo.productWithHigherPrice(price);
	}
	
	public Product getProductWithId(int id) {
		Optional<Product> found = repo.findById(id);
		return found.get();
	}
	
	public Product updateProduct(Product product) {
		return repo.save(product);
	}
	
	public boolean deleteProduct(int id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		}
		return false;
	}
}
