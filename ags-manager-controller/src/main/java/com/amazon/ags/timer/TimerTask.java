package com.amazon.ags.timer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazon.ags.commons.FileUtil;
import com.amazon.ags.commons.SendEmail;
import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.BrowseNodeDefectiveAsin;
import com.amazon.ags.pojo.RefinementCoverage;
import com.amazon.ags.pojo.RefinementDefectiveAsin;
import com.amazon.ags.service.BrowseNodeService;
import com.amazon.ags.service.RefinementService;

@Component
@Controller
public class TimerTask {
	@Resource
	private BrowseNodeService service;
	@Resource
	private RefinementService service2;

	@ResponseBody
	@RequestMapping(value = "scheduleBrowseNode")
	@Scheduled(cron = "0 0 0 ? * MON")
	public void scheduleRunBrowseNode() {
		service.clearBrowseNodeCoverage();
		importBrowseNodeCoverage();

		// clear database and cache
		service.clearBrowseNodeDefectiveAsin();
		importBrowseNodeDefectiveAsin();
	}

	@ResponseBody
	@RequestMapping(value = "scheduleRefinement")
	@Scheduled(cron = "0 0 1 ? * MON")
	public void scheduleRunRefinement() {
		service2.clearRefinementCoverage();
		importRefinementCoverage();

		// clear database and cache
		service2.clearRefinementDefectiveAsin();
		importRefinementDefectiveAsin();
	}

	public void importBrowseNodeCoverage() {
		File filePath = new File("/home/metron/ags_dashboard_browsenode");
		if (filePath.exists()) {
			File[] files = filePath.listFiles();
			for (File file : files) {
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String line = bufferedReader.readLine();// title
					List<BrowseNodeCoverage> list = new ArrayList<BrowseNodeCoverage>();
					while ((line = bufferedReader.readLine()) != null) {
						String[] values = line.split("\t");
						BrowseNodeCoverage browseNodeCoverage = new BrowseNodeCoverage(values[0], values[1], values[6],
								values[4], values[5], values[7], values[8], values[9], values[3], values[2]);
						list.add(browseNodeCoverage);
						if (list.size() == 5000) {
							service.addBrowseNodeCoverage(list);
							list.clear();
						}
					}
					if (list.size() > 0) {
						service.addBrowseNodeCoverage(list);
						list.clear();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bufferedReader != null) {
							bufferedReader.close();
						}
						file.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void importBrowseNodeDefectiveAsin() {
		File filePath = new File("/home/metron/ags_dashboard_browsenodeasin");
		if (filePath.exists()) {
			File[] files = filePath.listFiles();
			for (File file : files) {
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String line = bufferedReader.readLine();// title
					List<BrowseNodeDefectiveAsin> list = new ArrayList<BrowseNodeDefectiveAsin>();
					while ((line = bufferedReader.readLine()) != null) {
						String[] values = line.split("\t");
						BrowseNodeDefectiveAsin browseNodeAsin = new BrowseNodeDefectiveAsin(values[0], values[1],
								values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9],
								values[10], values[11], values[12]);
						list.add(browseNodeAsin);
						if (list.size() == 5000) {
							service.addBrowseNodeDefectiveAsin(list);
							list.clear();
						}
					}
					if (list.size() > 0) {
						service.addBrowseNodeDefectiveAsin(list);
						list.clear();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bufferedReader != null) {
							bufferedReader.close();
						}
						file.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void importRefinementCoverage() {
		File filePath = new File("/home/metron/ags_dashboard_refinement");
		if (filePath.exists()) {
			File[] files = filePath.listFiles();
			for (File file : files) {
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String line = bufferedReader.readLine();// title
					List<RefinementCoverage> list = new ArrayList<RefinementCoverage>();
					while ((line = bufferedReader.readLine()) != null) {
						String[] values = line.split("\t");
						RefinementCoverage refinementCoverage = new RefinementCoverage(values[0], values[1], values[2],
								values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10],
								values[11], values[12], values[13], values[14], values[15], values[16], values[17],
								values[15], values[19], values[20], values[21]);
						list.add(refinementCoverage);
						if (list.size() == 5000) {
							service2.addRefinementCoverage(list);
							list.clear();
						}
					}
					if (list.size() > 0) {
						service2.addRefinementCoverage(list);
						list.clear();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bufferedReader != null) {
							bufferedReader.close();
						}
						file.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void importRefinementDefectiveAsin() {
		File filePath = new File("/home/metron/ags_dashboard_refinementasin");
		if (filePath.exists()) {
			File[] files = filePath.listFiles();
			for (File file : files) {
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String line = bufferedReader.readLine();// title
					List<RefinementDefectiveAsin> list = new ArrayList<RefinementDefectiveAsin>();
					while ((line = bufferedReader.readLine()) != null) {
						String[] values = line.split("\t");
						RefinementDefectiveAsin refinementDefectiveAsin = new RefinementDefectiveAsin(values[0],
								values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8],
								values[9], values[10], values[11], values[12], values[12], values[14]);
						list.add(refinementDefectiveAsin);
						if (list.size() == 5000) {
							service2.addRefinementDefectiveAsin(list);
							list.clear();
						}
					}
					if (list.size() > 0) {
						service2.addRefinementDefectiveAsin(list);
						list.clear();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bufferedReader != null) {
							bufferedReader.close();
						}
						file.delete();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
