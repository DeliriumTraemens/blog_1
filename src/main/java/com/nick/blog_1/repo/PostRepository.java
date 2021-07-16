package com.nick.blog_1.repo;

import com.nick.blog_1.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository <Post,Long>
	{
	
	}
