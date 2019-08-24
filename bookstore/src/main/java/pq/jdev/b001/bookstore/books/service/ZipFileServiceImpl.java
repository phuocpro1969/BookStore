package pq.jdev.b001.bookstore.books.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	public void unzipFileWithLink(String link, String linkZipOut) {
		try {
			File f = new File(link);
			byte[] buffer = new byte[1024];
			File destDir = new File(linkZipOut);
			InputStream is = new FileInputStream(f);
			if (!destDir.exists())
				destDir.mkdir();
			ZipInputStream zis = null;
			if (f.getName().contains(".jar")) {
				BufferedOutputStream dest = null;
				zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(link)));
				ZipEntry entry;
				while ((entry = zis.getNextEntry()) != null) {
					int count;
					if (entry.isDirectory()) {
						new File(linkZipOut + "/" + entry.getName()).mkdirs();
						continue;
					} else {
						int di = entry.getName().lastIndexOf('/');
						if (di != -1) {
							new File(linkZipOut + "/" + entry.getName().substring(0, di)).mkdirs();
						}
					}
					FileOutputStream fos = new FileOutputStream(linkZipOut + "/" + entry.getName());
					dest = new BufferedOutputStream(fos);
					while ((count = zis.read(buffer)) != -1)
						dest.write(buffer, 0, count);
					dest.flush();
					dest.close();
				}
			} else if (f.getName().contains(".zip")) {
				zis = new ZipInputStream(is);
				ZipEntry entry = null;
				while ((entry = zis.getNextEntry()) != null) {
					String zipPath = entry.getName();
					try {
						if (entry.isDirectory()) {
							File zipFolder = new File(linkZipOut + File.separator + zipPath);
							if (!zipFolder.exists()) {
								zipFolder.mkdirs();
							}
						} else {
							File file = new File(linkZipOut + File.separator + zipPath);
							if (!file.exists()) {
								File pathDir = file.getParentFile();
								pathDir.mkdirs();
								file.createNewFile();
							}

							FileOutputStream fos = new FileOutputStream(file);
							int bread;
							while ((bread = zis.read()) != -1) {
								fos.write(bread);
							}
							fos.close();
						}
					} catch (Exception e) {
						continue;
					}
				}
				zis.close();
				is.close();
			} else {
				try {
					String cmd = "src\\main\\resources\\static\\winrar\\WinRAR.exe";
					String unrarCmd = cmd + " x -r -p- -o+ " + f.getAbsolutePath() + " " + destDir.getAbsolutePath();

					Runtime rt = Runtime.getRuntime();
					Process pre = rt.exec(unrarCmd);
					InputStreamReader isr = new InputStreamReader(pre.getInputStream());
					BufferedReader bf = new BufferedReader(isr);
					String line = null;
					while ((line = bf.readLine()) != null) {
						line = line.trim();
						if ("".equals(line)) {
							continue;
						}
						System.out.println(line);
					}

					bf.close();
				} catch (Exception e) {
					System.out.println("Unpacking exception");
				}
			}
		} catch (Exception e) {
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
