package com.dex.midi.view;

import com.dex.midi.model.GuitarPosition;
import com.dex.midi.util.UniqueItem;

public interface FretboardView extends UniqueItem {
	
	public Object getLabel(GuitarPosition fret);
	
	public FretboardView shift();
	
	public FretboardView next();
	
	public boolean isShift();
	
}
