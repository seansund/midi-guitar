package com.dex.midi.view;


public interface FretboardViewEventListener {

	public void viewFactoryChanged(FretboardViewFactory factory);
	
	public void viewChanged(String viewId);
	
}
