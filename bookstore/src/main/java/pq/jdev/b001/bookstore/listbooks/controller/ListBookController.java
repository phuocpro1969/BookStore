package pq.jdev.b001.bookstore.listbooks.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.books.service.UploadPathService;
import pq.jdev.b001.bookstore.books.service.ZipFileService;
import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.service.CategoryService;
import pq.jdev.b001.bookstore.listbooks.service.ListBookService;
import pq.jdev.b001.bookstore.publishers.service.PublisherService;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.service.ModuleRunFirstImpl;
import pq.jdev.b001.bookstore.users.service.UserService;

@Controller
public class ListBookController {
	@Autowired
	private ListBookService listBookService;

	@Autowired
	private UserService userService;

	@Autowired
	private PublisherService publisherService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BookService bookService;

	@Autowired
	private UploadPathService uploadPathService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ZipFileService zipFileService;

	@Autowired
	private ModuleRunFirstImpl moduleRunFirst;

	@GetMapping("/book")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect, Principal principal) {

		PagedListHolder<?> pages = null;

		int pagesize = 6;
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
			request.getSession().setAttribute("bookListCC", pages);
		else
			request.getSession().setAttribute("bookListCR", pages);

		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/book/page/1";

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

	@GetMapping("/book/page/{pageNumber}")
	public String showBookPage(Authentication authentication, HttpServletRequest request, @PathVariable int pageNumber,
			Model model, ModelMap map, Principal principal) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		map.addAttribute("ok", "FALSE");
		// if (roles != null)
		if (moduleRunFirst.isAdmin(roles))
			map.addAttribute("ok", "TRUE");

		moduleRunFirst.leftBar_cate_pub(model);

		PagedListHolder<?> pages = null;
		int pagesize = 6;
		List<Book> list = null;
		if (principal == null) {
			list = (List<Book>) listBookService.findAll();
			pages = (PagedListHolder<?>) request.getSession().getAttribute("bookListCC");
		} else {
			Person per = userService.findByUsername(principal.getName());
			pages = (PagedListHolder<?>) request.getSession().getAttribute("bookListCR");
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
			request.getSession().setAttribute("bookListCC", pages);
		else
			request.getSession().setAttribute("bookListCR", pages);
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

		return "bookview/listbook";
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	@GetMapping("/book/create")
	public String create(Model model, ModelMap map, Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		moduleRunFirst.leftBar_cate_pub(model);
		model.addAttribute("book", new Book());
		return "bookview/savebook";
	}

	@GetMapping("/book/{id}/edit")
	public String edit(@PathVariable Long id, Model model, ModelMap map, Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);

		if (!moduleRunFirst.isAdmin(roles) && !moduleRunFirst.isUser(roles))
			return "redirect:/";

		if (moduleRunFirst.isUser(roles)) {
			Long personId = userService.findByUsername(authentication.getName()).getId();
			Long personIdInBook = listBookService.findOne(id).getPerson().getId();
			if (personIdInBook != personId)
				return "redirect:/";
		}
		moduleRunFirst.leftBar_cate_pub(model);
		model.addAttribute("book", listBookService.findOne(id));
		return "bookview/savebook";
	}

	@RequestMapping("/book/{id}/download")
	public ResponseEntity<InputStreamResource> download(@PathVariable Long id) throws FileNotFoundException {
		String link = "src/main/resources/static/images/uploads/" + id.toString();
		zipFileService.zipByWinRar(link + ".rar", link);
		File file = new File(link + ".rar");
		Book book = bookService.findBookByID(id);
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, link);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		moduleRunFirst.deleteFile("uploads/", ".rar");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + book.getTitle() + ".rar")
				.contentType(mediaType).body(resource);
	}

	@PostMapping("/book/save")
	public String save(@Valid Book book, BindingResult result, RedirectAttributes redirect, ModelMap map,
			Principal principal, Authentication authentication) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		if (!moduleRunFirst.isAdmin(roles) && !moduleRunFirst.isUser(roles)
				&& !book.getPerson().getUsername().equals(authentication.getName()))
			return "redirect:/";
		if (result.hasErrors()) {
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "bookview/savebook";
		}

		if (book.getPerson() == null)
			book.setPerson(userService.findByUsername(principal.getName()));
		listBookService.save(book);
		redirect.addFlashAttribute("success", "Saved book successfully!");
		return "redirect:/book";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/book/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes redirect, Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		if ((authentication != null) && ((userService.findByUsername(authentication.getName())
				.getId() == listBookService.findOne(id).getPerson().getId()) || (moduleRunFirst.isAdmin(roles)))) {
			bookService.changeUpload((long) 1, id);
			uploadPathService.deleteAllUploadByIdBook(id);
			listBookService.delete(id);

			moduleRunFirst.deleteFile("uploads/" + id.toString() + "/", null);
			moduleRunFirst.deleteFile("booksCover/", null);
			redirect.addFlashAttribute("success", "Deleted book successfully!");
		}
		return "redirect:/book";
	}

	@GetMapping("/book/search/{pageNumber}")
	public String search(@RequestParam("s") String s, Authentication authentication, Model model,
			HttpServletRequest request, @PathVariable int pageNumber, ModelMap map, Principal principal) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model);
		moduleRunFirst.headerFooter(authentication, map, roles);
		if (moduleRunFirst.isAdmin(roles))
			map.addAttribute("ok", "TRUE");
		else
			map.addAttribute("ok", "FALSE");

		if (s.equals("")) {
			return "redirect:/book";
		}

//		List<Book> list = listBookService.search(s);
//		if (list == null) {
//			return "redirect:/book";
//		}

		PagedListHolder<?> pages = null;
		int pagesize = 6;

		List<Book> listBookGet = null;
		if (principal == null) {
			listBookGet = (List<Book>) listBookService.findAll();
		} else {
			Person per = userService.findByUsername(principal.getName());
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

		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}

		if (principal == null)
			request.getSession().setAttribute("bookListCC", pages);
		else
			request.getSession().setAttribute("bookListCR", pages);
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
		return "bookview/listbook";
	}
	
	@GetMapping("/book/{category}/page/{pageNumber}")
	public String showBookPage(Authentication authentication, HttpServletRequest request, @PathVariable("pageNumber") int pageNumber,
			Model model, ModelMap map, Principal principal, @PathVariable("category") String category) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		map.addAttribute("ok", "FALSE");
		// if (roles != null)
		if (moduleRunFirst.isAdmin(roles))
			map.addAttribute("ok", "TRUE");

		moduleRunFirst.leftBar_cate_pub(model);

		PagedListHolder<?> pages = null;
		int pagesize = 6;
		List<Book> list = null;
		if (pageNumber == 1) {
			request.getSession().setAttribute("bookListBCC", null);
			request.getSession().setAttribute("bookListBCC", null);
		}
		if (principal == null) {
			String cate = category.replace("%20", " ");
			Collection<Category> categoryCollection = new ArrayList<Category>(categoryService.findByName(cate));
			list = (List<Book>) bookService.findBookByCategories(categoryCollection);
			pages = (PagedListHolder<?>) request.getSession().getAttribute("bookListBCC");
		} else {
			Person per = userService.findByUsername(principal.getName());
			pages = (PagedListHolder<?>) request.getSession().getAttribute("bookListBCR");
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
			request.getSession().setAttribute("bookListBCC", pages);
		else
			request.getSession().setAttribute("bookListBCR", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/book/"+category+"/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("books", pages);

		return "bookview/listbook";
	}
}
