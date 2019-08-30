package pq.jdev.b001.bookstore.category.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.service.CategoryService;
import pq.jdev.b001.bookstore.users.service.ModuleRunFirst;
import pq.jdev.b001.bookstore.users.service.UserService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ModuleRunFirst moduleRunFirst;

	@Autowired
	private UserService userService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/category/add")
	public String AddEditForm(Model model, ModelMap map, Authentication authentication, HttpServletRequest request) {
		
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model, 15);
		moduleRunFirst.headerFooter(authentication, map, roles);

		Long idP = userService.findByUsername(authentication.getName()).getId();
		request.getSession().setAttribute("cd", null);
		request.getSession().setAttribute("idCate", idP);
		request.getSession().setAttribute("idC", idP);
		request.getSession().setAttribute("idU", idP);
		model.addAttribute("category", new Category(idP, idP));
		java.util.Date date = new java.util.Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		map.addAttribute("cd", ts);
		map.addAttribute("ud", ts);

		return "category/categoryadd";
	}

	@PostMapping("/category/save")
	public String Add(@Valid Category category, BindingResult result, HttpServletRequest request,
			Authentication authentication, ModelMap map, Model model) {
		List<String> roles = moduleRunFirst.getRole(authentication);

		Long idCate = (Long) request.getSession().getAttribute("idCate");
		if (idCate != category.getId() && category.getId() != null)
			return "redirect:/";

		category.setCreateDate((Timestamp) request.getSession().getAttribute("cd"));
		category.setCreateId((Long) request.getSession().getAttribute("idC"));
		category.setUpdateId((Long) request.getSession().getAttribute("idU"));
		java.util.Date date = new java.util.Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		map.addAttribute("cd", category.getCreateDate());
		map.addAttribute("ud", ts);

		if (result.hasErrors()) {
			moduleRunFirst.headerFooter(authentication, map, roles);
			moduleRunFirst.leftBar_cate_pub(model, 15);
			return "category/categoryadd";
		}

		if (moduleRunFirst.isAdmin(roles)) 
			categoryService.save(category);
		 else
			return "redirect:/";
		
		return "redirect:/categoryList";
	}
}
