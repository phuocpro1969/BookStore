package pq.jdev.b001.bookstore.category.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.service.CategoryService;
import pq.jdev.b001.bookstore.users.service.ModuleRunFirst;
import pq.jdev.b001.bookstore.users.service.UserService;

@PreAuthorize("hasRole('ADMIN')")
@Controller
public class CategoryListController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModuleRunFirst moduleRunFirst;

	@GetMapping("/categoryList")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("listCategory", null);
		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/categoryList/page/1";
	}

	@GetMapping("/categoryList/page/{pageNumber}")
	public String ListForm(HttpServletRequest request, @PathVariable int pageNumber, Model model, ModelMap map,
			Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model);
		moduleRunFirst.headerFooter(authentication, map, roles);

		PagedListHolder<?> pageLs = (PagedListHolder<?>) request.getSession().getAttribute("listCategory");
		int pagesize = 9;
		List<Category> categoryList = categoryService.findAll();
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
		String baseUrl = "/categoryList/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("categoryL", pageLs);

		return "category/categoryList";
	}

	@GetMapping("/categoryList/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirect, Authentication authentication) {
		if (id == (long) (1))
			return "redirect:/categoryList";
		if ((authentication != null) && (id != (long) (1))) {
			bookService.changeCategory((long) 1, id);
			categoryService.delete(id);
			redirect.addFlashAttribute("success", "Deleted category successfully!");
		}
		return "redirect:/categoryList";
	}

	@GetMapping("/categoryList/edit/{id}")
	public String edit(@PathVariable long id, RedirectAttributes redirect, ModelMap map, Model model,
			HttpServletRequest request, Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model);
		moduleRunFirst.headerFooter(authentication, map, roles);

		Long idP = userService.findByUsername(authentication.getName()).getId();
		Category cate = categoryService.findCategoryByID(id);

		model.addAttribute("category", cate);
		request.getSession().setAttribute("cd", cate.getCreateDate());
		request.getSession().setAttribute("idCate", cate.getId());
		request.getSession().setAttribute("idC", cate.getCreateId());
		request.getSession().setAttribute("idU", idP);

		java.util.Date date = new java.util.Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		map.addAttribute("cd", cate.getCreateDate());
		map.addAttribute("ud", ts);
		redirect.addFlashAttribute("success", "Update category successfully!");
		return "category/categoryadd";
	}

	@GetMapping("/categoryList/search/{pageNumber}")
	public String search(@RequestParam("s") String s, Model model, HttpServletRequest request,
			@PathVariable int pageNumber, ModelMap map, Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model);
		moduleRunFirst.headerFooter(authentication, map, roles);
		if (s.equals("")) {
			return "redirect:/categoryList";
		}

		List<Category> categoryList = categoryService.findByName(s);
		if (categoryList == null) {
			return "redirect:/categoryList";
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
		String baseUrl = "/categoryList/search/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("categoryL", pageLs);

		return "category/categoryList";
	}
}
