package com.example.example2;

import java.util.ArrayList;
import java.util.List;

public class StringOoMock {
	static String  base = "stringxxx";
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		for (int i=0;i< Integer.MAX_VALUE;i++){
			String str = base + base;
			base = str;
			list.add(str.intern());
		}
	}
}