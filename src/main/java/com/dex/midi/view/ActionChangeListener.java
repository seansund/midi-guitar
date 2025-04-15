package com.dex.midi.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ActionChangeListener<T> implements ActionListener {
	
	private int currentIndex = 0;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int index = getActionIndex(e);
		
		if (index != currentIndex) {
			currentIndex = index;
			
			T value = getActionValue(e);
			handleAction(value);
		}
	}
	
	protected int getActionIndex(ActionEvent e) {
		JComboBox b = (JComboBox)e.getSource();
		
		return b.getSelectedIndex();
	}
	
	@SuppressWarnings("unchecked")
	protected T getActionValue(ActionEvent e) {
		JComboBox<T> b = (JComboBox<T>)e.getSource();
		
		return (T)b.getSelectedItem();
	}
	
	protected abstract void handleAction(T value);

}
