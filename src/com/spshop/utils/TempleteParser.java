package com.spshop.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 
 * @author zhuwans
 * 
 */
public class TempleteParser {

	private static Logger logger = Logger.getLogger(TempleteParser.class);

	private static String TPL_FILE = null;

	private static Configuration cfg = null;

	static {
		initCFG();
	}

	/**
	 * 
	 * @param templeteType
	 * @param variables
	 * @return return null if no content
	 */
	public static String parseMailContent(String templeteType,
			Map<String, Object> variables) {

		try {
			Template tpl = cfg.getTemplate(templeteType);

			StringWriter writer = new StringWriter();

			tpl.process(variables, writer);

			return writer.toString();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.warn(e.getMessage(), e);
			return null;
		}

	}

	private static void initConfigFile() {
		Properties prop = new Properties();
		try {
			prop.load(TempleteParser.class
					.getResourceAsStream("/templateConf.properties"));
			TPL_FILE = prop.getProperty("template.dir");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void initCFG() {
		if (null == TPL_FILE) {
			initConfigFile();
		}

		cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File(TPL_FILE));
		} catch (IOException e) {
			// logger.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setOutputEncoding("UTF-8");
	}

	public static void main(String[] args) {
		Map<String, Object> root = new HashMap<String, Object>();

		root.put("title", "Welcome to HoneyBuy");

		String content = TempleteParser.parseMailContent("2.tpl", root);

		System.out.println(content);
	}
}
