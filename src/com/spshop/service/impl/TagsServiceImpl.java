package com.spshop.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.TagsService;

public class TagsServiceImpl implements TagsService {
	
	private static Logger logger = Logger.getLogger(TagsServiceImpl.class);
	
	private static String[] INDEX_KEYS = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0_9"};
	
	private String indexFile;
	private Map<String,String[]> tagIndexs;
	private SortedMap<String, List<String>> indexs = new TreeMap<String, List<String>>();
	private Properties properties = new Properties();
	private SortedMap<String, String[]> indexsAll =  new TreeMap<String, String[]>();
	public TagsServiceImpl() {
	}
	
	public void init(){
		if(null == tagIndexs){
			
			tagIndexs = new TreeMap<String, String[]>();
			try {
				properties.load(new FileInputStream(new File(indexFile)));
			} catch (Exception e) {
				logger.error("cannot read confile file: " + indexFile,e);
			}
			for(String key : INDEX_KEYS){
				String kw = properties.getProperty(key);
				if(null != kw){
					String[] kws = kw.split(",");
					tagIndexs.put(key, kws);
					String[] keys = tagIndexs.get(key);
					List<String> keyList = new ArrayList<String>();
					indexsAll.put(key, kws);
					for(int i = 0; i < INDEX_SIZE_PER_KEY && i < keys.length && null != keys; i++){
						keyList.add(keys[i]);
					}
					indexs.put(key, keyList);
				}
			}
		}
	}

	@Override
	public List<String> getTags(String key, int page, int size) {
		
		List<String> keys = new ArrayList<String>();
		int count = getKeywordCount(key);
		if(count > 0){
			String[] kyArray = indexsAll.get(key);
			if((page-1)*size + size <= kyArray.length){
				keys = Arrays.asList((String[])ArrayUtils.subarray(kyArray, (page-1)*size, (page-1)*size+size-1));
			}else if((page-1)*size <= kyArray.length){
				keys = Arrays.asList((String[])ArrayUtils.subarray(kyArray, (page-1)*size, kyArray.length -1));
			}
		}
		
		return keys;
	}
	
	public int getKeywordCount(String key){
		String[] keys = indexsAll.get(key);
		
		return keys.length;
	}
	

	@Override
	public SortedMap<String, List<String>> getTagIndexs() {
		return indexs;
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}
	
	public static void main(String[] args) {
		TagsService service = ServiceFactory.getService(TagsService.class);
		service.getTagIndexs().get("A");
	}

}
