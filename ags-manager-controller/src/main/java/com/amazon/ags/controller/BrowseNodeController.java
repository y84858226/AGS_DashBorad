package com.amazon.ags.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.BrowseNodeDefectiveAsin;
import com.amazon.ags.service.BrowseNodeService;
import com.csvreader.CsvWriter;

@Controller
public class BrowseNodeController {
	@Resource
	private BrowseNodeService service;

	@ResponseBody
	@RequestMapping("/findSourceCountry")
	public List<BrowseNodeCoverage> findSourceCountry() {
		List<BrowseNodeCoverage> sourceCountry = service.findSourceCountry();
		return sourceCountry;
	}

	@ResponseBody
	@RequestMapping("/findTargetCountry")
	public List<BrowseNodeCoverage> findTargetCountry(BrowseNodeCoverage browseNodeCoverage) {
		List<BrowseNodeCoverage> targetCountry = service.findTargetCountry(browseNodeCoverage);
		return targetCountry;
	}

	@ResponseBody
	@RequestMapping("/findYear")
	public List<BrowseNodeCoverage> findYear(BrowseNodeCoverage browseNodeCoverage) {
		List<BrowseNodeCoverage> year = service.findYear(browseNodeCoverage);
		return year;
	}

	@ResponseBody
	@RequestMapping("/findWeek")
	public List<BrowseNodeCoverage> findSourceCountry(BrowseNodeCoverage browseNodeCoverage) {
		List<BrowseNodeCoverage> week = service.findWeek(browseNodeCoverage);
		return week;
	}

	@ResponseBody
	@RequestMapping("/findPL")
	public List<BrowseNodeCoverage> findPL(BrowseNodeCoverage browseNodeCoverage, String[] weeks) {
		List<BrowseNodeCoverage> pl = service.findPL(browseNodeCoverage, weeks);
		return pl;
	}

	@ResponseBody
	@RequestMapping("/findGL")
	public List<BrowseNodeCoverage> findGL(BrowseNodeCoverage browseNodeCoverage, String[] weeks, String[] pls) {
		List<BrowseNodeCoverage> GL = service.findGL(browseNodeCoverage, weeks, pls);
		return GL;
	}

	@ResponseBody
	@RequestMapping("/findWeekCoverage")
	public List<BrowseNodeCoverage> findWeekCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks) {
		List<BrowseNodeCoverage> weekCoverage = service.findWeekCoverage(browseNodeCoverage, weeks);
		return weekCoverage;
	}

	@ResponseBody
	@RequestMapping("/findPlCoverage")
	public List<BrowseNodeCoverage> findPlCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks,
			String[] pls) {
		List<BrowseNodeCoverage> plCoverage = service.findPlCoverage(browseNodeCoverage, weeks, pls);
		return plCoverage;
	}

	@ResponseBody
	@RequestMapping("/findGlCoverage")
	public List<BrowseNodeCoverage> findGlCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks,
			String[] gls) {
		List<BrowseNodeCoverage> glCoverage = service.findGlCoverage(browseNodeCoverage, weeks, gls);
		return glCoverage;
	}

	@ResponseBody
	@RequestMapping("/downLoadBrowseNodeCoverage")
	public void downLoadBrowseNodeCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks, String[] pls,
			String[] gls, HttpServletRequest request, HttpServletResponse response) {
		List<BrowseNodeCoverage> browseNodes = service.downLoadBrowseNodeCoverage(browseNodeCoverage, weeks, pls, gls);
		File file = new File("BrowseNodeCoverage.csv");
		Writer writer = null;
		CsvWriter csvWriter = null;
		try {
			writer = new FileWriter(file);
			csvWriter = new CsvWriter(writer, ',');
			String title[] = { "sourceCountry", "targetCountry", "pl", "productGroupCode", "productGroupDescription",
					"inLeadCount", "buyableAsinCount", "coverage", "week", "year" };
			csvWriter.writeRecord(title);
			for (BrowseNodeCoverage browseNode : browseNodes) {
				String[] browse = { browseNode.getSourceCountry(), browseNode.getTargetCountry(), browseNode.getPl(),
						browseNode.getProductGroupCode(), browseNode.getProductGroupDescription(),
						browseNode.getInLeafCount(), browseNode.getBuyableAsinCount(), browseNode.getCoverage(),
						browseNode.getWeek(), browseNode.getYear() };
				csvWriter.writeRecord(browse);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				csvWriter.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		response.setCharacterEncoding("utf-8");
		// response.setContentType("multipart/form-data");
		response.setContentType("application/force-download");// 设置强制下载不打开
		response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());
		try {
			InputStream inputStream = new FileInputStream(file);
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
	}

	@RequestMapping("downLoadBrowseNodeDefectiveAsin")
	public ResponseEntity<ResponseBodyEmitter> downLoadBrowseNodeDefectiveAsin(
			final BrowseNodeDefectiveAsin browseNodeDefectiveAsin, final String[] weeks, final String[] pls, final String[] gls,
			HttpServletRequest request, HttpServletResponse response) {
		// 线程池
		final ExecutorService exec = Executors.newCachedThreadPool();
		// 阻塞队列
		final BlockingQueue<List<BrowseNodeDefectiveAsin>> queue = new LinkedBlockingQueue<List<BrowseNodeDefectiveAsin>>();
		// 原子整型
		final AtomicInteger readAtomic = new AtomicInteger();
		// 读取数据库数据线程
		Runnable read = new Runnable() {
			@Override
			public void run() {
				while (!exec.isShutdown()) {
					try {
						int page = readAtomic.incrementAndGet();
						int start = (page - 1) * 5000;
						int end = 5000;
						// 读取数据库数据
						List<BrowseNodeDefectiveAsin> list = service
								.findBrowseNodeDefectiveAsin(browseNodeDefectiveAsin, weeks, pls, gls, start, end);
						queue.put(list);
						if (list.size() == 0) {
							break;
						}
					} catch (Exception e) {
						exec.shutdownNow();
					}
				}
			}
		};
		// 写数据线程
		final ResponseBodyEmitter emitter = new ResponseBodyEmitter();
		Runnable write = new Runnable() {
			@Override
			public void run() {
				try {
					emitter.send(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
					emitter.send(new BrowseNodeDefectiveAsin().getTitle());
					while (!exec.isShutdown()) {
						List<BrowseNodeDefectiveAsin> list = queue.take();
						if (list.size() == 0) {
							emitter.complete();
							break;
						} else {
							// 写数据
							for (BrowseNodeDefectiveAsin asin : list) {
								emitter.send(asin.toString());
							}
						}
					}
				} catch (Exception e) {
					exec.shutdownNow();
				}
			}
		};
		exec.submit(read);
		exec.submit(write);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/octet-stream;charset=UTF-8");
		headers.set("Transfer-Encoding", "chunked");
		headers.setContentDispositionFormData("attachment", "BrowseNodeDefectiveAsin.csv");
		return new ResponseEntity<ResponseBodyEmitter>(emitter, headers, HttpStatus.OK);
	}

}
