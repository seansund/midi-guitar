package com.dex.midi.view;

import java.util.List;

public interface FretboardViewFactory {
	
	List<? extends FretboardView> getViews();
	
	FretboardView getCurrentView();
}
