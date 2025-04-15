package com.dex.midi.view;

import com.dex.midi.model.GuitarPosition;
import com.dex.midi.util.UniqueItem;

import java.util.Map;

public interface DecoratedView<T> extends UniqueItem {
	
	void init(Map<Object, Object> obj);
	
	Decorator<T> getDecorator(GuitarPosition fret);
	
	Decorator<T> getDecorator(int stringIndex, int fretIndex);
}
