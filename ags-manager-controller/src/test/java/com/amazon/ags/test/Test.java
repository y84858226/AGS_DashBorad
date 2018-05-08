package com.amazon.ags.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazon.ags.commons.BufferedRandomAccessFile;

public class Test {

	String path = "/home/metron/ags_browsenode_tool/us_to_cn/";
	String defectiveAsinFileName = "defectiveAsin.txt";
	String mappingTableFileName = "mappingTable.txt";
	String browseNodeFileName = "browseNode.txt";

	@org.junit.Test
	public void testCsv() {

		File mappingTableFile = new File(path + mappingTableFileName);
		File defectiveAsinFile = new File(path + defectiveAsinFileName);
		File browseNodeFile = new File(path + browseNodeFileName);

		if (defectiveAsinFile.exists() && mappingTableFile.exists() && browseNodeFile.exists()) {
			Map<String, String> mappingMap = readMappingFile(mappingTableFile);

			Map<String, String> browseNodeMap = readBrowseNodeFile(browseNodeFile);

			mappingDefectiveAsin(defectiveAsinFile, mappingMap, browseNodeMap);

			deduplication();
		}
	}

	public void clearResult() {

	}

	public Map<String, String> readMappingFile(File mappingTableFile) {
		BufferedRandomAccessFile br = null;
		Map<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			br = new BufferedRandomAccessFile(mappingTableFile, "r");
			String line = null;
			br.readLine();// title
			while ((line = br.readLine()) != null) {
				String attr[] = line.split("\t");
				String usNode = attr[0];
				String targetNode = attr[1];
				map.put(usNode, targetNode);
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

	public void mappingDefectiveAsin(File defectiveAsinFile, Map<String, String> mappingMap,
			Map<String, String> browseNodeMap) {
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
				if (mappingMap.get(attr[2]) != null) {
					String targetBrowseNodeId = mappingMap.get(attr[2]);
					line = line + "\t" + targetBrowseNodeId;
					if (browseNodeMap.get(targetBrowseNodeId) != null) {
						line = line + "\t" + browseNodeMap.get(targetBrowseNodeId);
					} else {
						line = line + "\t" + "" + "\t" + "";
					}
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
			String title = br.readLine();
			while ((line = br.readLine()) != null) {
				String attr[] = line.split("\t");
				String asin = attr[0];
				String gl = attr[1];
				String sourceBrowseNodeId = attr[2];
				String browseNodeId = attr[3];
				String isLeafNode = attr[4];
				String path = attr[5];
				if (map.get(asin) == null) {
					map.put(asin, gl + "\t" + sourceBrowseNodeId + "\t" + browseNodeId + "\t" + isLeafNode + "\t" + path
							+ "\t" + "*");
				} else {
					String str[] = map.get(asin).split("\t");
					String oldIsLeafNode = str[3];
					if (oldIsLeafNode.equals("Y")) {
						continue;
					} else if (oldIsLeafNode.equals("N")) {
						if (isLeafNode.equals("Y")) {
							map.put(asin, gl + "\t" + sourceBrowseNodeId + "\t" + browseNodeId + "\t" + isLeafNode
									+ "\t" + path + "\t" + "*");
						}
					} else if (oldIsLeafNode.equals("")) {
						if (!isLeafNode.equals("")) {
							map.put(asin, gl + "\t" + sourceBrowseNodeId + "\t" + browseNodeId + "\t" + isLeafNode
									+ "\t" + path + "\t" + "*");
						}
					}
				}
			}
			// write
			writeMap = new HashMap<String, BufferedRandomAccessFile>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String attr[] = entry.getValue().split("\t");
				String isLeafNode = attr[3];
				if (writeMap.get(isLeafNode) == null) {
					File result = new File(path + "result_" + isLeafNode + ".csv");
					bw = new BufferedRandomAccessFile(result, "rw");
					bw.writeBytes(title.substring(0, title.lastIndexOf("\t")) + "\r\n");
					writeMap.put(isLeafNode, bw);
				}
				bw = writeMap.get(isLeafNode);
				bw.writeBytes(entry.getKey() + "," + attr[0] + "," + attr[1] + "," + attr[2] + "," + attr[3] + ",\""
						+ attr[4] + "\"" + "\r\n");
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
