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

import com.amazon.ags.commons.SendEmail;
import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.BrowseNodeDefectiveAsin;
import com.amazon.ags.service.BrowseNodeService;

@Component
public class TimerTask {
	@Resource
	private BrowseNodeService service;

	@Scheduled(cron = "0 0 0 ? * MON-FRI")
	public void importBrowseNodeCoverage() {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 阻塞队列
		final BlockingQueue<BrowseNodeCoverage> queue = new LinkedBlockingQueue<BrowseNodeCoverage>();
		// 读数据线程
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				File file = new File("/home/metron/ags_dashboard_browsenode");
				if (file.exists()) {
					File[] files = file.listFiles();
					for (File brwoseNodeCoveageFile : files) {
						BufferedReader reader = null;
						try {
							reader = new BufferedReader(
									new InputStreamReader(new FileInputStream(brwoseNodeCoveageFile)));
							String line = "";
							reader.readLine();// title
							while ((line = reader.readLine()) != null) {
								String array[] = line.split("\t");
								BrowseNodeCoverage browseNodeCoverage = new BrowseNodeCoverage(array[0], array[1],
										array[6], array[4], array[5], array[7], array[8], array[9], array[3], array[2]);
								queue.put(browseNodeCoverage);
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							try {
								if (reader != null) {
									reader.close();
									brwoseNodeCoveageFile.delete();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		};

		// 写数据线程
		Runnable runnable2 = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						BrowseNodeCoverage browseNodeCoverage = queue.poll(10, TimeUnit.SECONDS);
						if (browseNodeCoverage == null) {
							service.clearBrowseNode();
							break;
						} else {
							service.addBrowseNodeCoverage(browseNodeCoverage);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		exec.submit(runnable);
		exec.submit(runnable2);
	}

	@Scheduled(cron = "0 0 0 ? * MON-FRI")
	public void importBrowseNodeDefectiveAsin() {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 阻塞队列
		final BlockingQueue<BrowseNodeDefectiveAsin> queue = new LinkedBlockingQueue<BrowseNodeDefectiveAsin>();
		// 读数据线程
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				File file = new File("/home/metron/ags_dashboard_browsenodeasin");
				if (file.exists()) {
					File[] files = file.listFiles();
					for (File brwoseNodeAsinFile : files) {
						BufferedReader reader = null;
						try {
							reader = new BufferedReader(new InputStreamReader(new FileInputStream(brwoseNodeAsinFile)));
							String line = "";
							reader.readLine();// title
							while ((line = reader.readLine()) != null) {
								String array[] = line.split("\t");
								BrowseNodeDefectiveAsin browseNodeAsin = new BrowseNodeDefectiveAsin(array[0], array[1],
										array[2], array[3], array[4], array[5], array[6], array[7], array[8], array[9],
										array[10], array[11], array[12], array[13]);
								queue.put(browseNodeAsin);
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							try {
								if (reader != null) {
									reader.close();
									brwoseNodeAsinFile.delete();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		};

		// 写数据线程
		Runnable runnable2 = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						BrowseNodeDefectiveAsin browseNodeDefectiveAsin = queue.poll(10, TimeUnit.SECONDS);
						if (browseNodeDefectiveAsin == null) {
							service.clearBrowseNodeDefectiveAsin();
							break;
						} else {
							service.addBrowseNodeDefectiveAsin(browseNodeDefectiveAsin);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		exec.submit(runnable);
		exec.submit(runnable2);
	}
}
