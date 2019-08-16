package pq.jdev.b001.bookstore.category.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.service.CategoryAddEditService;
import pq.jdev.b001.bookstore.category.web.CategoryWeb;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.publishers.service.PublisherService;

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

	@Autowired
	private PublisherService publisherService;

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
	public String ListForm(HttpServletRequest request, @PathVariable int pageNumber, Model model, ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		PagedListHolder<?> pageLs = (PagedListHolder<?>) request.getSession().getAttribute("listCategory");
		int pagesize = 4;
		List<Category> categoryList = categoryservice.findAll();
		if (pageLs == null) {
			pageLs = new PagedListHolder<>(categoryList);
			pageLs.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pageLs.getPageCount() && goToPage >= 0) {
				pageLs.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("listCategory", pageLs);
		int current = pageLs.getPage() + 1;
		int begin = Math.max(1, current - categoryList.size());
		int end = Math.min(begin + 5, pageLs.getPageCount());
		int totalPageCount = pageLs.getPageCount();
		String baseUrl = "/categorylist/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("categoryL", pageLs);

		int pagesizeCP = 10;
		PagedListHolder<?> pagePubs = null;
		PagedListHolder<?> pageCates = null;
		List<Publishers> listPub = (List<Publishers>) publisherService.findAll();
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
			@PathVariable int pageNumber, ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		int pagesizeCP = 10;
		PagedListHolder<?> pagePubs = null;
		PagedListHolder<?> pageCates = null;
		List<Publishers> listPub = (List<Publishers>) publisherService.findAll();
		List<Category> listCate = categoryservice.findAll();
		if (pageCates == null) {
			pageCates = new PagedListHolder<>(listCate);
			pageCates.setPageSize(pagesizeCP);
		}
		if (pagePubs == null) {
			pagePubs = new PagedListHolder<>(listPub);
			pagePubs.setPageSize(pagesizeCP);
		}
		model.addAttribute("publishers", pagePubs);
		model.addAttribute("categories", pageCates);
		if (s.equals("")) {
			return "redirect:/categorylist";
		}

		List<Category> categoryList = categoryservice.findByName(s);
		if (categoryList == null) {
			return "redirect:/categorylist";
		}
		PagedListHolder<?> pageLs = (PagedListHolder<?>) request.getSession().getAttribute("listCategory");
		int pagesize = 4;

		pageLs = new PagedListHolder<>(categoryList);
		pageLs.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pageLs.getPageCount() && goToPage >= 0) {
			pageLs.setPage(goToPage);
		}

		request.getSession().setAttribute("listCategory", pageLs);
		int current = pageLs.getPage() + 1;
		int begin = Math.max(1, current - categoryList.size());
		int end = Math.min(begin + 5, pageLs.getPageCount());
		int totalPageCount = pageLs.getPageCount();
		String baseUrl = "/categorylist/search/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("categoryL", pageLs);

		return "categoryList";
	}
}
