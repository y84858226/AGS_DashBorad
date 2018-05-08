package com.amazon.ags.commons;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DefectiveAsinMapping {
	String path;
	String defectiveAsinFileName = "defectiveAsin.txt";
	String mappingTableFileName = "mappingTable.txt";
	String browseNodeFileName = "browseNode.txt";

	public String getPath() {
		return path;
	}

	public void setPath(String sourceCountry, String targetCountry) {
		this.path = "/home/metron/ags_browsenode_tool/" + sourceCountry + "_to_" + targetCountry + "/";
	}

	public String getDefectiveAsinFileName() {
		return defectiveAsinFileName;
	}

	public void setDefectiveAsinFileName(String defectiveAsinFileName) {
		this.defectiveAsinFileName = defectiveAsinFileName;
	}

	public String getMappingTableFileName() {
		return mappingTableFileName;
	}

	public void setMappingTableFileName(String mappingTableFileName) {
		this.mappingTableFileName = mappingTableFileName;
	}

	public String getBrowseNodeFileName() {
		return browseNodeFileName;
	}

	public void setBrowseNodeFileName(String browseNodeFileName) {
		this.browseNodeFileName = browseNodeFileName;
	}

	public Map<String, String> readMappingFile(File mappingTableFile, Map<String, String> mappingTable) {
		BufferedRandomAccessFile br = null;
		Map<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			br = new BufferedRandomAccessFile(mappingTableFile, "r");
			String line = null;
			br.readLine();// title
			int count = 0;
			while ((line = br.readLine()) != null) {
				String attr[] = line.split("\t");
				try {
					String usNode = attr[0];
					String targetNode = attr[1];
					String isLeafNode = "null";
					String nodePath = "null";
					String browseNodeMap = mappingTable.get(targetNode);
					if (browseNodeMap != null) {
						isLeafNode = browseNodeMap.split("\t")[0];
						nodePath = browseNodeMap.split("\t")[1];
					}
					if (map.get(usNode) == null) {
						map.put(usNode, targetNode + "\t" + isLeafNode + "\t" + nodePath);
					} else {
						String oldLeafNode = map.get(usNode).split("\t", -1)[1];
						if (isLeafNode.equals("Y")) {
							map.put(usNode, targetNode + "\t" + isLeafNode + "\t" + nodePath);
						} else if (isLeafNode.equals("N")) {
							if (oldLeafNode.equals("")) {
								map.put(usNode, targetNode + "\t" + isLeafNode + "\t" + nodePath);
							}
						}
					}
				} catch (Exception e) {

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public Map<String, String> readBrowseNodeFile(File mappingTableFile) {
		BufferedRandomAccessFile br = null;
		Map<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			br = new BufferedRandomAccessFile(mappingTableFile, "r");
			String line = null;
			br.readLine();// title
			while ((line = br.readLine()) != null) {
				String attr[] = line.split("\t");
				String browseNodeId = attr[0];
				String path = attr[2];
				String isLeafNode = attr[4];
				map.put(browseNodeId, isLeafNode + "\t" + path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public void mappingDefectiveAsin(File defectiveAsinFile, Map<String, String> mappingMap) {
		BufferedRandomAccessFile br = null;
		BufferedRandomAccessFile bw = null;
		File tmp = null;
		try {
			br = new BufferedRandomAccessFile(defectiveAsinFile, "r");
			tmp = new File(path + "tmp.txt");
			bw = new BufferedRandomAccessFile(tmp, "rw");
			String line = "";
			String title = br.readLine();// title
			bw.writeBytes(
					title + "\t" + "target_browse_node_id" + "\t" + "is_leaf_node" + "\t" + "path" + "\t*" + "\r\n");
			while ((line = br.readLine()) != null) {
				String attr[] = line.split("\t");
				if (mappingMap.get(attr[3]) != null) {
					String browseNode = mappingMap.get(attr[3]);
					line = line + "\t" + browseNode;
				} else {
					line = line + "\t" + "" + "\t" + "" + "\t" + "";
				}
				bw.writeBytes(line + "\t*" + "\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void deduplication() {
		File tmp = new File(path + "tmp.txt");
		Map<String, String> map = new HashMap<String, String>();
		Map<String, BufferedRandomAccessFile> writeMap = null;
		BufferedRandomAccessFile br = null;
		BufferedRandomAccessFile bw = null;
		String line = "";
		try {
			br = new BufferedRandomAccessFile(tmp, "r");
			String titles[] = br.readLine().split("\t");
			while ((line = br.readLine()) != null) {
				String attr[] = line.split("\t");
				String pasin = attr[0];
				String casin = attr[1];
				String gl = attr[2];
				String sourceBrowseNodeId = attr[3];
				String browseNodeId = attr[4];
				String isLeafNode = attr[5];
				String path = attr[6];
				if (map.get(casin) == null) {
					map.put(casin, pasin + "\t" + casin + "\t" + gl + "\t" + sourceBrowseNodeId + "\t" + browseNodeId
							+ "\t" + isLeafNode + "\t" + path + "\t" + "*");
				} else {
					String str[] = map.get(casin).split("\t");
					String oldIsLeafNode = str[5];
					if (oldIsLeafNode.equals("Y")) {
						continue;
					} else if (oldIsLeafNode.equals("N")) {
						if (isLeafNode.equals("Y")) {
							map.put(casin, pasin + "\t" + casin + "\t" + gl + "\t" + sourceBrowseNodeId + "\t"
									+ browseNodeId + "\t" + isLeafNode + "\t" + path + "\t" + "*");
						}
					} else if (oldIsLeafNode.equals("")) {
						if (!isLeafNode.equals("")) {
							map.put(casin, pasin + "\t" + casin + "\t" + gl + "\t" + sourceBrowseNodeId + "\t"
									+ browseNodeId + "\t" + isLeafNode + "\t" + path + "\t" + "*");
						}
					}
				}
			}
			// write
			writeMap = new HashMap<String, BufferedRandomAccessFile>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String attr[] = entry.getValue().split("\t");
				String isLeafNode = attr[5];
				if (writeMap.get(isLeafNode) == null) {
					File result = new File(path + "result_" + isLeafNode + ".txt");
					if (result.exists()) {
						result.delete();
						result.createNewFile();
					}
					bw = new BufferedRandomAccessFile(result, "rw");

					bw.writeBytes(titles[0] + "\t" + titles[1] + "\t" + titles[2] + "\t" + titles[3] + "\t" + titles[4]
							+ "\t" + titles[5] + "\t" + titles[6] + "\r\n");
					writeMap.put(isLeafNode, bw);
				}
				bw = writeMap.get(isLeafNode);
				bw.writeBytes(attr[0] + "\t" + attr[1] + "\t" + attr[2] + "\t" + attr[3] + "\t"
						+ attr[4] + "\t" + attr[5] + "\t" + attr[6] + "\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				for (Map.Entry<String, BufferedRandomAccessFile> entry : writeMap.entrySet()) {
					entry.getValue().close();
				}
				tmp.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
