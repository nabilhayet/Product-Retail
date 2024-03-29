package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer>{

}