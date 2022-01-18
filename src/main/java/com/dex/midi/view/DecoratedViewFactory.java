package com.dex.midi.view;

import java.util.Collection;

public interface DecoratedViewFactory<T> {
	
	public DecoratedView<T> getDefaultView();

	public DecoratedView<T> getView(String id);
	
	public Collection<DecoratedView<T>> getViews();
	
	public DecoratedView<T>[] getViewArray();
}
