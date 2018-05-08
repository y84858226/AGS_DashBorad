package com.amazon.ags.pojo;

import java.io.Serializable;

//@JsonSerialize
//@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class BrowseNodeDefectiveAsin implements Serializable {
	private static final long serialVersionUID = 121808756610457825L;
	int id;
	String sourceCountry;
	String targetCountry;
	String year;
	String week;
	String asin;
	String productGroupCode;
	String productGroupDescription;
	String pl;
	String browseNodeId;
	String rootBrowseNodeId;
	String browseNodeName;
	String edgePathName;
	String isLeafNode;
	String mark;

	public BrowseNodeDefectiveAsin() {

	}

	public BrowseNodeDefectiveAsin(String sourceCountry, String targetCountry, String year, String week, String asin,
			String productGroupCode, String productGroupDescription, String pl, String browseNodeId,
			String rootBrowseNodeId, String browseNodeName, String edgePathName, String isLeafNode, String mark) {
		this.sourceCountry = sourceCountry;
		this.targetCountry = targetCountry;
		this.year = year;
		this.week = week;
		this.asin = asin;
		this.productGroupCode = productGroupCode;
		this.productGroupDescription = productGroupDescription;
		this.pl = pl;
		this.browseNodeId = browseNodeId;
		this.rootBrowseNodeId = rootBrowseNodeId;
		this.browseNodeName = browseNodeName;
		this.edgePathName = edgePathName;
		this.isLeafNode = isLeafNode;
		this.mark = mark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSourceCountry() {
		return sourceCountry;
	}

	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}

	public String getTargetCountry() {
		return targetCountry;
	}

	public void setTargetCountry(String targetCountry) {
		this.targetCountry = targetCountry;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public String getProductGroupDescription() {
		return productGroupDescription;
	}

	public void setProductGroupDescription(String productGroupDescription) {
		this.productGroupDescription = productGroupDescription;
	}

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getBrowseNodeId() {
		return browseNodeId;
	}

	public void setBrowseNodeId(String browseNodeId) {
		this.browseNodeId = browseNodeId;
	}

	public String getRootBrowseNodeId() {
		return rootBrowseNodeId;
	}

	public void setRootBrowseNodeId(String rootBrowseNodeId) {
		this.rootBrowseNodeId = rootBrowseNodeId;
	}

	public String getBrowseNodeName() {
		return browseNodeName;
	}

	public void setBrowseNodeName(String browseNodeName) {
		this.browseNodeName = browseNodeName;
	}

	public String getEdgePathName() {
		return edgePathName;
	}

	public void setEdgePathName(String edgePathName) {
		this.edgePathName = edgePathName;
	}

	public String getIsLeafNode() {
		return isLeafNode;
	}

	public void setIsLeafNode(String isLeafNode) {
		this.isLeafNode = isLeafNode;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return sourceCountry + "," + targetCountry + "," + year + "," + week + "," + asin + "," + productGroupCode + ","
				+ productGroupDescription + "," + pl + "," + browseNodeId + "," + rootBrowseNodeId + ",\""
				+ browseNodeName + "\",\"" + edgePathName + "\"," + isLeafNode + "," + mark + "\r\n";
	}

}
