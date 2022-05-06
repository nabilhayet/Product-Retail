package com.cognixia.jump.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Order;
import com.cognixia.jump.service.OrderService;

@RequestMapping("/api")
@RestController
public class OrderController {
	
	@Autowired
	OrderService serv;
	
	
	@GetMapping("/orders")
	public List<Order> getAllOrders(){
		return serv.getOrders();
	}
	
	@PostMapping("/orders")
	public ResponseEntity<Order> createOrder(@RequestBody Order order, HttpServletRequest request){
		
		Order created = serv.createOrder(order, request);
		
		return ResponseEntity.status(201).body(created);
	}
	
}