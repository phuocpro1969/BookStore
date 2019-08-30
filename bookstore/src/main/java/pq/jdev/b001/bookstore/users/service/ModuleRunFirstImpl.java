package pq.jdev.b001.bookstore.users.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.service.CategoryService;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.publishers.service.PublisherService;
import pq.jdev.b001.bookstore.users.model.Person;

@Service("FirstRunService")
@Transactional
public class ModuleRunFirstImpl implements ModuleRunFirst {

//	@Autowired
//	private ListBookService listBookService;
//
	@Autowired
	private UserService userService;

	@Autowired
	private PublisherService publisherService;

//	@Autowired
//	private BookService bookService;

	@Autowired
	private CategoryService categoryservice;

	@Override
	public boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_EMPLOYEE"))
			return true;
		return false;
	}

	@Override
	public boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) 
			return true;
		return false;
	}

	@Override
	public boolean is(String a, String b) {
		a = unAccent(a);
		b = unAccent(b);
		b.replace("+", " ");
		b.replace("%20", " ");
		b = b.toLowerCase();
		a = a.toLowerCase();
		return b.equalsIgnoreCase(a);
	}

	@Override
	public boolean error(String a, String b) {
		a = unAccent(a);
		b = unAccent(b);
		System.out.println(a);
		System.out.println(b);
		b.replace("%20", "+");
		b = b.toLowerCase();
		a = a.toLowerCase();
		String[] arr = b.split("\\+");
		System.out.println(a);
		System.out.println(arr.toString());
		for (String item : arr)
			if (a.contains(item))
				return true;
		return false;
	}

	@Override
	public String unAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "d");
	}

	@Override
	public void headerFooter(Authentication authentication, ModelMap map, List<String> roles) {
		if (authentication != null) {
			Person p = userService.findByUsername(authentication.getName());
			String img = p.getPicture();
			String fn = p.getFirstname() +" "+p.getLastname();
			map.addAttribute("image",img);
			map.addAttribute("fullname", fn);
			if (isUser(roles)) {
				map.addAttribute("header", "header_user");
				map.addAttribute("footer", "footer_user");
			} else if (isAdmin(roles)) {
				map.addAttribute("header", "header_admin");
				map.addAttribute("footer", "footer_admin");
			}
		} else {
			map.addAttribute("image","noImage");
			map.addAttribute("fullname", "person");
			map.addAttribute("header", "header_login");
			map.addAttribute("footer", "footer_login");
		}
	}

	@Override
	public List<String> getRole(Authentication authentication) {
		List<String> roles = new ArrayList<String>();
		if (authentication == null) {
			roles.add(null);
			return roles;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}
		return roles;
	}

	@Override
	public void leftBar_cate_pub(Model model, int num) {
		int pagesizeCP = num;
		PagedListHolder<?> pagePubs = null;
		PagedListHolder<?> pageCates = null;
		List<Publishers> listPub = (List<Publishers>) publisherService.findAll();
		List<Category> categoryList = categoryservice.findAll();
		if (pageCates == null) {
			pageCates = new PagedListHolder<>(categoryList);
			pageCates.setPageSize(pagesizeCP);
		}
		if (pagePubs == null) {
			pagePubs = new PagedListHolder<>(listPub);
			pagePubs.setPageSize(pagesizeCP);
		}
		model.addAttribute("publishers", pagePubs);
		model.addAttribute("categories", pageCates);
	}

	@Override
	public void deleteFile(String linkImagestoFolder, String ex) {
		File folder = new File("src/main/resources/static/images/", linkImagestoFolder);
		if (folder.exists() && folder.isDirectory()) {
			for (File f : folder.listFiles())
				if (ex == null)
					f.delete();
				else if (f.getAbsolutePath().endsWith(ex)) {
					f.delete();
					if (f.exists())
						f.deleteOnExit();
				}
		}

	}

	@Override
	public Timestamp getTimeNow() {
		java.util.Date date = new java.util.Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		return ts;
	}

}
