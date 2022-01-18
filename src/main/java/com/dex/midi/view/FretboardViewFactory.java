package com.dex.midi.view;

import java.util.List;

public interface FretboardViewFactory {
	
	public List<? extends FretboardView> getViews();
	
	public FretboardView getCurrentView();
}
