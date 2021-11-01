package com.nick.blog_1.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="product")
public class Product
	{
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@Column
		private Long categoryId;
		
		@Column
		private String name;
		
		@Column
		private String description;
		
		@Column
		private float price;
		
		@Column
		private String imagePath;
	
	@Override
	public boolean equals(Object o)
		{
		if (this == o)
			{
				return true;
			}
		if (o == null || getClass() != o.getClass())
			{
				return false;
			}
		Product product = (Product) o;
		return Float.compare(product.price, price) == 0 && id.equals(product.id) && categoryId.equals(product.categoryId) && name.equals(product.name) && Objects.equals(description, product.description) && Objects.equals(imagePath, product.imagePath);
		}
	
	@Override
	public int hashCode()
		{
		return Objects.hash(id, categoryId, name, description, price, imagePath);
		}
		
		
	
	public Long getCategoryId()
		{
		return categoryId;
		}
	
	public void setCategoryId(Long categoryId)
		{
		this.categoryId = categoryId;
		}
	
	public Product(Long id, Long categoryId, String name, String description, float price, String imagePath)
		{
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imagePath = imagePath;
		}
	
		
	
	public Product()
		{
		}
	
	public Product(Long id, String name, String description, float price, String imagePath)
		{
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imagePath = imagePath;
		}
	
	public Product(Long id, String name, String description, float price)
		{
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		}
	public Product(String name, String description, float price)
		{
		this.name = name;
		this.description = description;
		this.price = price;
		}
	
	public Long getId()
		{
		return id;
		}
	
	public void setId(Long id)
		{
		this.id = id;
		}
	
	public String getName()
		{
		return name;
		}
	
	public void setName(String name)
		{
		this.name = name;
		}
	
	public String getDescription()
		{
		return description;
		}
	
	public void setDescription(String description)
		{
		this.description = description;
		}
	
	public float getPrice()
		{
		return price;
		}
	
	public void setPrice(float price)
		{
		this.price = price;
		}
	
	public String getImagePath()
		{
		return imagePath;
		}
	
	public void setImagePath(String imagePath)
		{
		this.imagePath = imagePath;
		}
	
	@Override
	public String toString()
		{
		return "\nProduct{" +
				       "id=" + id +
				       ", name='" + name + '\'' +
				       ", description='" + description + '\'' +
				       ", price=" + price +
				       ", imagePath='" + imagePath + '\'' +
				       '}';
		}
	}
