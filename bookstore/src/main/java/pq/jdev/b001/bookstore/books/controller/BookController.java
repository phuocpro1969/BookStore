package pq.jdev.b001.bookstore.books.controller;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.users.model.Person;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@ModelAttribute("dto")
	public UploadInformationDTO dTO() {
		return new UploadInformationDTO();
	}

	@PostMapping("/bookview")
	public String loadGuestView(Model model) {
		model.addAttribute("books", bookService.viewAllBooks());
		return "/bookview/books";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview")
	public String loadUserView(@AuthenticationPrincipal User user, Model model) throws Exception {
		String role = "guest";
		try {
			Person currentUser = bookService.findCurrentUser(user);
			if (currentUser.getPower() == 2) {
				role = "admin";
			} else if (currentUser.getPower() == 1) {
				role = "employee";
			}
			model.addAttribute("currentUser", currentUser);
		} catch (AuthenticationException e) {
			return "/bookview/books";
		}
		model.addAttribute("role", role);
		model.addAttribute("books", bookService.viewAllBooks());
		return "/bookview/information";
	}

	@PostMapping("/bookview/books")
	public String loadInformationGuestView(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("books", bookService.viewAllBooks());
		return "bookview/books";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview/information")
	public String loadInformationUserView(@AuthenticationPrincipal User user, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		String role = "guest";
		try {
			Person currentUser = bookService.findCurrentUser(user);
			if (currentUser.getPower() == 2) {
				role = "admin";
			} else if (currentUser.getPower() == 1) {
				role = "employee";
			}

			model.addAttribute("currentUser", currentUser);
		} catch (AuthenticationException e) {
			return "/bookview/books";
		}
		model.addAttribute("role", role);
		model.addAttribute("books", bookService.viewAllBooks());
		return "bookview/information";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview/createform")
	public String initializeCreating(@AuthenticationPrincipal User user, UploadInformationDTO dto, Model model,
			RedirectAttributes redirectAttributes) {
		Person currentUser = new Person();
		try {
			currentUser = bookService.findCurrentUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return "/bookview/books";
		}
		dto.setPublishers(bookService.showAllPublishers());
		dto.setCategories(bookService.showAllCategories());
		model.addAttribute("dto", dto);
		model.addAttribute("currentUser", currentUser);
		return "bookview/createform";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@PostMapping("/bookview/createform")
	public String creatBook(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@RequestParam(value = "idChecked", required = false) List<String> categoriesId, Model model) {
		try {
			Person currentUser = new Person();
			try {
				currentUser = bookService.findCurrentUser(user);
				if (!bookService.checkInput(dto)) {
					return "bookview/error";
				}
			} catch (NullPointerException e) {
				return "/bookview/books";
			}
			bookService.save(dto, currentUser, categoriesId);
			return "bookview/success";

		} catch (Exception e) {
			return "bookview/error";
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview/editform/{id}")
	public String initializeEditing(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@PathVariable("id") String id, Model model) {
		try {
			dto = new UploadInformationDTO();
			Person currentUser = new Person();
			Long idEditBook = Long.parseLong(id);
			Book editBook = bookService.findBookByID(idEditBook);
			try {
				currentUser = bookService.findCurrentUser(user);
				if (!bookService.checkRightInteraction(user, editBook)) {
					return "/bookview/books";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "/bookview/books";
			}
			try {
				model.addAttribute("currentUser", currentUser);
				dto.setTitle(editBook.getTitle());
				dto.setPrice(editBook.getPrice());
				dto.setDomain(editBook.getDomain());
				dto.setAuthors(editBook.getAuthors());
				dto.setPublishers(bookService.showAllPublishers());
				String currentSelected = editBook.getPublisher().getUpdateName();
				model.addAttribute("currentSelected", currentSelected);
				dto.setSelectCategories(bookService.showAllCategoriesWithFlag(editBook));
				model.addAttribute("id", editBook.getId());
				model.addAttribute("dto", dto);
				return "bookview/editform";
			} catch (Exception e) {
				e.printStackTrace();
				return "/bookview/books";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/bookview/books";
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@PostMapping("/bookview/editform")
	public String editBook(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@RequestParam("id") String editBookId,
			@RequestParam(value = "idChecked", required = false) List<String> categoriesId, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Person currentUser = new Person();
			Long id = Long.parseLong(editBookId);
			Book editBook = bookService.findBookByID(id);
			try {
				currentUser = bookService.findCurrentUser(user);
				if (!bookService.checkRightInteraction(user, editBook)) {
					return "/bookview/books";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "/bookview/books";
			}
			bookService.update(dto, currentUser, categoriesId, editBook);
			return "bookview/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "bookview/error";
		}
	}

	@GetMapping("/bookview/error")
	public String failedNoticeCreated(Model model) {
		return "bookview/error";
	}

	@GetMapping("/bookview/success")
	public String successfulNoticeCreated(Model model) {
		return "bookview/error";
	}
}
