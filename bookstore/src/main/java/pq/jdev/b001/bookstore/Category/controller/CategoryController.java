package pq.jdev.b001.bookstore.Category.controller;

import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pq.jdev.b001.bookstore.Category.service.CategoryAddEditService;
import pq.jdev.b001.bookstore.Category.web.CategoryWeb;

@Controller
@RequestMapping("/addition")
public class CategoryController {
	@Autowired
	private CategoryAddEditService categoryservice;
	
	@ModelAttribute("category")
	public CategoryWeb categoryweb() {
		return new CategoryWeb();
	}
	
	@GetMapping
	public String AddEditForm(Model model) {
		return "categoryaddedit";
	}
	
	
	
	
	@PostMapping
	public String Add(CategoryWeb categoryweb) {
		pq.jdev.b001.bookstore.Category.model.Category category = categoryservice.save(categoryweb);
		return "redirect:/categorylist";
	}
}