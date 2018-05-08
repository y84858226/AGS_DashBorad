package com.amazon.ags.service;

import java.util.List;

import com.amazon.ags.pojo.RefinementCoverage;

public interface RefinementService {

	public List<RefinementCoverage> findRefinementSourceCountry();

	public List<RefinementCoverage> findRefinementTargetCountry(RefinementCoverage refinementCoverage);

	public List<RefinementCoverage> findRefinementYear(RefinementCoverage refinementCoverage);
	
	public List<RefinementCoverage> findRefinementWeek(RefinementCoverage refinementCoverage);

	public List<RefinementCoverage> findRefinementPL(RefinementCoverage refinementCoverage, String[] weeks);

	public List<RefinementCoverage> findRefinementGL(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls);

	public List<RefinementCoverage> findRefinementBand(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls, String[] gls);

	public List<RefinementCoverage> findRefinementWeekCoverage(RefinementCoverage refinementCoverage, String[] weeks);

	public List<RefinementCoverage> findRefinementPlCoverage(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls);

	public List<RefinementCoverage> findRefinementBandCoverage(RefinementCoverage refinementCoverage, String[] weeks,
			String[] gls, String[] bands);
	
	public List<RefinementCoverage> downLoadRefinementCoverage(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls, String[] gls, String[] bands);

	
}
