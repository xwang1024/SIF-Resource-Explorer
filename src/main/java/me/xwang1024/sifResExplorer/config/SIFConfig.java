package me.xwang1024.sifResExplorer.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SIFConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(SIFConfig.class);

	public static SIFConfig instance;
	private String filePath = "sif.xml";
	private Map<String, String> conf = Collections
			.synchronizedMap(new HashMap<String, String>());

	public static SIFConfig getInstance() {
		if (instance == null) {
			instance = new SIFConfig();
		}
		return instance;
	}

	private SIFConfig() {
	}

	public void loadConfig() throws DocumentException {
		if(!new File(filePath).exists()) {
			logger.info("Could not find sif.xml.");
			return;
		}
		logger.info("Read config file sif.xml");
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(filePath));
		List nodes = doc.getRootElement().elements("config");
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			String name = elm.attributeValue("name");
			String value = elm.attributeValue("value");
			if (name != null && value != null) {
				conf.put(name, value);
				logger.info("Read conf: {}={}", name, value);
			}
		}
	}

	public void saveConfig() throws IOException {
		logger.info("Save config file sif.xml");
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("configuration");
		for (String name : conf.keySet()) {
			String value = "" + conf.get(name);
			Element conf = DocumentHelper.createElement("config");
			conf.addAttribute(new QName("name"), name);
			conf.addAttribute(new QName("value"), value);
			root.add(conf);
		}
		doc.setRootElement(root);

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8"); // 指定XML编码
		XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
		writer.write(doc);
		writer.close();
	}

	public String get(String name) {
		return conf.get(name);
	}

	public String set(String name, String value) {
		return conf.put(name, value);
	}

	public static class ConfigName {
		public static final String dbPath = "dbPath";
		public static final String assetsPath = "assetsPath";
	}
}
