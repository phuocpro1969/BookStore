package pq.jdev.b001.bookstore.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.service.CategoryAddEditService;
import pq.jdev.b001.bookstore.category.web.CategoryWeb;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.publishers.service.PublisherService;

/*
 * CategoryController Class
 * 
 * Java 12
 * 
 * 17/08/2019
 * 
 * author @nphtu
 * 
 * */

@Controller
@RequestMapping("/addition")
public class CategoryController {
	@Autowired
	private CategoryAddEditService categoryservice;

	@Autowired
	private PublisherService publisherService;

	@ModelAttribute("category")
	public CategoryWeb categoryweb() {
		return new CategoryWeb();
	}

	@GetMapping
	public String AddEditForm(Model model, ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");

		int pagesizeCP = 10;
		PagedListHolder<?> pagePubs = null;
		PagedListHolder<?> pageCates = null;
		List<Publishers> listPub = (List<Publishers>) publisherService.findAll();
		List<Category> categoryList = categoryservice.findAll();
		if (pageCates == null) {
			pageCates = new PagedListHolder<>(categoryList);
			pageCates.setPageSize(pagesizeCP);
		}
		if (pagePubs == null) {
			pagePubs = new PagedListHolder<>(listPub);
			pagePubs.setPageSize(pagesizeCP);
		}
		model.addAttribute("publishers", pagePubs);
		model.addAttribute("categories", pageCates);
		return "categoryadd";
	}

	@PostMapping
	public String Add(CategoryWeb categoryweb) {
		pq.jdev.b001.bookstore.category.model.Category category = categoryservice.save(categoryweb);
		return "redirect:/categorylist";
	}
}
