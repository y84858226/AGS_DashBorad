package com.amazon.ags.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.RefinementCoverage;
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
					"sourceCountry,targetCountry,week,pl,gl,nodeId,storeContextName,refId,refName,refFilterTags,owner,status,glanceViewBand,eligibleAsinCount,participatingAsinCount,coverageAssignments,eligibleAsinGv,participatingAsinGv,coverageGvs,idCat,idRef\r\n");
			for (RefinementCoverage refinement : refinements) {
				writer.write(refinement.toString()+"\r\n");
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
}
