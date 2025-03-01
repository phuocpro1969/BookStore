package pq.jdev.b001.bookstore.users.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.model.Role;
import pq.jdev.b001.bookstore.users.service.ModuleRunFirst;
import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.AdminUpdateInfoUserDto;
import pq.jdev.b001.bookstore.users.web.dto.UserUpdateInfoDto;

@Controller
@RequestMapping("/listUser")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private ModuleRunFirst moduleRunFirst;

	// tao list
	@ModelAttribute("list")
	public List<Person> getList(Principal principal) {
		if (principal == null) {
			return null;
		}
		String username = principal.getName();
		Person per = userService.findByUsername(username);
		List<Person> oldList = userService.findAll();
		List<Person> newList = new ArrayList<Person>();
		int key = per.getPower();
		for (Person p : oldList)
			if (p.getPower() <= key) {
				newList.add(p);
			}
		return newList;
	}

	@ModelAttribute("singleSelectAllValues")
	public String[] getSingleSelectAllValues() {
		return new String[] { "Male", "Female" };
	}

	@ModelAttribute("allRoles")
	public List<String> allRoles(Principal principal) {
		if (principal == null)
			return null;
		ArrayList<String> kq = new ArrayList<>();
		String username = principal.getName();
		Person per = userService.findByUsername(username);
		Set<Role> roles = per.getRoles();
		String key = null;
		for (Role role : roles) {
			key = role.getName();
		}
		List<Role> list = userService.findAllRole();
		for (Role r : list) {
			String ss = r.getName();
			if (!ss.equals(key)) {
				kq.add(ss.substring(5));
			} else {
				kq.add(ss.substring(5));
				break;
			}
		}
		return kq;
	}

	@GetMapping
	public String showUpdateInfoForm(Principal principal) {
		Role r = userService.findByIdRole(userService.findByUsername(principal.getName()).getPower());
		if (r.getName().equals("ROLE_EMPLOYEE"))
			return "redirect:/";
		return "redirect:/listUser/page/1";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/page/{pageNumber}")
	public String showListUser(HttpServletRequest request, @PathVariable int pageNumber, Model model, ModelMap map,
			Principal principal, Authentication authentication) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		moduleRunFirst.leftBar_cate_pub(model);
		List<Person> list = (List<Person>) getList(principal);

		int pagesize = 7;
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
		String baseUrl = "/listUser/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("list", pages);

		return "user_admin/admin/listUser";
	}

	// update user
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/edit-user-{id}")
	public String showUpdateInfoForm(Model model, ModelMap map, Principal principal, @PathVariable long id,
			Authentication authentication) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		List<Person> list = getList(principal);
		for (Person p : list)
			if (p.getId() == id) {
				AdminUpdateInfoUserDto uuid = userService.updateUserInfo(p);
				model.addAttribute("person", uuid);
			}
		return "user_admin/admin/adminUpdateUser";
	}

	@PostMapping(value = "/edit-user-{id}")
	public String updateUserAccount(@PathVariable long id, Model model, Principal principal,
			@ModelAttribute("person") @Valid AdminUpdateInfoUserDto userDto, BindingResult result, ModelMap map,
			Authentication authentication) throws Exception {

		if (result.hasErrors()) {
			List<String> roles = moduleRunFirst.getRole(authentication);
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "user_admin/admin/adminUpdateUser";
		}
		userService.save(userDto);

		userService.autoLogin(principal.getName());

		return "redirect:/listUser";
	}

	// change password

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/edit-user-{id}/changePassword", method = RequestMethod.GET)
	public String showChangePassForm(@PathVariable long id, Model model, ModelMap map, Authentication authentication,
			Principal principal) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		List<Person> list = getList(principal);
		for (Person p : list)
			if (p.getId() == id) {
				UserUpdateInfoDto uuid = userService.updateInfo(p);
				model.addAttribute("person", uuid);
			}
		return "user_admin/admin//adminChangePassword";
	}

	@RequestMapping(value = "/edit-user-{id}/changePassword", method = RequestMethod.POST)
	public String UpdatePassUserAccount(@PathVariable long id,
			@ModelAttribute("person") @Valid UserUpdateInfoDto userDto, BindingResult result, ModelMap map,
			Principal principal, Authentication authentication) {
		if (principal == null)
			return "redirect:/";
		String url = "";
		if (result.hasErrors()) {
			List<String> roles = moduleRunFirst.getRole(authentication);
			moduleRunFirst.headerFooter(authentication, map, roles);
			return "user_admin/admin/adminChangePassword";
		}

		String updatedPassword = passwordEncoder.encode(userDto.getPassword());
		userService.updatePassword(updatedPassword, userDto.getId());
		userService.loadUserByUsername(userDto.getUserName());
		url = "";
		url = "redirect:/listUser/edit-user-" + String.valueOf(id) + "/changePassword?success";
		return url;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/search/{pageNumber}")
	public String searchUser(HttpServletRequest request, @RequestParam("keyword") String kw,
			@PathVariable("pageNumber") int pageNumber, Model model, ModelMap map, Principal principal, Authentication authentication
			) {

		if (principal == null)
			return "redirect:/";

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.headerFooter(authentication, map, roles);
		moduleRunFirst.leftBar_cate_pub(model);

		if (kw.equals("")) {
			return "redirect:/listUser";
		}

//		List<Person> listUserGet = (List<Person>) getList(principal);
		List<Person> list = new ArrayList<Person>();
		list = search(kw, "firstname");
		for (Person p : (List<Person>) search(kw, "username"))
			if (!list.contains(p))
				list.add(p);
//		for (Person a : listUserGet) {
//			if (is(a.getId().toString(), kw) || is(a.getUsername(), kw) || is(a.getFirstname(), kw)
//					|| is(a.getLastname(), kw) || is(a.getAddress(), kw))
//				list.add(a);
//		}
//
//		for (Person a : listUserGet) {
//			if (error(a.getId().toString(), kw) || error(a.getFirstname(), kw) || error(a.getLastname(), kw)
//					|| error(a.getAddress(), kw))
//				if (!list.contains(a))
//					list.add(a);
//		}

		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listU");
		int pagesize = 7;

		// if (pages == null) {
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}

		request.getSession().setAttribute("listU", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/listUser/search/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("list", pages);

		return "user_admin/admin/listUser";
	}

	@SuppressWarnings("unchecked")
	public List<Person> search(String kw, String field) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Person.class)
				.get();
		List<Person> listNew = new ArrayList<>();
		List<Person> list = new ArrayList<>();
		try {
			org.apache.lucene.search.Query query = queryBuilder.keyword().wildcard().onField(field).matching(kw)
					.createQuery();
			org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
					Person.class);
			listNew = jpaQuery.getResultList();
			for (Person p : listNew)
				if (!list.contains(p))
					list.add(p);
		} catch (Exception e) {
		}

		try {
			org.apache.lucene.search.Query fuzzyQuery = queryBuilder.keyword().fuzzy().onField(field).matching(kw)
					.createQuery();
			org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(fuzzyQuery,
					Person.class);
			listNew = jpaQuery.getResultList();
			for (Person p : listNew)
				if (!list.contains(p))
					list.add(p);
		} catch (Exception e) {
		}

		try {
			org.apache.lucene.search.Query phraseQuery = queryBuilder.phrase().withSlop(100000).onField(field)
					.ignoreAnalyzer().ignoreFieldBridge().sentence(kw).createQuery();

			org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(phraseQuery,
					Person.class);
			List<Person> list3 = new ArrayList<Person>();
			list3 = jpaQuery.getResultList();
			for (Person p : list3)
				if (!list.contains(p))
					list.add(p);
		} catch (Exception e) {
		}

		try {
			org.apache.lucene.search.Query query = queryBuilder.phrase().withSlop(1000).onField(field).sentence(kw)
					.createQuery();
			org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
					Person.class);
			listNew = jpaQuery.getResultList();
			for (Person p : listNew)
				if (!list.contains(p))
					list.add(p);
		} catch (Exception e) {
		}
		return list;
	}

	// delete user
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = { "/delete-user-{id}" })
	public String deleteUser(@PathVariable long id, Principal principal, HttpServletRequest request,
			HttpServletResponse response) {

		if (principal == null)
			return "redirect:/";
		if (id != 1) {
			int key_user_delete = userService.findById(id).getPower();
			Person p = userService.findByUsername(principal.getName());
			int key_admin = p.getPower();

			if (id == p.getId()) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth != null) {
					new SecurityContextLogoutHandler().logout(request, response, auth);
				}
			}

			if (key_admin >= key_user_delete) {
				userService.changeAuthorize(p.getId(), id);
				userService.deleteTokenByIdPerson(id);
				userService.delete(id);
			}
		}
		return "redirect:/listUser";
	}
}
