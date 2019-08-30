package pq.jdev.b001.bookstore.users.web;

import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.listbooks.service.ListBookService;
import pq.jdev.b001.bookstore.publishers.service.PublisherService;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.service.ModuleRunFirst;
import pq.jdev.b001.bookstore.users.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private ListBookService listBookService;

	@Autowired
	private UserService userService;

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private ModuleRunFirst moduleRunFirst;

	@GetMapping({ "/" })
	public String root(Authentication authentication, ModelMap map, Model model, HttpServletRequest request,
			Principal principal) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model, 15);
		moduleRunFirst.headerFooter(authentication, map, roles);
		map.addAttribute("ok", "FALSE");
		if (moduleRunFirst.isAdmin(roles))
			map.addAttribute("ok", "TRUE");

		PagedListHolder<?> pages = null;

		int pagesize = 8;
		List<Book> listH = null;
		if (principal == null) {
			listH = (List<Book>) listBookService.findAll();
		} else {
			Person per = userService.findByUsername(principal.getName());
			listH = getList(per);
		}

		if (pages == null) {
			pages = new PagedListHolder<>(listH);
			pages.setPageSize(pagesize);
		}
		if (principal == null)
			request.getSession().setAttribute("bookListC", pages);
		else
			request.getSession().setAttribute("bookListR", pages);

		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - listH.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("books", pages);

		return "user_admin/indexcontainer";
	}

	@GetMapping("/page/{pageNumber}")
	public String showBookPage(Authentication authentication, HttpServletRequest request, @PathVariable int pageNumber,
			Model model, ModelMap map, Principal principal) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model, 15);
		moduleRunFirst.headerFooter(authentication, map, roles);
		map.addAttribute("ok", "FALSE");
		if (moduleRunFirst.isAdmin(roles))
			map.addAttribute("ok", "TRUE");

		PagedListHolder<?> pages = null;
		int pagesize = 6;
		List<Book> list = null;
		if (principal == null) {
			list = (List<Book>) listBookService.findAll();
			pages = (PagedListHolder<?>) request.getSession().getAttribute("bookListC");
		} else {
			Person per = userService.findByUsername(principal.getName());
			pages = (PagedListHolder<?>) request.getSession().getAttribute("bookListR");
			list = getList(per);
		}

		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		if (principal == null)
			request.getSession().setAttribute("bookListC", pages);
		else
			request.getSession().setAttribute("bookListR", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/page/";
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("books", pages);

		return "user_admin/indexcontainer";
	}

	// tao list
	@ModelAttribute("list")
	public List<Book> getList(Person p) {
		List<Book> oldList = listBookService.findAll();
		List<Book> newList = new ArrayList<Book>();
		Long id = p.getId();
		for (Book b : oldList) {
			b.setOk(0);
			if (b.getPerson().getId() == id)
				b.setOk(1);
			newList.add(b);
		}
		return newList;
	}

	@GetMapping("/search/{pageNumber}")
	public String search(@RequestParam("s") String s, Authentication authentication, Model model,
			HttpServletRequest request, @PathVariable int pageNumber, ModelMap map, Principal principal) {
		if (s.equals("")) {
			return "redirect:/";
		}
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model, 15);
		moduleRunFirst.headerFooter(authentication, map, roles);
		map.addAttribute("ok", "FALSE");
		if (moduleRunFirst.isAdmin(roles))
			map.addAttribute("ok", "TRUE");

		PagedListHolder<?> pages = null;
		List<Book> listBookGet = null;
		if (principal == null) {
			listBookGet = (List<Book>) listBookService.findAll();
			pages = (PagedListHolder<?>) request.getSession().getAttribute("bookListC");
		} else {
			Person per = userService.findByUsername(principal.getName());
			pages = (PagedListHolder<?>) request.getSession().getAttribute("bookListR");
			listBookGet = getList(per);
		}
		List<Book> list = new ArrayList<Book>();
		for (Book a : listBookGet) {
			if (moduleRunFirst.is(String.valueOf(a.getId()), s) || moduleRunFirst.is(a.getTitle(), s)
					|| moduleRunFirst.is(a.getDomain(), s) || moduleRunFirst.is(a.getAuthors(), s)
					|| moduleRunFirst.is(publisherService.findOne(a.getPublisher().getId()).getPublisher(), s))
				list.add(a);
		}

		for (Book a : listBookGet) {
			if (moduleRunFirst.error(String.valueOf(a.getId()), s) || moduleRunFirst.error(a.getTitle(), s)
					|| moduleRunFirst.error(a.getDomain(), s) || moduleRunFirst.error(a.getAuthors(), s)
					|| moduleRunFirst.error(publisherService.find(a.getPublisher().getId()).getPublisher(), s))
				if (!list.contains(a))
					list.add(a);
		}

		int pagesize = 8;

		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}

		if (principal == null)
			request.getSession().setAttribute("bookListC", pages);
		else
			request.getSession().setAttribute("bookListR", pages);

		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("books", pages);

		return "user_admin/indexcontainer";
	}

	@GetMapping("/login")
	public String login(ModelMap map, HttpServletRequest request, Model model, Authentication authentication) {
		
		List<String> roles = moduleRunFirst.getRole(authentication);
		if (!roles.contains(null))
			return "redirect:/";
		moduleRunFirst.headerFooter(authentication, map, roles);
		moduleRunFirst.leftBar_cate_pub(model, 10);
		
		String referrer = request.getHeader("Referer");
		if (referrer != null) {
			request.getSession().setAttribute("url_pre_login", referrer);
		}
		return "user_admin/no_login/login";
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/user")
	public String userPage(ModelMap map) {
		return "redirect:/";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String getAdmin(ModelMap map) {
		return "redirect:/";
	}

	@GetMapping(value = "/403")
	public String accessDeniedPage(Authentication authentication, ModelMap map, Model model) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		moduleRunFirst.leftBar_cate_pub(model, 15);
		return "error/403";
	}

}
