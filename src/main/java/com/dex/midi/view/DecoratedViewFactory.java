package com.dex.midi.view;

import java.util.Collection;

public interface DecoratedViewFactory<T> {
	
	DecoratedView<T> getDefaultView();

	DecoratedView<T> getView(String id);
	
	Collection<DecoratedView<T>> getViews();
	
	DecoratedView<T>[] getViewArray();
}
