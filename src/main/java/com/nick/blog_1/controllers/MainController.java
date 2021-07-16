package com.nick.blog_1.controllers;

import com.nick.blog_1.models.Post;
import com.nick.blog_1.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

	@GetMapping("/")
	public String home (Model model) {
	model.addAttribute("title", "Главная бля сука нахер страница");
	return "home";
	}
	
	
	@GetMapping("/about")
	public String about (Model model) {
	model.addAttribute("title", "Об Нас");
	return "about";
	}
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/blog")
	public String blog (Model model) {
	model.addAttribute("title", "Блог");
	Iterable<Post> posts = postRepository.findAll();
	model.addAttribute("posts", posts);
	return "blog-main";
	}
	
	@GetMapping("/blog/add")
	public String blogAdd (Model model) {
	return "blog-add";
	}
	
//	@GetMapping("/blog/{id}")
//	public String blogDetails(@PathVariable(value = "id") long id, Model model) {
//		Optional <Post> post =postRepository.findById(id);
//		ArrayList<Post> res = new ArrayList<>();
//		post.ifPresent(res :: add);
//		model.addAttribute("post",res);
//	return "blog-details";
//	}
	
	
	//@PostMapping("/blog/add")
	//public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
	//	Post post = new Post(title,anons,full_text);
	//	postRepository.save(post);
	//
	//	return "redirect:/blog";
	//}
	


}
