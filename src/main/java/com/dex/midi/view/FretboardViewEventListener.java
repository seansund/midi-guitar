package com.dex.midi.view;


public interface FretboardViewEventListener {

	void viewFactoryChanged(FretboardViewFactory factory);
	
	void viewChanged(String viewId);
	
}
