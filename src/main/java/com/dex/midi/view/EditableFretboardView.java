package com.dex.midi.view;

import com.dex.midi.model.GuitarPosition;

public interface EditableFretboardView extends FretboardView {

	public void setLabel(GuitarPosition fret, Object label);
}
