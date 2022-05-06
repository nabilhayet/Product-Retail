package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class OrderProduct implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn( name = "order_id", referencedColumnName= "id")
	private Order order;
	 
	
	@ManyToOne
	@JoinColumn( name = "product_id", referencedColumnName= "id")
	private Product product;
	
	
	public OrderProduct() {
		
	}

	public OrderProduct(Integer id, Order order, Product product) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//	@Override
//	public String toString() {
//		return "OrderProduct [id=" + id + ", product=" + product + "]";
//	}

}
