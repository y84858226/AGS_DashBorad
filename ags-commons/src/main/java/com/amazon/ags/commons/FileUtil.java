package com.amazon.ags.commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class FileUtil {

	// 判断是否图片
	public boolean isImage(File file) {
		boolean flag = false;
		try {
			ImageInputStream is = ImageIO.createImageInputStream(file);
			if (null == is) {
				return flag;
			}
			is.close();
			flag = true;
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return flag;
	}

	// 判断是否图片
	public boolean isImage(String fileName) {
		boolean flag = false;
		if (null == fileName && "".equals(fileName))
			return flag;

		String suf = fileName.substring(fileName.lastIndexOf(".") + 1);
		List<String> fileNamesList = Arrays.asList(new String[] { "jpg", "png", "bmp", "gif" });

		if (fileNamesList.contains(suf)) {
			flag = true;
		}

		return flag;
	}

	public void spiltFile(String path, int splitNum) {
		List<String> lineList = new ArrayList<String>();
		File filePath = new File(path);
		if (filePath.isDirectory()) {
			File[] files = filePath.listFiles();
			for (File file : files) {
				// read file
				BufferedReader br = null;
				int v = 0;
				int fileCount = 0;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String line = br.readLine();
					while ((line = br.readLine()) != null) {
						v += 1;
						lineList.add(line);
						if (v % splitNum == 0) {
							fileCount += 1;
							for (String text : lineList) {
								// write
								writeFile(file.getPath().split("\\.")[0] + "_" + fileCount + ".txt", text);
							}
							lineList.clear();
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null) {
							br.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (lineList.size() > 0) {
					fileCount += 1;
					for (String text : lineList) {
						// write
						writeFile(file.getPath().split("\\.")[0] + "_" + fileCount + ".txt", text);
					}
					lineList.clear();
					file.delete();
				}
			}
		}
	}

	public void writeFile(String fileName, String line) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
			writer.write(line + "\r\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
