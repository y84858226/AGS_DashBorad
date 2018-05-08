package com.amazon.ags.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.csvreader.CsvWriter;

public class Test {

	@org.junit.Test
	public void testCsv() {
		File file = new File("test.csv");
		Writer writer;
		CsvWriter csvWriter = null;
		try {
			String[] strs = { "abc", "abc", "abc" };
			writer = new FileWriter(file);
			csvWriter = new CsvWriter(writer, ',');
			csvWriter.writeRecord(strs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		csvWriter.close();
	}
}
