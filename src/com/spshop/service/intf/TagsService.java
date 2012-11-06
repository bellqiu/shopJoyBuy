package com.spshop.service.intf;

import java.util.List;
import java.util.SortedMap;

public interface TagsService {
	static final int INDEX_SIZE_PER_KEY = 5;
	List<String> getTags(String key, int page, int size);
	SortedMap<String,List<String>> getTagIndexs();
	int getKeyworkCount(String key);
}
