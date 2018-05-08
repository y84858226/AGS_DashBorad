package com.amazon.ags.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.amazon.ags.mapper.RefinementMapper;
import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.RefinementCoverage;
import com.amazon.ags.pojo.RefinementDefectiveAsin;
import com.amazon.ags.service.RefinementService;

@Service
public class RefinementServiceImpl implements RefinementService {
	@Autowired
	private RefinementMapper mapper;

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementSourceCountry'")
	public List<RefinementCoverage> findRefinementSourceCountry() {
		return mapper.findRefinementSourceCountry();
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementTargetCountry'+#refinementCoverage")
	public List<RefinementCoverage> findRefinementTargetCountry(RefinementCoverage refinementCoverage) {
		return mapper.findRefinementTargetCountry(refinementCoverage);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementYear'+#refinementCoverage")
	public List<RefinementCoverage> findRefinementYear(RefinementCoverage refinementCoverage) {
		return mapper.findRefinementYear(refinementCoverage);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementWeek'+#refinementCoverage")
	public List<RefinementCoverage> findRefinementWeek(RefinementCoverage refinementCoverage) {
		return mapper.findRefinementWeek(refinementCoverage);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementPL'+#refinementCoverage+#weeks")
	public List<RefinementCoverage> findRefinementPL(RefinementCoverage refinementCoverage, String[] weeks) {
		return mapper.findRefinementPL(refinementCoverage, weeks);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementGL'+#refinementCoverage+#weeks+#pls")
	public List<RefinementCoverage> findRefinementGL(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls) {
		return mapper.findRefinementGL(refinementCoverage, weeks, pls);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementBand'+#refinementCoverage+#weeks+#pls+#gls")
	public List<RefinementCoverage> findRefinementBand(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls, String[] gls) {

		return mapper.findRefinementBand(refinementCoverage, weeks, pls, gls);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementWeekCoverage'+#refinementCoverage+#weeks")
	public List<RefinementCoverage> findRefinementWeekCoverage(RefinementCoverage refinementCoverage, String[] weeks) {

		return mapper.findRefinementWeekCoverage(refinementCoverage, weeks);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementPlCoverage'+#refinementCoverage+#weeks+#pls")
	public List<RefinementCoverage> findRefinementPlCoverage(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls) {
		return mapper.findRefinementPlCoverage(refinementCoverage, weeks, pls);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'findRefinementBandCoverage'+#refinementCoverage+#weeks+#gls+#bands")
	public List<RefinementCoverage> findRefinementBandCoverage(RefinementCoverage refinementCoverage, String[] weeks,
			String[] gls, String[] bands) {
		return mapper.findRefinementBandCoverage(refinementCoverage, weeks, gls, bands);
	}

	@Override
	@Cacheable(value = "refinementCoverage", key = "'downLoadRefinementCoverage'+#refinementCoverage+#weeks+#pls+#gls+#bands")
	public List<RefinementCoverage> downLoadRefinementCoverage(RefinementCoverage refinementCoverage, String[] weeks,
			String[] pls, String[] gls, String[] bands) {
		return mapper.downLoadRefinementCoverage(refinementCoverage, weeks, pls, gls, bands);
	}

	public void addRefinementCoverage(List<RefinementCoverage> list) {
		mapper.addRefinementCoverage(list);
	}

	@Override
	@CacheEvict(value = "refinementCoverage", allEntries = true)
	public void clearRefinementCoverage() {

	}

	@Override
	@CacheEvict(value = "refinementDefectiveAsin", allEntries = true)
	public void clearRefinementDefectiveAsin() {
		mapper.deleteRefinementDefectiveAsin();
	}

	@Override
	public void addRefinementDefectiveAsin(List<RefinementDefectiveAsin> list) {
		mapper.addRefinementDefectiveAsin(list);
	}

	@Override
	public List<RefinementDefectiveAsin> findRefinementDefectiveAsin(RefinementDefectiveAsin refinementDefectiveAsin,
			String[] weeks, String[] pls, String[] gls, int start, int end) {
		return mapper.findRefinementDefectiveAsin(refinementDefectiveAsin, weeks, pls, gls, start, end);
	}
}
