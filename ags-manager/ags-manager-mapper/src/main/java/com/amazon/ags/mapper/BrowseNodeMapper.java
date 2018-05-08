package com.amazon.ags.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.BrowseNodeDefectiveAsin;

public interface BrowseNodeMapper {
	public List<BrowseNodeCoverage> findSourceCountry();

	public List<BrowseNodeCoverage> findTargetCountry(BrowseNodeCoverage browseNodeCoverage);

	public List<BrowseNodeCoverage> findWeek(BrowseNodeCoverage browseNodeCoverage);

	public List<BrowseNodeCoverage> findYear(BrowseNodeCoverage browseNodeCoverage);

	public List<BrowseNodeCoverage> findPL(@Param("browseNodeCoverage") BrowseNodeCoverage browseNodeCoverage,
			@Param("weeks") String weeks[]);

	public List<BrowseNodeCoverage> findGL(@Param("browseNodeCoverage") BrowseNodeCoverage browseNodeCoverage,
			@Param("weeks") String weeks[], @Param("pls") String[] pls);

	public List<BrowseNodeCoverage> findWeekCoverage(@Param("browseNodeCoverage") BrowseNodeCoverage browseNodeCoverage,
			@Param("weeks") String weeks[]);

	public List<BrowseNodeCoverage> findPlCoverage(@Param("browseNodeCoverage") BrowseNodeCoverage browseNodeCoverage,
			@Param("weeks") String weeks[], @Param("pls") String[] pls);

	public List<BrowseNodeCoverage> findGlCoverage(@Param("browseNodeCoverage") BrowseNodeCoverage browseNodeCoverage,
			@Param("weeks") String weeks[], @Param("gls") String[] gls);

	public List<BrowseNodeCoverage> downLoadBrowseNodeCoverage(
			@Param("browseNodeCoverage") BrowseNodeCoverage browseNodeCoverage, @Param("weeks") String[] weeks,
			@Param("pls") String[] pls, @Param("gls") String[] gls);

	public List<BrowseNodeDefectiveAsin> findBrowseNodeDefectiveAsin(
			@Param("browseNodeDefectiveAsin") BrowseNodeDefectiveAsin browseNodeDefectiveAsin, @Param("weeks") String[] weeks,
			@Param("pls") String[] pls, @Param("gls") String[] gls, @Param(value = "start") int start,
			@Param(value = "end") int end);

	public void addBrowseNodeCoverage(@Param("browseNodeCoverage") BrowseNodeCoverage browseNodeCoverage);

	public void addBrowseNodeDefectiveAsin(@Param("browseNodeDefectiveAsin") BrowseNodeDefectiveAsin browseNodeDefectiveAsin);

}
