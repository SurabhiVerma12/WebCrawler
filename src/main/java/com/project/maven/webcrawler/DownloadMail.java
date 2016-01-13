package com.project.maven.webcrawler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadMail{
	String filename="filename";
	static int i=1;
	int size = 1024;

	public void downloadMail(String url) {
        OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;
		try {
		    File dir = new File("C:\\Users\\surabhiv\\Desktop\\Mails");
			dir.mkdirs();
            URL url1;
			byte[] buf;
			int byteRead, byteWritten = 0;
			url1 = new URL(url);
			outStream = new BufferedOutputStream(new FileOutputStream(dir + "\\" + filename+i));
			i++;
			uCon = url1.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
			System.out.println("Downloaded Successfully.");
			System.out.println("File name:\"" + filename + "\"\nNo ofbytes :" + byteWritten);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
