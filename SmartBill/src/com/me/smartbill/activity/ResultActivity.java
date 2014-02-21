package com.me.smartbill.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.me.smartbill.R;
import com.me.smartbill.preference.SplitPreference;
import com.me.smartbill.preference.TipPreference;

public class ResultActivity extends Activity {
	// XXX
	// private float bill;
	private boolean isCash;
	private float tip;
	private int split;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.result_layout);

		retrieveInput();

		System.out.println("isCash : " + isCash);
		System.out.println("tip : " + tip);
		System.out.println("split : " + split);
	}

	private void retrieveInput() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		isCash = prefs.getBoolean("cash", false);
		tip = prefs.getInt("tip", TipPreference.DEFAULT) / 100f;
		split = prefs.getInt("split", SplitPreference.DEFAULT);
	}
}
