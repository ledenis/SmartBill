package com.me.smartbill.preference;

import com.me.smartbill.R;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

public class SplitPreference extends DialogPreference {
	
	public static final int DEFAULT = 1;
	private NumberPicker nbPicker;

	public SplitPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setDialogLayoutResource(R.layout.split_pref_layout);
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		
		nbPicker = (NumberPicker) view.findViewById(R.id.nbPicker);
		int persistedInt = getPersistedInt(DEFAULT);
		
		nbPicker.setMinValue(1);
		nbPicker.setMaxValue(1000);
		nbPicker.setValue(persistedInt);
		nbPicker.setWrapSelectorWheel(false);
	}

	@Override
	protected void onDialogClosed(boolean positive) {
		super.onDialogClosed(positive);
		
		if (positive) {
			persistInt(nbPicker.getValue());
		}
	}
	
}
