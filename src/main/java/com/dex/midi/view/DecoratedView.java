package com.dex.midi.view;

import com.dex.midi.model.GuitarPosition;
import com.dex.midi.util.UniqueItem;

import java.util.Map;

public interface DecoratedView<T> extends UniqueItem {
	
	public void init(Map<Object, Object> obj);
	
	public Decorator<T> getDecorator(GuitarPosition fret);
	
	public Decorator<T> getDecorator(int stringIndex, int fretIndex);
}
