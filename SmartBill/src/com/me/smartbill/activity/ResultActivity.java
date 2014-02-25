package com.me.smartbill.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.me.smartbill.R;
import com.me.smartbill.preference.SplitPreference;
import com.me.smartbill.preference.TipPreference;
import com.me.smartbill.utils.Calculator;

public class ResultActivity extends Activity {
	private float bill;
	private boolean isCash;
	private float tip;
	private int split;
	private Calculator calculator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.result_layout);

		retrieveInput();

		calculator = new Calculator();
		Calculator result = calculator
				.calculate(bill, isCash, tip, split, true);

		// Init radio buttons
		((RadioGroup) findViewById(R.id.roundGroup))
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int id) {
						Calculator result;
						if (id == R.id.upRadio) {
							result = calculator.calculate(bill, isCash, tip,
									split, true);
						} else {
							result = calculator.calculate(bill, isCash, tip,
									split, false);
						}
						updateView(result, isCash && tip != 0f);
					}
				});
		
		updateView(result, isCash && tip != 0f);

		// Add back button
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void retrieveInput() {
		bill = getIntent().getFloatExtra("bill", -1);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		isCash = prefs.getBoolean("cash", false);
		tip = prefs.getInt("tip", TipPreference.DEFAULT) / 100f;
		split = prefs.getInt("split", SplitPreference.DEFAULT);
	}

	private void updateView(Calculator result, boolean isRounding) {
		TextView text;

		// toPay
		text = (TextView) findViewById(R.id.toPayText);
		text.setText("" + result.getToPay());

		// bill
		text = (TextView) findViewById(R.id.billText);
		text.setText("" + bill);

		// tipping
		text = (TextView) findViewById(R.id.tipPercentText);
		text.setText("" + result.getNewTip() * 100);
		text = (TextView) findViewById(R.id.tipValueText);
		text.setText("" + result.getNewTipValue());

		// total
		text = (TextView) findViewById(R.id.totalText);
		text.setText("" + result.getTotal());

		// nb persons
		text = (TextView) findViewById(R.id.splitText);
		text.setText("" + split);

		// Hide radio buttons if we don't round the result
		if (!isRounding) {
			findViewById(R.id.roundLayout).setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Action bar's back button
		onBackPressed();
		return true;
	}

}
