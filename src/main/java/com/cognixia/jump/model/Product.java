package com.cognixia.jump.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Product {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column( nullable = false)
	private String name;
	
	private double weight;
	
	private String description;
	
	@Column( nullable = false)
	private double price;
	
	@ManyToOne
	@JoinColumn( name = "admin_id", referencedColumnName= "id")
	private User user;
	
	@OneToMany( mappedBy = "product")
	private List<OrderProduct> order;
	
	public Product() {
		
	}

	public Product(Integer id, String name, double weight, String description, double price, User user,
			List<OrderProduct> order) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.description = description;
		this.price = price;
		this.user = user;
		this.order = order;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<OrderProduct> getOrder() {
		return order;
	}

	public void setOrder(List<OrderProduct> order) {
		this.order = order;
	}
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", weight=" + weight + ", description=" + description
				+ ", price=" + price + ", user=" + user + " ]";
	}

	
	
	
	
}
