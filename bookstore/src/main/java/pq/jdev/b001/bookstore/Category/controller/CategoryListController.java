package pq.jdev.b001.bookstore.Category.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.Category.service.CategoryAddEditService;
import pq.jdev.b001.bookstore.Category.web.CategoryWeb;
import pq.jdev.b001.bookstore.books.model.Book;

/*
 * CategoryListController Class
 * 
 * Java 12
 * 
 * 17/08/2019
 * 
 * author @nphtu
 * 
 * */


@PreAuthorize("hasRole('ADMIN')")
@Controller
public class CategoryListController {
	@Autowired
	private CategoryAddEditService categoryservice;

	@ModelAttribute("category")
	public CategoryWeb categoryweb() {
		return new CategoryWeb();
	}

	@GetMapping("/categorylist")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("listCategory", null);

		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/categorylist/page/1";
	}
	
	@GetMapping("/categorylist/page/{pageNumber}")
	public String ListForm(HttpServletRequest request, @PathVariable int pageNumber, Model model,ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listCategory");
		int pagesize = 3;
		List<Category> categoryList = categoryservice.findAll();
		if (pages == null) {
			pages = new PagedListHolder<>(categoryList);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("listCategory", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - categoryList.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/categorylist/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("categories", pages);

		return "categoryList";
	}
	
	@GetMapping("/categorylist/delete/{id}")
	public String delete(@PathVariable long id, RedirectAttributes redirect) {
		categoryservice.delete(id);
		redirect.addFlashAttribute("success", "Deleted book successfully!");
		return "redirect:/categorylist";
	}
	
	@GetMapping("/categorylist/search/{pageNumber}")
	public String search(@RequestParam("s") String s, Model model, HttpServletRequest request,
			@PathVariable int pageNumber,ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		if (s.equals("")) {
			return "redirect:/categorylist";
		}
		
		List<Category> categoryList = categoryservice.findByName(s);
		if (categoryList == null) {
			return "redirect:/categorylist";
		}
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listCategory");
		int pagesize = 3;

		pages = new PagedListHolder<>(categoryList);
		pages.setPageSize(pagesize);
		
		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}

		request.getSession().setAttribute("listCategory", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - categoryList.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/categorylist/search/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("categories", pages);

		return "categoryList";
	}
}
