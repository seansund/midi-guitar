package com.dex.midi.util;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public abstract class FretboardFactoryConfig<T extends UniqueItem> {
	
	public static final String KEY_NAME = "name";
	public static final String KEY_CONFIG = "config";
	public static final String KEY_DECORATOR = "decorator";
	public static final String KEY_TYPE = "type";
	
	private final Map<String, T> map = new LinkedHashMap<>(20);
	private T defaultItem;
	
	protected FretboardFactoryConfig(String configFile) {
		super();
		
		try {
			List<T> l = buildChildren(configFile);
			defaultItem = l.getFirst();
			
			for (T v : l) {
				map.put(v.getId(), v);
			}
		} catch (IOException ex) {
			defaultItem = buildDefaultChild();
			map.put(defaultItem.getId(), defaultItem);
			
			SimpleLogger.log(Level.SEVERE, this, SimpleLogger.CONSTRUCTOR, String.format("Exception loading configFile: %s", configFile), ex);
		}
	}
	
	protected List<T> buildChildren(String configFile) throws IOException {
		
		HierarchicalHashMap map = HierarchicalHashMap.load(configFile, this.getClass());
		
		List<Object> keys = new ArrayList<>(map.keySet());
		keys.sort(new StringComparator());
		
		List<T> l = new ArrayList<>(keys.size());
		for (Object key : keys) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> value = (Map<Object, Object>)map.get(key);
			
			l.add(buildChild(value));
		}
		
		return l;
	}
	
	protected T buildDefaultChild() {
		return buildChild(null);
	}
	
	protected abstract T buildChild(Map<Object, Object> config);
	
	public T getDefault() {
		return defaultItem;
	}
	
	public T getItem(String id) {
		return map.get(id);
	}
	
	public Collection<T> getItems() {
		return map.values();
	}

}
