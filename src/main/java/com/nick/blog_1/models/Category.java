package com.nick.blog_1.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="category" )
public class Category
	{
	public Long getId()
		{
		return id;
		}
	
	@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public Long id;
		
		@Column
		private String name;
		@Column
		private long parentId;
		@Column
		private String description;
		
		@Column
		private String imagePath;
	
	public Category(String name, long parentId, String description)
		{
		this.name = name;
		this.parentId = parentId;
		this.description = description;
		}
	public Category(String name, String description)
		{
		this.name = name;
		this.description = description;
		}
	
	public Category()
		{
		}
	
	public long getParentId()
		{
		return parentId;
		}
	
	public void setParentId(long parentId)
		{
		this.parentId = parentId;
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
	
	public String getImagePath()
		{
		return imagePath;
		}
	
	public void setImagePath(String imagePath)
		{
		this.imagePath = imagePath;
		}
	
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
		Category category = (Category) o;
		return parentId == category.parentId && id.equals(category.id) && name.equals(category.name) && Objects.equals(description, category.description) && Objects.equals(imagePath, category.imagePath);
		}
	
	@Override
	public int hashCode()
		{
		return Objects.hash(id, parentId, name, description, imagePath);
		}
	
	@Override
	public String toString()
		{
		return "\nCategory{" +
				       "id=" + id +
				       ", parentId=" + parentId +
				       ", name='" + name + '\'' +
				       ", description='" + description + '\'' +
				       ", imagePath='" + imagePath + '\'' +
				       '}';
		}
	}
