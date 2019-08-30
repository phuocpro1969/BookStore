package pq.jdev.b001.bookstore.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.service.ModuleRunFirst;
import pq.jdev.b001.bookstore.users.service.UserService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModuleRunFirst moduleRunFirst;

	@ModelAttribute("dto")
	public UploadInformationDTO dTO() {
		return new UploadInformationDTO();
	}

	@GetMapping("/bookview")
	public String loadGuestView(Model model) {
		model.addAttribute("books", bookService.viewAllBooks());
		return "/book/page/1";
	}

	@GetMapping("/bookview/books")
	public String loadInformationGuestView(Model model) {
		model.addAttribute("books", bookService.viewAllBooks());
		return "book/page/1";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview/createform")
	public String initializeCreating(@AuthenticationPrincipal User user, UploadInformationDTO dto, Model model,
			RedirectAttributes redirectAttributes, Authentication authentication, ModelMap map) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		try {
			moduleRunFirst.headerFooter(authentication, map, roles);
			Person currentUser = new Person();
			currentUser = userService.findByUsername(user.getUsername());
			dto.setPublishers(bookService.showAllPublishers());
			dto.setCategories(bookService.showAllCategories());
			model.addAttribute("dto", dto);
			model.addAttribute("currentUser", currentUser);
			return "bookview/createform";
		} catch (Exception e) {
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "bookview/error";
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@PostMapping("/bookview/createform")
	public String createBook(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@RequestParam(value = "idChecked", required = false) List<String> categoriesId, Model model, ModelMap map,
			Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		try {
			moduleRunFirst.headerFooter(authentication, map, roles);
			Person currentUser = userService.findByUsername(user.getUsername());
			if (!bookService.checkInput(dto)) {
				moduleRunFirst.headerFooter(authentication, map, roles);
				return "bookview/error";
			}
			bookService.save(dto, currentUser, categoriesId);
			return "bookview/success";
		} catch (Exception e) {
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "bookview/error";
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview/editform/{id}")
	public String initializeEditing(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@PathVariable("id") String id, Model model, Authentication authentication, ModelMap map) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		try {
			moduleRunFirst.headerFooter(authentication, map, roles);
			dto = new UploadInformationDTO();
			Person currentUser = new Person();
			Long idEditBook = Long.parseLong(id);
			Book editBook = bookService.findBookByID(idEditBook);
			currentUser = userService.findByUsername(user.getUsername());
			if (!bookService.checkRightInteraction(user, editBook)) {
				moduleRunFirst.headerFooter(authentication, map, roles);
				return "/bookview/error";
			}
			model.addAttribute("currentUser", currentUser);
			dto.setId(editBook.getId());
			dto.setTitle(editBook.getTitle());
			dto.setPrice(editBook.getPrice());
			dto.setDomain(editBook.getDomain());
			dto.setAuthors(editBook.getAuthors());
			dto.setPublishers(bookService.showAllPublishers());
			String currentSelected = editBook.getPublisher().getPublisher();
			dto.setPublisherName(currentSelected);
			map.addAttribute("currentSelected", currentSelected);
			dto.setPublishedYear(editBook.getPublishedYear());
			dto.setSelectCategories(bookService.showAllCategoriesWithFlag(editBook));
			dto.setDescription(editBook.getDescription());
			model.addAttribute("id", editBook.getId());
			model.addAttribute("dto", dto);
			return "bookview/editform";
		} catch (Exception e) {
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "/bookview/error";
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@PostMapping("/bookview/editform")
	public String editBook(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@RequestParam("id") String editBookId,
			@RequestParam(value = "idChecked", required = false) List<String> categoriesId, Model model,
			RedirectAttributes redirectAttributes, ModelMap map, Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);

		try {
			moduleRunFirst.headerFooter(authentication, map, roles);
			Person currentUser = new Person();
			Long id = Long.parseLong(editBookId);
			Book editBook = bookService.findBookByID(id);
			currentUser = userService.findByUsername(user.getUsername());
			if (!bookService.checkRightInteraction(user, editBook)) {
				return "redirect:/";
			}
			bookService.update(dto, currentUser, categoriesId, editBook);
			return "bookview/success";
		} catch (Exception e) {
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "bookview/error";
		}
	}

	@GetMapping("/bookview/infor/{id}")
	public String showBookDetails(UploadInformationDTO dto, @PathVariable("id") String id, Model model, ModelMap map,
			Authentication authentication) {
		List<String> roles = moduleRunFirst.getRole(authentication);
		try {
			moduleRunFirst.leftBar_cate_pub(model,15);
			moduleRunFirst.headerFooter(authentication, map, roles);
			model.addAttribute("book", new Book());

			Long idCurrent = Long.parseLong(id);
			Book currentBook = bookService.findBookByID(idCurrent);
			dto.setId(currentBook.getId());
			dto.setTitle(currentBook.getTitle());
			dto.setPrice(currentBook.getPrice());
			dto.setDomain(currentBook.getDomain());
			dto.setAuthors(currentBook.getAuthors());
			String publisher = currentBook.getPublisher().getPublisher();
			dto.setPublishedYear(currentBook.getPublishedYear());
			dto.setUploadedDate(currentBook.getUploadedDate());
			String stringCategories = "";
			for (Category category : currentBook.getCategories()) {
				stringCategories = stringCategories + category.getName() + ", ";
			}
			model.addAttribute("stringCategories", stringCategories);
			dto.setDescription(currentBook.getDescription());
			model.addAttribute("publisher", publisher);
			model.addAttribute("book", currentBook);
			model.addAttribute("dto", dto);
			model.addAttribute("id", id);
			return "bookview/infor";
		} catch (Exception e) {
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "bookview/error";
		}

	}
}
