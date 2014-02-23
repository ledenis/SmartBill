package com.me.smartbill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.me.smartbill.R;
import com.me.smartbill.preference.SplitPreference;
import com.me.smartbill.preference.TipPreference;
import com.me.smartbill.utils.DecimalDigitsInputFilter;

public class MainActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	private EditText billText;

	@SuppressWarnings("deprecation")
	// compatibility with older API
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Include the preference screen
		addPreferencesFromResource(R.xml.prefs);

		// Init preference summaries
		for (String key : PreferenceManager.getDefaultSharedPreferences(this)
				.getAll().keySet()) {
			updateSummary(key);
		}

		// Init EditText
		billText = (EditText) findViewById(R.id.billText);
		billText.setFilters(new InputFilter[] {// limit to 2 decimal digits
		new DecimalDigitsInputFilter(5, 2) });
	}

	@Override
	protected void onResume() {
		super.onResume();

		PreferenceManager.getDefaultSharedPreferences(this)
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void calculateClick(View view) {
		String billString = billText.getText().toString();
		if (billString.isEmpty()) {
			billText.requestFocus();
			return;
		}

		// Else, switch to ResultActivity
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtra("bill", Float.parseFloat(billString));

		startActivity(intent);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences preferences,
			String key) {
		@SuppressWarnings("deprecation")
		// compatibility with older API
		Preference pref = findPreference(key);

		updateSummary(pref.getKey());
	}

	@SuppressWarnings("deprecation")
	private void updateSummary(String key) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		if (key.equals("tip")) {
			System.out.println("tipprf");
			TipPreference p = (TipPreference) findPreference(key);
			p.setSummary("" + prefs.getInt(key, TipPreference.DEFAULT) + " %");
		} else if (key.equals("split")) {
			SplitPreference p = (SplitPreference) findPreference(key);
			p.setSummary("" + prefs.getInt(key, SplitPreference.DEFAULT) + " "
					+ getString(R.string.persons));
		}
	}

}
