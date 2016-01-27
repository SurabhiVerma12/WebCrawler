package com.project.maven.webcrawler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class DownloadMail {

	static int i = 1;
	int size = 1024;
	private static final Logger LOGGER = Logger.getLogger(DownloadMail.class);

	public void downloadMail(String url, String destinationFolder, String filename) {
		OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;
		String path = "C:\\Users\\surabhiv\\Desktop\\Mail" + destinationFolder;
		try {
			File dir = new File(path);
			dir.mkdirs();
			URL url1;
			byte[] buf;
			int byteRead, byteWritten = 0;
			url1 = new URL(url);
			outStream = new BufferedOutputStream(new FileOutputStream(dir + "\\" + filename + "-" + i));
			i++;
			uCon = url1.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
			LOGGER.debug("Downloaded Successfully.");

			LOGGER.debug("File name:\"" + filename + "-" + i + "\"\nNo ofbytes :" + byteWritten);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		} finally {
			try {
				is.close();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error(e);
			}
		}
	}
}
