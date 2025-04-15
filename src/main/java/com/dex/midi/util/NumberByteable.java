package com.dex.midi.util;

public class NumberByteable<T extends Number> implements Byteable {
	
	private final T value;
	
	public NumberByteable(T value) {
		super();
		
		this.value = value;
	}
	
	@Override
	public byte byteValue() {
		return value.byteValue();
	}

}
