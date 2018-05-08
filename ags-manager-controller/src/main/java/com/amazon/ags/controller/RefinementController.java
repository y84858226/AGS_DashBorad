package com.amazon.ags.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.amazon.ags.commons.FileUtil;
import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.BrowseNodeDefectiveAsin;
import com.amazon.ags.pojo.RefinementCoverage;
import com.amazon.ags.pojo.RefinementDefectiveAsin;
import com.amazon.ags.service.RefinementService;
import com.csvreader.CsvWriter;

@Controller
public class RefinementController {
	@Resource
	private RefinementService service;

	@ResponseBody
	@RequestMapping("/findRefinementSourceCountry")
	public List<RefinementCoverage> findRefinementSourceCountry() {
		List<RefinementCoverage> sourceCountry = service.findRefinementSourceCountry();
		return sourceCountry;
	}

	@ResponseBody
	@RequestMapping("/findRefinementTargetCountry")
	public List<RefinementCoverage> findRefinementTargetCountry(RefinementCoverage refinementCoverage) {
		List<RefinementCoverage> targetCountry = service.findRefinementTargetCountry(refinementCoverage);
		return targetCountry;
	}

	@ResponseBody
	@RequestMapping("/findRefinementYear")
	public List<RefinementCoverage> findRefinementYear(RefinementCoverage refinementCoverage) {
		List<RefinementCoverage> year = service.findRefinementYear(refinementCoverage);
		return year;
	}

	@ResponseBody
	@RequestMapping("/findRefinementWeek")
	public List<RefinementCoverage> findRefinementWeek(RefinementCoverage refinementCoverage) {
		List<RefinementCoverage> week = service.findRefinementWeek(refinementCoverage);
		return week;
	}

	@ResponseBody
	@RequestMapping("/findRefinementPL")
	public List<RefinementCoverage> findRefinementPL(RefinementCoverage refinementCoverage, String[] weeks) {
		List<RefinementCoverage> pl = service.findRefinementPL(refinementCoverage, weeks);
		return pl;
	}

	@ResponseBody
	@RequestMapping("/findRefinementGL")
	public List<RefinementCoverage> findRefinementGL(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls) {
		List<RefinementCoverage> gl = service.findRefinementGL(refinementCoverage, weeks, pls);
		return gl;
	}

	@ResponseBody
	@RequestMapping("/findRefinementBand")
	public List<RefinementCoverage> findRefinementBand(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls, String[] gls) {
		List<RefinementCoverage> Band = service.findRefinementBand(refinementCoverage, weeks, pls, gls);
		return Band;
	}

	@ResponseBody
	@RequestMapping("/findRefinementWeekCoverage")
	public List<RefinementCoverage> findRefinementWeekCoverage(RefinementCoverage refinementCoverage, String[] weeks) {
		List<RefinementCoverage> weekCoverage = service.findRefinementWeekCoverage(refinementCoverage, weeks);
		return weekCoverage;
	}

	@ResponseBody
	@RequestMapping("/findRefinementPlCoverage")
	public List<RefinementCoverage> findRefinementPlCoverage(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls) {
		List<RefinementCoverage> plCoverage = service.findRefinementPlCoverage(refinementCoverage, weeks, pls);
		return plCoverage;
	}

	@ResponseBody
	@RequestMapping("/findRefinementBandCoverage")
	public List<RefinementCoverage> findRefinementBandCoverage(RefinementCoverage refinementCoverage, String[] weeks,
			String[] gls, String[] bands) {
		if (bands.length == 0) {
			bands = null;
		}
		List<RefinementCoverage> bandCoverage = service.findRefinementBandCoverage(refinementCoverage, weeks, gls,
				bands);
		return bandCoverage;
	}

	@ResponseBody
	@RequestMapping("/downLoadRefinementCoverage")
	public void downLoadRefinementCoverage(RefinementCoverage refinementCoverage, String[] weeks, String[] pls,
			String[] gls, String[] bands, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		// response.setContentType("multipart/form-data");
		response.setContentType("application/force-download");// 设置强制下载不打开
		response.setHeader("Content-Disposition", "attachment;fileName=RefinementCoverage.csv");
		try {
			List<RefinementCoverage> refinements = service.downLoadRefinementCoverage(refinementCoverage, weeks, pls,
					gls, bands);
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
			writer.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
			writer.write(
					"sourceCountry,targetCountry,year,week,pl,gl,nodeId,storeContextName,refId,refName,refFilterTags,owner,status,glanceViewBand,eligibleAsinCount,participatingAsinCount,coverageAssignments,eligibleAsinGv,participatingAsinGv,coverageGvs,idCat,idRef\r\n");
			for (RefinementCoverage refinement : refinements) {
				writer.write(refinement.toString() + "\r\n");
			}
			// 这里主要关闭。
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("downLoadRefinementDefectiveAsin")
	public ResponseEntity<ResponseBodyEmitter> downLoadRefinementDefectiveAsin(
			final RefinementDefectiveAsin refinementDefectiveAsin, final String[] weeks, final String[] pls,
			final String[] gls, HttpServletRequest request, HttpServletResponse response) {
		// 线程池
		final ExecutorService exec = Executors.newCachedThreadPool();
		// 阻塞队列
		final BlockingQueue<List<RefinementDefectiveAsin>> queue = new LinkedBlockingQueue<List<RefinementDefectiveAsin>>();
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
						List<RefinementDefectiveAsin> list = service
								.findRefinementDefectiveAsin(refinementDefectiveAsin, weeks, pls, gls, start, end);
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
					emitter.send(new RefinementDefectiveAsin().getTitle());
					while (!exec.isShutdown()) {
						List<RefinementDefectiveAsin> list = queue.take();
						if (list.size() == 0) {
							emitter.complete();
							break;
						} else {
							// 写数据
							for (RefinementDefectiveAsin asin : list) {
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
		headers.setContentDispositionFormData("attachment", "RefinementDefectiveAsin.csv");
		return new ResponseEntity<ResponseBodyEmitter>(emitter, headers, HttpStatus.OK);
	}

}
