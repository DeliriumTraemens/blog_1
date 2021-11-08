package com.nick.blog_1.repo;

import com.nick.blog_1.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>
	{
		List<Product> findByCategoryId(long id);
	}
