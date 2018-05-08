package com.amazon.ags.pojo;

import java.io.Serializable;

public class RefinementDefectiveAsin implements Serializable {

	private static final long serialVersionUID = -2079941619672784173L;

	int id;
	String sourceCountry;
	String targetCountry;
	String year;
	String week;
	String pl;
	String gl;
	String nodeId;
	String storeContextName;
	String refId;
	String refName;
	String refFilterTags;
	String owner;
	String status;
	String glanceViewBand;
	String defectiveAsin;

	public RefinementDefectiveAsin() {

	}

	public RefinementDefectiveAsin(String sourceCountry, String targetCountry, String year, String week, String pl,
			String gl, String nodeId, String storeContextName, String refId, String refName, String refFilterTags,
			String owner, String status, String glanceViewBand, String defectiveAsin) {
		this.sourceCountry = sourceCountry;
		this.targetCountry = targetCountry;
		this.year = year;
		this.week = week;
		this.pl = pl;
		this.gl = gl;
		this.nodeId = nodeId;
		this.storeContextName = storeContextName;
		this.refId = refId;
		this.refName = refName;
		this.refFilterTags = refFilterTags;
		this.owner = owner;
		this.status = status;
		this.glanceViewBand = glanceViewBand;
		this.defectiveAsin = defectiveAsin;
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

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getGl() {
		return gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getStoreContextName() {
		return storeContextName;
	}

	public void setStoreContextName(String storeContextName) {
		this.storeContextName = storeContextName;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getRefFilterTags() {
		return refFilterTags;
	}

	public void setRefFilterTags(String refFilterTags) {
		this.refFilterTags = refFilterTags;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGlanceViewBand() {
		return glanceViewBand;
	}

	public void setGlanceViewBand(String glanceViewBand) {
		this.glanceViewBand = glanceViewBand;
	}

	public String getDefectiveAsin() {
		return defectiveAsin;
	}

	public void setDefectiveAsin(String defectiveAsin) {
		this.defectiveAsin = defectiveAsin;
	}

	@Override
	public String toString() {
		return sourceCountry + "," + targetCountry + "," + year + "," + week + "," + pl + "," + gl + "," + nodeId
				+ ",\"" + storeContextName + "\"," + refId + ",\"" + refName + "\"," + refFilterTags + "," + owner + ","
				+ status + "," + glanceViewBand + "," + defectiveAsin+"\r\n";
	}

	public String getTitle() {
		return "sourceCountry,targetCountry,year,week,pl,gl,nodeId,storeContextName,refId,refName,refFilterTags,owner,status,glanceViewBand,defectiveAsin\r\n";
	}

}
