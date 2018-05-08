package com.amazon.ags.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.amazon.ags.pojo.RefinementCoverage;
import com.amazon.ags.pojo.RefinementDefectiveAsin;

public interface RefinementMapper {

	public List<RefinementCoverage> findRefinementSourceCountry();

	public List<RefinementCoverage> findRefinementTargetCountry(
			@Param("refinementCoverage") RefinementCoverage refinementCoverage);

	public List<RefinementCoverage> findRefinementYear(
			@Param("refinementCoverage") RefinementCoverage refinementCoverage);

	public List<RefinementCoverage> findRefinementWeek(
			@Param("refinementCoverage") RefinementCoverage refinementCoverage);

	public List<RefinementCoverage> findRefinementPL(@Param("refinementCoverage") RefinementCoverage refinementCoverage,
			@Param("weeks") String[] weeks);

	public List<RefinementCoverage> findRefinementGL(@Param("refinementCoverage") RefinementCoverage refinementCoverage,
			@Param("weeks") String[] weeks, @Param("pls") String[] pls);

	public List<RefinementCoverage> findRefinementBand(
			@Param("refinementCoverage") RefinementCoverage refinementCoverage, @Param("weeks") String[] weeks,
			@Param("pls") String[] pls, @Param("gls") String[] gls);

	public List<RefinementCoverage> findRefinementWeekCoverage(
			@Param("refinementCoverage") RefinementCoverage refinementCoverage, @Param("weeks") String[] weeks);

	public List<RefinementCoverage> findRefinementPlCoverage(
			@Param("refinementCoverage") RefinementCoverage refinementCoverage, @Param("weeks") String[] weeks,
			@Param("pls") String[] pls);

	public List<RefinementCoverage> findRefinementBandCoverage(
			@Param("refinementCoverage") RefinementCoverage refinementCoverage, @Param("weeks") String[] weeks,
			@Param("gls") String[] gls, @Param("bands") String[] bands);

	public List<RefinementCoverage> downLoadRefinementCoverage(
			@Param("refinementCoverage") RefinementCoverage refinementCoverage, @Param("weeks") String[] weeks,
			@Param("pls") String[] pls, @Param("gls") String[] gls, @Param("bands") String[] bands);

	public void addRefinementCoverage(@Param("list") List<RefinementCoverage> list);

	public void deleteRefinementDefectiveAsin();

	public void addRefinementDefectiveAsin(@Param("list") List<RefinementDefectiveAsin> list);

	public List<RefinementDefectiveAsin> findRefinementDefectiveAsin(
			@Param("refinementDefectiveAsin") RefinementDefectiveAsin refinementDefectiveAsin,
			@Param("weeks") String[] weeks, @Param("pls") String[] pls, @Param("gls") String[] gls,
			@Param("start") int start, @Param("end") int end);

}
