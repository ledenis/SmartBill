package com.me.smartbill.preference;

import com.me.smartbill.R;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TipPreference extends DialogPreference implements
		OnSeekBarChangeListener {

	public static final int DEFAULT = 15;
	private SeekBar seekBar;
	private TextView textView;

	public TipPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		setDialogLayoutResource(R.layout.tip_pref_layout);
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);

		seekBar = (SeekBar) view.findViewById(R.id.seekBar);
		textView = (TextView) view.findViewById(R.id.seekbarText);

		seekBar.setProgress(getPersistedInt(DEFAULT));
		seekBar.setOnSeekBarChangeListener(this);
		updateText(seekBar.getProgress());
	}

	@Override
	protected void onDialogClosed(boolean positive) {
		super.onDialogClosed(positive);

		if (positive) {
			persistInt(seekBar.getProgress());
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		updateText(progress);
	}

	private void updateText(int progress) {
		textView.setText("" + progress + " %");
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}
