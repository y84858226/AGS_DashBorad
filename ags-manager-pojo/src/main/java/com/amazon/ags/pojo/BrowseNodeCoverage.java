package com.amazon.ags.pojo;

import java.io.Serializable;

//@JsonSerialize
//@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class BrowseNodeCoverage implements Serializable {
	private static final long serialVersionUID = 7783805227732450954L;
	int id;
	String sourceCountry;
	String targetCountry;
	String year;
	String week;
	String pl;
	String productGroupCode;
	String productGroupDescription;
	String inLeafCount;
	String buyableAsinCount;
	String coverage;

	public BrowseNodeCoverage() {
	}

	public BrowseNodeCoverage(String sourceCountry, String targetCountry, String pl, String productGroupCode,
			String productGroupDescription, String inLeafCount, String buyableAsinCount, String coverage, String week,
			String year) {
		this.sourceCountry = sourceCountry;
		this.targetCountry = targetCountry;
		this.pl = pl;
		this.productGroupCode = productGroupCode;
		this.productGroupDescription = productGroupDescription;
		this.inLeafCount = inLeafCount;
		this.buyableAsinCount = buyableAsinCount;
		this.coverage = coverage;
		this.week = week;
		this.year = year;
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

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
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

	public String getInLeafCount() {
		return inLeafCount;
	}

	public void setInLeafCount(String inLeafCount) {
		this.inLeafCount = inLeafCount;
	}

	public String getBuyableAsinCount() {
		return buyableAsinCount;
	}

	public void setBuyableAsinCount(String buyableAsinCount) {
		this.buyableAsinCount = buyableAsinCount;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "BrowseNode [id=" + id + ", sourceCountry=" + sourceCountry + ", targetCountry=" + targetCountry
				+ ", pl=" + pl + ", productGroupCode=" + productGroupCode + ", productGroupDescription="
				+ productGroupDescription + ", inLeafCount=" + inLeafCount + ", buyableAsinCount=" + buyableAsinCount
				+ ", coverage=" + coverage + ", week=" + week + ", year=" + year + "]";
	}

}
