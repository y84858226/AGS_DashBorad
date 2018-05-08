package com.amazon.ags.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.amazon.ags.commons.DefectiveAsinMapping;
import com.amazon.ags.commons.FileUtil;
import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.BrowseNodeDefectiveAsin;
import com.amazon.ags.service.BrowseNodeService;

@Controller
public class BrowseNodeMappingController {

	@ResponseBody
	@RequestMapping("/checkDefectiveAsinFile")
	public Map<String, String> findGlCoverage(String sourceCountry, String targetCountry) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("defectiveAsin.txt", "false");
		map.put("browseNode.txt", "false");
		map.put("mappingTable.txt", "false");
		File path = new File("/home/metron/ags_browsenode_tool/" + sourceCountry + "_to_" + targetCountry);
		if (path.isDirectory()) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			File[] files = path.listFiles();
			for (File file : files) {
				Date lastDate = new Date(file.lastModified());
				map.put(file.getName(), format.format(lastDate));
			}
		}
		return map;
	}

	@ResponseBody
	@RequestMapping("/uploadFile")
	public List<String> upload(@RequestParam MultipartFile file, HttpServletRequest request, String sourceCountry,
			String targetCountry) throws IOException {
		List<String> list = new ArrayList<String>();
		if (file != null) {
			String path = "/home/metron/ags_browsenode_tool/" + sourceCountry + "_to_" + targetCountry
					+ "/mappingTable.txt";
			File localFile = new File(path);
			file.transferTo(localFile);
			list.add("success");
		} else {
			list.add("success");
		}
		return list;
	}

	@ResponseBody
	@RequestMapping("/mapDefectiveAsin")
	public List<String> mapDefectiveAsin(String sourceCountry, String targetCountry) {
		DefectiveAsinMapping mapping = new DefectiveAsinMapping();
		mapping.setPath(sourceCountry, targetCountry);

		File mappingTableFile = new File(mapping.getPath() + mapping.getMappingTableFileName());
		File defectiveAsinFile = new File(mapping.getPath() + mapping.getDefectiveAsinFileName());
		File browseNodeFile = new File(mapping.getPath() + mapping.getBrowseNodeFileName());

		if (defectiveAsinFile.exists() && mappingTableFile.exists() && browseNodeFile.exists()) {
			Map<String, String> browseNodeMap = mapping.readBrowseNodeFile(browseNodeFile);
			
			Map<String, String> mappingMap = mapping.readMappingFile(mappingTableFile,browseNodeMap);
			
			mapping.mappingDefectiveAsin(defectiveAsinFile, mappingMap);

			mapping.deduplication();
			
		}
		List<String> list = new ArrayList<String>();
		list.add("success");
		return list;
	}

	@ResponseBody
	@RequestMapping("/downloadFile")
	public void download(String fileName, String sourceCountry, String targetCountry, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try {
			String path = "/home/metron/ags_browsenode_tool/" + sourceCountry + "_to_" + targetCountry + "/";
			InputStream inputStream = new FileInputStream(new File(path + fileName));

			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			// 这里主要关闭。
			os.flush();
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
	}

}
