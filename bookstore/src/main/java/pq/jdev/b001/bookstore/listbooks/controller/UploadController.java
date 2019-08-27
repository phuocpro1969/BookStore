package pq.jdev.b001.bookstore.listbooks.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.users.service.ModuleRunFirst;

@Controller

public class UploadController {
	
	@Autowired
	private ModuleRunFirst moduleRunFirst;

//	@Autowired
//	private ZipFileService zipFileService;

	@GetMapping("/upload")
	public String viewUpLoad(Authentication authentication, ModelMap map, Model model) {

		List<String> roles = moduleRunFirst.getRole(authentication);
		moduleRunFirst.leftBar_cate_pub(model);
		moduleRunFirst.headerFooter(authentication, map, roles);

//		zipFileService.unzipFileWithLink("src\\main\\resources\\static\\images\\noImage2.rar", "D:\\page");
//		File f1 = new File("src/main/resources/static","images/uploads/3");
//		File f2 = new File("src/main/resources/static","images/booksCover/");

//		zipFileService.zipDirectory(f1.toString(), f2.toString()+"\\3.zip" );
		return "upload";
	}

	@PostMapping("/upload") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			// Get the file and save them to src/main/resources/static/images
			byte[] bytes = file.getBytes();
			File f = new File("src/main/resources/static/images");
			Path path = Paths.get(f.toString() + file.getOriginalFilename());
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/";
	}

//    @GetMapping("/uploadStatus")
//    public String uploadStatus() {
//        return "uploadStatus";
//    }

}