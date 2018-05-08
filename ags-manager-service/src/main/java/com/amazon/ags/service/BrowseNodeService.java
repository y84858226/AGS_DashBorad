package com.amazon.ags.service;

import java.util.List;

import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.BrowseNodeDefectiveAsin;

public interface BrowseNodeService {
	public List<BrowseNodeCoverage> findSourceCountry();

	public List<BrowseNodeCoverage> findTargetCountry(BrowseNodeCoverage browseNodeCoverage);

	public List<BrowseNodeCoverage> findYear(BrowseNodeCoverage browseNodeCoverage);

	public List<BrowseNodeCoverage> findWeek(BrowseNodeCoverage browseNodeCoverage);

	public List<BrowseNodeCoverage> findPL(BrowseNodeCoverage browseNodeCoverage, String[] weeks);

	public List<BrowseNodeCoverage> findGL(BrowseNodeCoverage browseNodeCoverage, String[] weeks, String[] pls);

	public List<BrowseNodeCoverage> findWeekCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks);

	public List<BrowseNodeCoverage> findPlCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks, String[] pls);

	public List<BrowseNodeCoverage> findGlCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks, String[] gls);

	public List<BrowseNodeCoverage> downLoadBrowseNodeCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks,
			String[] pls, String[] gls);

	public List<BrowseNodeDefectiveAsin> findBrowseNodeDefectiveAsin(BrowseNodeDefectiveAsin browseNodeDefectiveAsin,
			String[] weeks, String[] pls, String[] gls, int start, int end);

	public void addBrowseNodeCoverage(List<BrowseNodeCoverage> list);

	public void addBrowseNodeDefectiveAsin(List<BrowseNodeDefectiveAsin> list);

	public void clearBrowseNodeCoverage();

	public void clearBrowseNodeDefectiveAsin();

}
