package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Order implements Serializable{

	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date order_date;
	
	@ManyToOne
	@JoinColumn( name = "user_id", referencedColumnName= "id")
	private User user;
	
	
	@OneToMany( mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderProduct> order_products;
	
	public Order() {
		
	}

	public Order(Integer id, Date order_date, User user, List<OrderProduct> order_products) {
		super();
		this.id = id;
		this.order_date = order_date;
		this.user = user;
		this.order_products = order_products;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderProduct> getProducts() {
		return order_products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.order_products = products;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", order_date=" + order_date + ", user=" + user + "]";
	}
	
	
}
