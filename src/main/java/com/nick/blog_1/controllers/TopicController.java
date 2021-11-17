package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Topic;
import com.nick.blog_1.repo.CategoryRepository;
import com.nick.blog_1.repo.ProductRepository;
import com.nick.blog_1.repo.TopicRepository;
import com.nick.blog_1.service.PathMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TopicController {
	@Autowired
	TopicRepository topicRepo;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@Value("${upload.path2}")
	private String uploadPath2;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private PathMaker pm;
	
	@ModelAttribute(name = "topic")
	public Topic topic() {
		return new Topic();
	}
	
	// ----------- Methods ----------- \\
	
	@GetMapping("/topics")
	public String topicList(Model model){
		List<Topic> root =topicRepo.findByParentId(0L);
		model.addAttribute("topicList",root);
		System.out.println("modelis"+model);
		return "/topics";
	}
	
	@GetMapping("/topic/details/{id}")
	public String topicDetails(@PathVariable(value = "id") long id, Model model){
		model.addAttribute("curTopic",topicRepo.findById(id).get());
		
		return "/topic-details";
	}
	//-------add
	@GetMapping("/topics/add")
	public String topicAdd(Model model){
		return "/topic-add";
	}
	
	@PostMapping("/topics/add")
		public String topicAddPost(@RequestParam String parent,
	                               @RequestParam String name,
	                               @RequestParam String description){
		Topic topic = new Topic();
		
		if(parent!=null){
			topic.setParentId(Long.valueOf(parent));
		}
		if(name !=null){
			topic.setName(name);
		}
		if(description!=null){
			topic.setDescription(description);
		}
		
		topicRepo.save(topic);
		return "redirect:/topics";
		}
	//-----add
	//sub Topic add
	@GetMapping("/sub-topic-add/{id}/{parentId}")
	public String subTopicAdd(@PathVariable(value = "id") long id,
	                          @PathVariable(value = "parentId") long parentId,
	                          Model model){
		Topic parent = topicRepo.findById(id).get();
		model.addAttribute("parentTopic",parent);
		
		return "/subtopicadd";
	}
	
	@PostMapping("/subtopics/add")
	public String subTopicAddPost(@RequestParam String parent,
	                              @RequestParam String name,
	                              @RequestParam String description){
		
		Topic currentParent = topicRepo.findById(Long.valueOf(parent)).get();
			currentParent.setImageUrl("Parent");
		
		Topic newChild=new Topic();
		newChild.setParentId(Long.valueOf(parent));
		newChild.setName(name);
		newChild.setDescription(description);
		newChild.setParentTopic(currentParent);
		
//		currentParent.childrenSet.add(newChild);
		
		topicRepo.save(currentParent);
		topicRepo.save(newChild);
		
		return "redirect:/topics";
	}
	
	//sub Topic add
	
	//Topic Remove
	@GetMapping("/topic/remove/{id}")
	public String removeTopic(@PathVariable(value="id" )long id){
		
		topicRepo.deleteById(id);
		
		return "redirect:/topics";
	}
	
}//End class
