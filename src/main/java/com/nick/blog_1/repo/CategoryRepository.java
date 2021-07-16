package com.nick.blog_1.repo;

import com.nick.blog_1.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{
	
	Iterable<Category> findByParentId(Long parentId);
	}
