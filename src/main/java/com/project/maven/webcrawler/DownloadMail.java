package com.project.maven.webcrawler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadMail{
	String filename="filename.txt";
	static int i=1;

	public void downloadMail(String url) {
		{
			BufferedInputStream in = null;
			FileOutputStream fout = null;
			try {
				in = new BufferedInputStream(new URL(url).openStream());
				File dir = new File("C:\\Users\\surabhiv\\Desktop\\2014");
				dir.mkdirs();
				
				File file = new File(dir, filename+i);
				i++;
				fout = new FileOutputStream(file);

				byte data[] = new byte[1024];
				int count;
				while ((count = in.read(data, 0, 1024)) != -1) {
					fout.write(data, 0, count);
				}
			} catch (Exception e) {
				System.out.println("Cannot download file ");
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if (fout != null)
					try {
						fout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
			}
		}
	}

}
