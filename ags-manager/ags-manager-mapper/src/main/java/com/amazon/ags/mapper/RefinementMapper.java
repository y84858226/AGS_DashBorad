package com.amazon.ags.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.amazon.ags.pojo.RefinementCoverage;

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

}
