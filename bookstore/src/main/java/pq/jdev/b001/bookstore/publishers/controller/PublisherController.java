package pq.jdev.b001.bookstore.publishers.controller;

import java.sql.Timestamp;
import java.util.List;
//import java.util.concurrent.Flow.Publisher;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.publishers.service.PublisherService;
import pq.jdev.b001.bookstore.users.service.ModuleRunFirst;
import pq.jdev.b001.bookstore.users.service.UserService;

@Controller
public class PublisherController {

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private ModuleRunFirst moduleRunFirst;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/publisher/add")
	public String create(Model model, ModelMap map, Authentication authentication, HttpServletRequest request) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model, 15);
		moduleRunFirst.headerFooter(authentication, map, roles);

		Long idP = userService.findByUsername(authentication.getName()).getId();
		request.getSession().setAttribute("cd", null);
		request.getSession().setAttribute("idPub", idP);
		request.getSession().setAttribute("idC", idP);
		request.getSession().setAttribute("idU", idP);
		model.addAttribute("publisher", new Publishers(idP, idP));
		java.util.Date date = new java.util.Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		map.addAttribute("cd", ts);
		map.addAttribute("ud", ts);
		return "publisher/publisherAdd";
	}

	@PostMapping("/publisher/save")
	public String savePublisher(@Valid Publishers publisher, BindingResult result, HttpServletRequest request,
			Authentication authentication, ModelMap map, Model model) {
		List<String> roles = moduleRunFirst.getRole(authentication);

		Long idPub = (Long) request.getSession().getAttribute("idPub");
		if (idPub != publisher.getId() && publisher.getId() != null)
			return "redirect:/";

		publisher.setCreateDate((Timestamp) request.getSession().getAttribute("cd"));
		publisher.setCreateId((Long) request.getSession().getAttribute("idC"));
		publisher.setUpdateId((Long) request.getSession().getAttribute("idU"));
		java.util.Date date = new java.util.Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		map.addAttribute("cd", publisher.getCreateDate());
		map.addAttribute("ud", ts);

		if (result.hasErrors()) {
			Long idP = userService.findByUsername(authentication.getName()).getId();
			moduleRunFirst.headerFooter(authentication, map, roles);
			moduleRunFirst.leftBar_cate_pub(model, 15);
			model.addAttribute("publisher", publisher);
			request.getSession().setAttribute("cd", publisher.getCreateDate());
			request.getSession().setAttribute("idC", publisher.getCreateId());
			request.getSession().setAttribute("idU", idP);
			if (publisher.getId() == null)
				map.addAttribute("cd", ts);
			else
				map.addAttribute("cd", publisher.getCreateDate());
			map.addAttribute("ud", ts);
			return "publisher/publisherAdd";
		}

		if (moduleRunFirst.isAdmin(roles))
			publisherService.save(publisher);
		else
			return "redirect:/";

		return "redirect:/publishersList";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/publishersList")
	public String viewPublishersList() {
		return "redirect:/publishersList/page/1";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/publisher/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes redirect, Authentication authentication) {

		if (moduleRunFirst.isAdmin(moduleRunFirst.getRole(authentication)) && (id != (long) 1)) {
			bookService.changePublisher(id, (long) 1);
			publisherService.delete(id);
		}
		return "redirect:/publishersList";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/publisher/{id}/edit")
	public String edit(@PathVariable long id, RedirectAttributes redirect, Model model, ModelMap map,
			HttpServletRequest request, Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model, 15);
		moduleRunFirst.headerFooter(authentication, map, roles);

		Long idP = userService.findByUsername(authentication.getName()).getId();
		Publishers pub = publisherService.find(id);

		model.addAttribute("publisher", pub);
		request.getSession().setAttribute("cd", pub.getCreateDate());
		request.getSession().setAttribute("idPub", pub.getId());
		request.getSession().setAttribute("idC", pub.getCreateId());
		request.getSession().setAttribute("idU", idP);

		java.util.Date date = new java.util.Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		map.addAttribute("cd", pub.getCreateDate());
		map.addAttribute("ud", ts);
		redirect.addFlashAttribute("success", "Update publisher successfully!");
		return "publisher/publisherAdd";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/publishersList/page/{pageNumber}")
	public String showPage(HttpServletRequest request, @PathVariable int pageNumber, Model model, ModelMap map,
			Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		moduleRunFirst.leftBar_cate_pub(model, 15);

		int pagesize = 8;
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
		return "publisher/publishersList";
	}
}
