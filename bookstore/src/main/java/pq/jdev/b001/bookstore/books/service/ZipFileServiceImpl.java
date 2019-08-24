package pq.jdev.b001.bookstore.books.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;

@Controller
@Transactional
public class ZipFileServiceImpl implements ZipFileService {
	List<String> filesListInDir = new ArrayList<String>();

	/**
	 * This method zips the directory
	 * 
	 * @param dir
	 * @param zipDirName
	 */
	public void zipDirectory(String linkDirectory, String linkZipOut) {
		try {
			FileOutputStream fos = new FileOutputStream(linkZipOut);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			File fileToZip = new File(linkDirectory);

			zipFile(fileToZip, fileToZip.getName(), zipOut);
			zipOut.close();
			fos.close();
		} catch (Exception e) {
		}
		
	}
	
	private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		if (fileToZip.isDirectory()) {
			if (fileName.endsWith("/")) {
				zipOut.putNextEntry(new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(new ZipEntry(fileName + "/"));
				zipOut.closeEntry();
			}
			File[] children = fileToZip.listFiles();
			for (File childFile : children) {
				zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
			}
			return;
		}
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}
	
	/**
	 * This method populates all the files in a directory to a List
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public int populateFilesList(File dir) throws IOException {
		File[] files = dir.listFiles();
		int regularFile = 0;
		for (File file : files) {
			if (file.isFile()) {
				filesListInDir.add(file.getAbsolutePath());
				regularFile++;
			} else
				populateFilesList(file);
		}
		return regularFile;
	}

	/**
	 * This method compresses the single file to zip format
	 * 
	 * @param file
	 * @param zipFileName
	 */
	public void zipSingleFile(String link, String zipFileName) {
		try {
			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			File fileToZip = new File(link);
			FileInputStream fis = new FileInputStream(fileToZip);
			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			zipOut.close();
			fis.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	   public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
	        File destFile = new File(destinationDir, zipEntry.getName());
	         
	        String destDirPath = destinationDir.getCanonicalPath();
	        String destFilePath = destFile.getCanonicalPath();
	         
	        if (!destFilePath.startsWith(destDirPath + File.separator)) {
	            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
	        }
	         
	        return destFile;
	    }

	@Override
	public void unzipFileWithLink(String link , String linkZipOut) {
		try {
			File destDir = new File(linkZipOut);
			if (!destDir.exists())
				destDir.mkdir();
			 byte[] buffer = new byte[1024];
		        ZipInputStream zis = new ZipInputStream(new FileInputStream(link));
		        ZipEntry zipEntry = zis.getNextEntry();
		        while (zipEntry != null) {
		            File newFile = newFile(destDir, zipEntry);
		            FileOutputStream fos = new FileOutputStream(newFile);
		            int len;
		            while ((len = zis.read(buffer)) > 0) {
		                fos.write(buffer, 0, len);
		            }
		            fos.close();
		            zipEntry = zis.getNextEntry();
		        }
		        zis.closeEntry();
		        zis.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void zipMultiFile(List<String> list, String zipFileName) {
		try {
			List<String> srcFiles = list;
			FileOutputStream fos = new FileOutputStream(zipFileName);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			for (String srcFile : srcFiles) {
				File fileToZip = new File(srcFile);
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();
			fos.close();
		} catch (Exception e) {
		}

	}
}
