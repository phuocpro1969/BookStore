package pq.jdev.b001.bookstore.listbooks.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.listbooks.service.ListBookService;

@Controller
public class ListBookController {
	@Autowired
	private ListBookService listBookService;


	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/book")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("booklist", null);

		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/book/page/1";
	}

	@GetMapping("/book/page/{pageNumber}")
	public String showBookPage(HttpServletRequest request, @PathVariable int pageNumber, Model model,ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("booklist");
		int pagesize = 3;
		List<Book> list = (List<Book>) listBookService.findAll();
		System.out.println(list.size());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("booklist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/book/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("books", pages);

		return "listbook";
	}

	@GetMapping("/book/create")
	public String create(Model model,ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		model.addAttribute("book", new Book());
		return "savebook";
	}

	@GetMapping("/book/{id}/edit")
	public String edit(@PathVariable int id, Model model,ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		model.addAttribute("book", listBookService.findOne(id));
		return "savebook";
	}

	@PostMapping("/book/save")
	public String save(@Valid Book book, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "savebook";
		}
		listBookService.save(book);
		redirect.addFlashAttribute("success", "Saved book successfully!");
		return "redirect:/book";
	}

	@GetMapping("/book/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		listBookService.delete(id);
		redirect.addFlashAttribute("success", "Deleted book successfully!");
		return "redirect:/book";
	}

	@GetMapping("/book/search/{pageNumber}")
	public String search(@RequestParam("s") String s, Model model, HttpServletRequest request,
			@PathVariable int pageNumber,ModelMap map) {
		map.addAttribute("header", "header_admin");
		map.addAttribute("footer", "footer_admin");
		if (s.equals("")) {
			return "redirect:/book";
		}
		
		List<Book> list = listBookService.search(s);
		if (list == null) {
			return "redirect:/book";
		}
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("booklist");
		int pagesize = 3;

		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);
		
		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}

		request.getSession().setAttribute("booklist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/book/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("books", pages);

		return "listbook";
	}
}
