package com.cognixia.jump.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Order;
import com.cognixia.jump.model.OrderProduct;
import com.cognixia.jump.model.Product;
import com.cognixia.jump.repository.OrderRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.util.JwtUtil;

@Service
public class OrderService {
	
	@Autowired
	ProductService pro_serv;

	@Autowired
	OrderRepository order_repo;

	@Autowired
	UserRepository user_repo;

	@Autowired
	JwtUtil jwtUtil;
	
	public List<OrderProduct> order_products = new ArrayList<>();

	public List<Order> getOrders() {
		return order_repo.findAll();
	}

	public Order createOrder(Order order, HttpServletRequest request) {

		final String authorizationHeader = request.getHeader("Authorization");
		
		String jwt = null;
		String username = null;
		
		jwt = authorizationHeader.substring(7);
		username = jwtUtil.extractUsername(jwt);

		order.setId(null);

		order.setUser(user_repo.findByUsername(username).get());
		
		int product_id = order.getProducts().get(0).getId();
		
		
		Product product = pro_serv.findProductById(product_id);
		
		OrderProduct op = new OrderProduct(null,order, product);
		
		order_products.add(op);
		
		order.setProducts(order_products);

		return order_repo.save(order);

	}

}
