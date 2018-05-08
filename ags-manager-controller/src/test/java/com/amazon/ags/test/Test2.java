package com.amazon.ags.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test2 {
	public static void main(String[] args) {
		String path = "/home/metron/3240_20180317.txt";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String line="";
			int i=0;
			while((line=bufferedReader.readLine())!=null){
				i++;
				if(i%1000000==0){
					System.out.println("add i:"+i);
				}
			}
			System.out.println("end");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
