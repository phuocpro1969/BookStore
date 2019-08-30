package pq.jdev.b001.bookstore.users.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

public interface ModuleRunFirst {
	boolean isUser(List<String> roles);
	boolean isAdmin(List<String> roles);
	boolean is(String a, String b);
	boolean error(String a, String b);
	String unAccent(String s);
	void headerFooter(Authentication authentication, ModelMap map, List<String> roles);
	void leftBar_cate_pub(Model model, int num);
	List<String> getRole(Authentication authentication);
	void deleteFile(String linkImagestoFolder, String extensionOFile);
	Timestamp getTimeNow();
}
