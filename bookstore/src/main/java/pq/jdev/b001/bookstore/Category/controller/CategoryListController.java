package pq.jdev.b001.bookstore.Category.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.Category.service.CategoryAddEditService;
import pq.jdev.b001.bookstore.Category.web.CategoryWeb;

@Controller
@RequestMapping("/categorylist")
public class CategoryListController {
	@Autowired
	private CategoryAddEditService categoryservice;

	@ModelAttribute("category")
	public CategoryWeb categoryweb() {
		return new CategoryWeb();
	}

	@GetMapping
	public String ListForm(Model model, ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		List<Category> categoryList = categoryservice.findAll();
		model.addAttribute("categories", categoryList);

		return "categoryList";
	}

}
