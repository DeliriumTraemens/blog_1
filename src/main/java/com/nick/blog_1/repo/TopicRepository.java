package com.nick.blog_1.repo;

import com.nick.blog_1.models.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository <Topic, Long> {
	
	List <Topic> findByParentId(long l);
	
}
