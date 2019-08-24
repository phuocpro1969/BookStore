package pq.jdev.b001.bookstore.books.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pq.jdev.b001.bookstore.books.repository.BookRepository;
import pq.jdev.b001.bookstore.books.repository.UploadRepository;

@Service
@Transactional
public class UploadPathServiceImpl implements UploadPathService {

	@Autowired
	UploadRepository uploadRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	ResourceLoader resourceLoader;

	@Override
	public void deleteAllUploadByIdBook(Long id) {
		uploadRepository.deleteByIdBook(id);
	}

	@Override
	public File getFilePath(String modifiedFileName, String path) {
		File f = new File("src/main/resources/static/images", path);
		/*
		 * ClassLoader classLoader = getClass().getClassLoader();
		 * File f = new File(classLoader.getResource("/static/images").getFile());
		 * get file in output source of src/main/resource in here it auto update but it don't save;
		 */
		if (!f.exists())
			f.mkdir();
		String md = f + File.separator + modifiedFileName;
		File file = new File(md);
		return file;
	}
}
