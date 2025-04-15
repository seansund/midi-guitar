package com.dex.midi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import static com.dex.midi.util.InputStreamLoader.buildInputStream;

public class HierarchicalHashMap extends HashMap<Object, Object> {
	
	@Serial
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
	
	public HierarchicalHashMap(Map<?, ?> m) {
		this(m, DELIMITER);
	}
	
	public HierarchicalHashMap(Map<?, ?> m, String delimiter) {
		this.putAll(m);
		
		this.delimiter = delimiter;
	}
	
	public static HierarchicalHashMap load(String configFile, Class<?> clazz) throws IOException {
		final String method = "load";

		Properties props = new Properties();
		try (InputStream in = buildInputStream(configFile, clazz)) {
			props.load(in);
		} catch (Exception e) {
			SimpleLogger.log(Level.WARNING, HierarchicalHashMap.class, method, "Unable to load config file: " + configFile, e);
		}
		
		return new HierarchicalHashMap(props);
	}
	
	public Object get(String key) {

		final int pos = key.indexOf(delimiter);
		if (pos > -1) {
			final String subKey = key.substring(pos+1);
			final String newKey = key.substring(0, pos);
			
			final Object value = super.get(newKey);
			if (value instanceof HierarchicalHashMap m) {
                return m.get(subKey);
			}

			return value;
		} else {
			final Object value = super.get(key);
			
			if (value instanceof HierarchicalHashMap m) {
                return m.get(DEFAULT);
			}

			return value;
		}
	}

	@Override
	public Object put(final Object k, final Object value) {
		
		final String key = k.toString();
		
		int pos = key.indexOf(delimiter);
		if (pos > -1) {
			final String subKey = key.substring(pos+1);
			final String newKey = key.substring(0, pos);

			return putInternal(newKey, subKey, value);
		}
		
		return putInternal(key, key, value);
	}

	protected Object putInternal(final String key, final String subKey, final Object value) {
		final Object obj = super.get(key);

		if (obj instanceof HierarchicalHashMap m) {
			m.put(subKey, value);

			return super.put(key, m);
		} else if (obj != null) {
			HierarchicalHashMap m = new HierarchicalHashMap(delimiter);

			m.put(DEFAULT, obj);
			m.put(subKey, value);

			return super.put(key, m);
		} else {
			HierarchicalHashMap m = new HierarchicalHashMap(delimiter);

			m.put(subKey, value);

			return super.put(key, m);
		}
	}

	@Override
	public void putAll(Map<?, ?> m) {
		if (m != null) {
			for (Entry<?, ?> e : m.entrySet()) {
				this.put(e.getKey(), e.getValue());
			}
		}
	}
	
	@Override
	public Object clone() {
		return new HierarchicalHashMap(this);
	}
}
