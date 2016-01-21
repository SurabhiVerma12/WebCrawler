package com.project.maven.webcrawler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

public class DownloadMailThread implements Runnable {


	static int i=1;
	int size = 1024;
	String year=null;
	BlockingQueue<String> downloadURLQueue = null;
	volatile static boolean finishedcrawler = false;

	
	public static void setFinishedcrawler(boolean val) {
		finishedcrawler = val;
	}

	private static final Logger LOGGER = Logger.getLogger(DownloadMailThread.class);
	
	public DownloadMailThread(BlockingQueue<String> downloadURLQueue,String year){
		this.downloadURLQueue=downloadURLQueue;
		this.year =year;
	}

	public void downloadMail(String url,String destinationFolder,String filename) {
		OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;
		String path="C:\\Users\\surabhiv\\Desktop\\Mail"+destinationFolder;
		try {
			File dir = new File(path);
			dir.mkdirs();
			URL url1;
			byte[] buf;
			int byteRead, byteWritten = 0;
			url1 = new URL(url);
			outStream = new BufferedOutputStream(new FileOutputStream(dir + "\\" + filename+"-"+i));
			i++;
			uCon = url1.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
			LOGGER.debug("Downloaded Successfully.");

			LOGGER.debug("File name:\"" +  filename+"-"+i + "\"\nNo ofbytes :" + byteWritten);
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

	public void run() {
		// TODO Auto-generated method stub
		while(!DownloadMailThread.finishedcrawler){
			String url;
			try {
				url = downloadURLQueue.take();
				downloadMail(url,year,"MailDownload-"+year);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}


}
