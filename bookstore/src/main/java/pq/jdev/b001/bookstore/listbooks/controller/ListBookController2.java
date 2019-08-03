//package pq.jdev.b001.bookstore.listbooks.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.support.PagedListHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import antlr.collections.impl.LList;
//import pq.jdev.b001.bookstore.Category.model.Category;
//import pq.jdev.b001.bookstore.books.model.Book;
//import pq.jdev.b001.bookstore.listbooks.service.ListBookService;
//
//@Controller
//public class ListBookController2 {
//	@Autowired
//	private ListBookService listBookService;
//
//	
//	@GetMapping("/listbook")
//	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
//		request.getSession().setAttribute("booklist", null);
//
//		
//		if (model.asMap().get("success") != null)
//			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
//		return "listbook";
//	}
//	
//	
//	@ModelAttribute
//	public Book book(Model model) {
//		return new Book();
//	}
//	
//	@GetMapping
//	public String ListForm(Model model) {
//		
//		Iterable<Book> listBook = new ArrayList<>();
//		listBook = listBookService.findAll();
//		model.addAttribute("books", listBook);
//		
//		return "listbook";
//	}
//
//	
//	
//	
//	@GetMapping("/savebook")
//	public String create(Model model) {
//		model.addAttribute("listbook", new Book());
//		return "savebook";
//	}
//
//	@GetMapping("/listbook/{id}/edit")
//	public String edit(@PathVariable int id, Model model) {
//		model.addAttribute("listbook", listBookService.findOne(id));
//		return "savebook";
//	}
//	
//	@PostMapping("/savebook")
//	public String save(@Valid @ModelAttribute Book book, BindingResult result, Model model) {
//		if (result.hasErrors()) {
//			return "savebook";
//		}
//		listBookService.save(book);
//		model.addAttribute("success", "Saved book successfully!");
//		return "listbook";
//	}
//
//	@GetMapping("/listbook/{id}/delete")
//	public String delete(@PathVariable int id, RedirectAttributes redirect) {
//		listBookService.delete(id);
//		redirect.addFlashAttribute("success", "Deleted book successfully!");
//		return "listbook";
//	}
//
//	@GetMapping("/listbook/search/{pageNumber}")
//	public String search(@RequestParam("s") String s, Model model, HttpServletRequest request,
//			@PathVariable int pageNumber) {
//		if (s.equals("")) {
//			return "redirect:/listbook";
//		}
//		List<Book> list = listBookService.search(s);
//		if (list == null) {
//			return "redirect:/listbook";
//		}
//		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("booklist");
//		int pagesize = 3;
//
//		pages = new PagedListHolder<>(list);
//		pages.setPageSize(pagesize);
//
//		final int goToPage = pageNumber - 1;
//		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
//			pages.setPage(goToPage);
//		}
//
//		request.getSession().setAttribute("booklist", pages);
//		int current = pages.getPage() + 1;
//		int begin = Math.max(1, current - list.size());
//		int end = Math.min(begin + 5, pages.getPageCount());
//		int totalPageCount = pages.getPageCount();
//		String baseUrl = "/listbook/page/";
//
//		model.addAttribute("beginIndex", begin);
//		model.addAttribute("endIndex", end);
//		model.addAttribute("currentIndex", current);
//		model.addAttribute("totalPageCount", totalPageCount);
//		model.addAttribute("baseUrl", baseUrl);
//		model.addAttribute("books", pages);
//
//		return "listbook";
//	}
//	
//	
//	@GetMapping("/listbook/{pageNumber}")
//	public String showBookPage(HttpServletRequest request, @PathVariable int pageNumber, Model model) {
//		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("booklist");
//		int pagesize = 3;
//		List<Book> list = (List<Book>) listBookService.findAll();
//		System.out.println(list.size());
//		if (pages == null) {
//			pages = new PagedListHolder<>(list);
//			pages.setPageSize(pagesize);
//		} else {
//			final int goToPage = pageNumber - 1;
//			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
//				pages.setPage(goToPage);
//			}
//		}
//		request.getSession().setAttribute("booklist", pages);
//		int current = pages.getPage() + 1;
//		int begin = Math.max(1, current - list.size());
//		int end = Math.min(begin + 5, pages.getPageCount());
//		int totalPageCount = pages.getPageCount();
//		String baseUrl = "/listbook/page/";
//
//		model.addAttribute("beginIndex", begin);
//		model.addAttribute("endIndex", end);
//		model.addAttribute("currentIndex", current);
//		model.addAttribute("totalPageCount", totalPageCount);
//		model.addAttribute("baseUrl", baseUrl);
//		model.addAttribute("books", pages);
//
//		return "listbook";
//	}
//
//}
