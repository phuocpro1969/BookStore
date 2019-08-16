package pq.jdev.b001.bookstore.publishers.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import java.util.concurrent.Flow.Publisher;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.service.CategoryAddEditService;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.publishers.service.PublisherService;

@Controller
public class PublisherController {
	// private final PublisherRepository publisherReponsitory;

	/*
	 * @Autowired public PublisherController(PublisherRepository
	 * publisherReponsitory) { this.publisherReponsitory = publisherReponsitory; }
	 */

	/*
	 * @GetMapping("list") public String showUpdateForm(Model model) {
	 * model.addAttribute("publishers", publisherReponsitory.findAll()); return
	 * "index"; }
	 */

	// private static List<Publishers> publishers = new ArrayList<Publishers>();

	/*
	 * @RequestMapping(value = {"/index" }, method = RequestMethod.GET) public
	 * String index(Model model) {
	 * 
	 * return "index"; }
	 */
	
	@Autowired
	private PublisherService publisherService;

	@Autowired
	private CategoryAddEditService categoryservice;

	@GetMapping("/publisher/add")
	public String create(Model model,ModelMap map, Authentication authentication) {
		if (authentication != null) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			List<String> roles = new ArrayList<String>();
			for (GrantedAuthority a : authorities) {
				roles.add(a.getAuthority());
			}
			
			if (isAdmin(roles)) {
				map.addAttribute("header", "header_admin");
				map.addAttribute("footer", "footer_admin");
			}
			else if (isUser(roles)){
				map.addAttribute("header", "header_user");
				map.addAttribute("footer", "footer_user");
			} 
		}
		else {
				map.addAttribute("header", "header_login");
				map.addAttribute("footer", "footer_login");
		}
		
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
		
		model.addAttribute("publisher", new Publishers());
		return "publisherAdd";
	}
	
	@PostMapping("/publisher/save")
	public String savePublisher(@Valid Publishers publishers, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "publisherAdd";
			}
		publisherService.save(publishers);
		return "redirect:/publishersList";
	}
	
	@GetMapping("/publishersList")
	public String viewPublishersList(Model model, ModelMap map, HttpServletRequest request) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		return "redirect:/publishersList/page/1";
	}

	@GetMapping("/publisher/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		publisherService.delete(id);
		return "redirect:/publishersList";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/publisher/{id}/edit")
	public String edit(@PathVariable int id, Model model, ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		model.addAttribute("publisher", publisherService.find(id));
		return "detailPublishers";
	}

	/*
	 * @GetMapping("/publisher/search") public String search(@RequestParam("s")
	 * String s, Model model) { if (s.equals("")) { return
	 * "redirect:/publishersList"; }
	 * 
	 * model.addAttribute("publisher", publisherService.search(s)); return
	 * "publishersList"; }
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/publishersList/page/{pageNumber}")
	public String showPage(HttpServletRequest request, @PathVariable int pageNumber, Model model, ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		
		int pagesize = 6;
		List<Publishers> list = (List<Publishers>) publisherService.findAll();
		PagedListHolder<?> pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);
		
		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}

		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/publishersList/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("publishersL", pages);
		
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
		return "publishersList";
	}
	
	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_EMPLOYEE")) {
			return true;
		}
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}
}
