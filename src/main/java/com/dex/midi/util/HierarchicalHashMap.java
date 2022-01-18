package com.dex.midi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HierarchicalHashMap extends HashMap<Object, Object> {
	
	private static final long serialVersionUID = 8936887183221710869L;
	
	public static final String DELIMITER = ".";
	public static final String DEFAULT = "<DEFAULT>";
	
	private String delimiter = HierarchicalHashMap.DELIMITER;

	public HierarchicalHashMap() {
		this(DELIMITER);
	}

	public HierarchicalHashMap(String delimiter) {
		super();
		
		this.delimiter = delimiter;
	}
	
	public HierarchicalHashMap(Map<? extends Object, ? extends Object> m) {
		this(m, DELIMITER);
	}
	
	public HierarchicalHashMap(Map<? extends Object, ? extends Object> m, String delimiter) {
		this.putAll(m);
		
		this.delimiter = delimiter;
	}
	
	public static HierarchicalHashMap load(String configFile, Class<?> clazz) throws IOException {
		
		InputStream in = null;
		Properties props = new Properties();
		try {
			in = clazz.getResourceAsStream(configFile);
			props.load(in);
		} finally {
			if (in != null) {
				try { in.close(); } catch (IOException ignore) { }
				in = null;
			}
		}
		
		return new HierarchicalHashMap(props);
	}
	
	public Object get(String key) {
		Object value = null;
		
		int pos = key.indexOf(delimiter);
		if (pos > -1) {
			String subKey = key.substring(pos+1);
			key = key.substring(0, pos);
			
			value = super.get(key);
			if (value instanceof HierarchicalHashMap) {
				HierarchicalHashMap m = (HierarchicalHashMap)value;
				
				value = m.get(subKey);
			}
		} else {
			value = super.get(key);
			
			if (value instanceof HierarchicalHashMap) {
				HierarchicalHashMap m = (HierarchicalHashMap)value;
				
				value = m.get(DEFAULT);
			}
		}
		
		return value;
	}

	@Override
	public Object put(Object k, Object value) {
		
		String key = k.toString();
		
		int pos = key.indexOf(delimiter);
		if (pos > -1) {
			String subKey = key.substring(pos+1);
			key = key.substring(0, pos);
			
			Object obj = super.get(key);
			if (obj instanceof HierarchicalHashMap) {
				HierarchicalHashMap m = (HierarchicalHashMap)obj;
				
				m.put(subKey, value);
				
				value = m;
			} else if (obj != null) {
				HierarchicalHashMap m = new HierarchicalHashMap(delimiter);
				
				m.put(DEFAULT, obj);
				m.put(subKey, value);
				
				value = m;
			} else {
				HierarchicalHashMap m = new HierarchicalHashMap(delimiter);
				
				m.put(subKey, value);
				
				value = m;
			}
		} else {
			Object obj = super.get(key);
			
			if (obj instanceof HierarchicalHashMap) {
				HierarchicalHashMap m = (HierarchicalHashMap)obj;
				
				m.put(DEFAULT, value);
				
				value = m;
			}
		}
		
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends Object, ? extends Object> m) {
		if (m != null) {
			for (Entry<? extends Object, ? extends Object> e : m.entrySet()) {
				this.put(e.getKey(), e.getValue());
			}
		}
	}
	
	@Override
	public Object clone() {
		HierarchicalHashMap map = new HierarchicalHashMap(this);
		
		return map;
	}
}
