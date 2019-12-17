package com.example.example2;

import java.io.*;

public class Test extends ClassLoader {
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) throws ClassNotFoundException {

		Test test = new Test();
		Class c = test.loadClass("/Users/jack/git_hub/spring-data-jpa-guide/2.0.0/example2/target/classes/com/example/example2/StringTest.class");
		System.out.println(c.getName());
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (name.indexOf("StringTest") < 1) {
			return super.loadClass(name);
		}
		byte[] classBytes = null;
		try {
//			InputStream in = new FileInputStream(new File(name));
//			InputStream in = getResourceAsStream(new File(name));
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(name)));
			byte[] buffer = new byte[BUFFER_SIZE];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int n = -1;
			while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				out.write(buffer, 0, n);
			}
			classBytes = out.toByteArray();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defineClass("com.example.example2.StringTest", classBytes, 0, classBytes.length);
	}
}