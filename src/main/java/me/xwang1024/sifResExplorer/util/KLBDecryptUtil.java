package me.xwang1024.sifResExplorer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KLBDecryptUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(KLBDecryptUtil.class);
	private static String decryptProgName;

	public static void Decrypt(File path) {
		File[] children = path.listFiles();
		for (File child : children) {
			if (child.isDirectory()) {
				Decrypt(child);
			} else if (child.isFile()) {
				try {
					Process p = Runtime.getRuntime().exec(
							new String[] { decryptProgName, "--force",
									child.getAbsolutePath() });
					BufferedReader br = new BufferedReader(
							new InputStreamReader(p.getInputStream(), "gbk"));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					String result = sb.toString();
					if (result.contains("Usage")) {
						logger.error("Command error!");
					} else if (result.contains("Done!")) {
						logger.info("Decrypt ok: {}", child.getName());
					} else if (result.contains("Decrypted file")) {
						logger.info("Decrypted: {}", child.getName());
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// 初始化解密程序路径
		File decryptor = new File("Decrypt.exe");
		if (!decryptor.exists()) {
			logger.error("Could not find decryptor.");
			System.exit(-1);
		}
		decryptProgName = decryptor.getAbsolutePath();

		String path = "D:/data/Application Support";
		File f = new File(path);
		if (!f.exists()) {
			logger.error("Could not find path \"{}\".", path);
			System.exit(-1);
		}
		Decrypt(f);
	}
}
