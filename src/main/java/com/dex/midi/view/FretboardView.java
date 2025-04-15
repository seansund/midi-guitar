package com.dex.midi.view;

import com.dex.midi.model.GuitarPosition;
import com.dex.midi.util.UniqueItem;

public interface FretboardView extends UniqueItem {
	
	Object getLabel(GuitarPosition fret);
	
	FretboardView shift();
	
	FretboardView next();
	
	boolean isShift();
	
}
