package pq.jdev.b001.bookstore.books.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ZipFileService {

	public void zipDirectory(String linkDirectory, String linkZipOut);

	public int populateFilesList(File dir) throws IOException;

	public void zipSingleFile(String link, String zipFileName);
	
	public void zipMultiFile(List<String> list, String zipFileName);
	
	public void unzipFileWithLink(String link, String linkZipOut);
	
	public void zipByWinRar(String linkFile, String linkZipTo);
}
