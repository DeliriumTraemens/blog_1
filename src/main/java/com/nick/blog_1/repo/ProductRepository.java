package com.nick.blog_1.repo;

import com.nick.blog_1.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>
	{
	}
