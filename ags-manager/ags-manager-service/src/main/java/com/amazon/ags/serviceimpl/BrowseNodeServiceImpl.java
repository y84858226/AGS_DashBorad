package com.amazon.ags.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.amazon.ags.mapper.BrowseNodeMapper;
import com.amazon.ags.pojo.BrowseNodeCoverage;
import com.amazon.ags.pojo.BrowseNodeDefectiveAsin;
import com.amazon.ags.service.BrowseNodeService;

@Service
public class BrowseNodeServiceImpl implements BrowseNodeService {
	@Autowired
	private BrowseNodeMapper mapper;

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findSourceCountry'")
	public List<BrowseNodeCoverage> findSourceCountry() {
		return mapper.findSourceCountry();
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findTargetCountry'+#browseNodeCoverage")
	public List<BrowseNodeCoverage> findTargetCountry(BrowseNodeCoverage browseNodeCoverage) {
		return mapper.findTargetCountry(browseNodeCoverage);
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findYear'+#browseNodeCoverage")
	public List<BrowseNodeCoverage> findYear(BrowseNodeCoverage browseNodeCoverage) {
		return mapper.findYear(browseNodeCoverage);
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findWeek'+#browseNodeCoverage")
	public List<BrowseNodeCoverage> findWeek(BrowseNodeCoverage browseNodeCoverage) {
		return mapper.findWeek(browseNodeCoverage);
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findPL'+#browseNodeCoverage+#weeks")
	public List<BrowseNodeCoverage> findPL(BrowseNodeCoverage browseNodeCoverage, String[] weeks) {
		return mapper.findPL(browseNodeCoverage, weeks);
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findGL'+#browseNodeCoverage+#weeks+#pls")
	public List<BrowseNodeCoverage> findGL(BrowseNodeCoverage browseNodeCoverage, String[] weeks, String[] pls) {
		return mapper.findGL(browseNodeCoverage, weeks, pls);
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findWeekCoverage'+#browseNodeCoverage+#weeks")
	public List<BrowseNodeCoverage> findWeekCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks) {
		return mapper.findWeekCoverage(browseNodeCoverage, weeks);
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findPlCoverage'+#browseNodeCoverage+#weeks+#pls")
	public List<BrowseNodeCoverage> findPlCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks,
			String[] pls) {
		return mapper.findPlCoverage(browseNodeCoverage, weeks, pls);
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'findGlCoverage'+#browseNodeCoverage+#weeks+#gls")
	public List<BrowseNodeCoverage> findGlCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks,
			String[] gls) {
		return mapper.findGlCoverage(browseNodeCoverage, weeks, gls);
	}

	@Override
	@Cacheable(value = "browseNodeCoverage", key = "'downLoadBrowseNodeCoverage'+#browseNodeCoverage+#weeks+#pls+#gls")
	public List<BrowseNodeCoverage> downLoadBrowseNodeCoverage(BrowseNodeCoverage browseNodeCoverage, String[] weeks,
			String[] pls, String[] gls) {
		List<BrowseNodeCoverage> browseNodes = mapper.downLoadBrowseNodeCoverage(browseNodeCoverage, weeks, pls, gls);
		return browseNodes;
	}

	@Override
	public void addBrowseNodeCoverage(BrowseNodeCoverage browseNodeCoverage) {
		mapper.addBrowseNodeCoverage(browseNodeCoverage);
	}

	@Override
	@CacheEvict(value = "browseNodeCoverage", allEntries = true)
	public void clearBrowseNode() {

	}

	@Override
	@Cacheable(value = "browseNodeDefectiveAsin", key = "'findBrowseNodeDefectiveAsin'+#browseNodeCoverage+#weeks+#pls+#gls+#start+#end")
	public List<BrowseNodeDefectiveAsin> findBrowseNodeDefectiveAsin(BrowseNodeDefectiveAsin browseNodeDefectiveAsin,
			String[] weeks, String[] pls, String[] gls, int start, int end) {
		return mapper.findBrowseNodeDefectiveAsin(browseNodeDefectiveAsin, weeks, pls, gls, start, end);
	}

	@Override
	public void addBrowseNodeDefectiveAsin(BrowseNodeDefectiveAsin browseNodeDefectiveAsin) {
		mapper.addBrowseNodeDefectiveAsin(browseNodeDefectiveAsin);
	}

	@Override
	@CacheEvict(value = "browseNodeDefectiveAsin", allEntries = true)
	public void clearBrowseNodeDefectiveAsin() {

	}

}
