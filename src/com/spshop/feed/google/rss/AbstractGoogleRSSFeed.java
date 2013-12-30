package com.spshop.feed.google.rss;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.spshop.web.FeedController;

public abstract class AbstractGoogleRSSFeed {
	private static Logger logger = Logger.getLogger(FeedController.class);
	private final static Properties feedConfig = new Properties();
	private final static Properties currency = new Properties();
	
	static DecimalFormat formater = new DecimalFormat("#0.##");
	static {
		try {
			feedConfig.load(FeedController.class
					.getResourceAsStream("/rssFeedConfig.properties"));
			currency.load(FeedController.class
					.getResourceAsStream("/currency.properties"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static class GoogleCategoryMapper {
		public static String mapping(String category) {
			return feedConfig.getProperty(category);
		}
	}
	
	public static String getProperty(String key){
		return feedConfig.getProperty(key);
	}
	
	public static String getCurrency(String money){
		return currency.getProperty(money);
	}
}
