package com.me.smartbill.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.result_layout);

		retrieveInput();

		Calculator result = new Calculator()
				.calculate(bill, isCash, tip, split);

		System.out.println("bill : " + bill);
		System.out.println("isCash : " + isCash);
		System.out.println("tip : " + tip);
		System.out.println("split : " + split);
		
		updateView(result);
		
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


	private void updateView(Calculator result) {
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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Action bar's back button
		onBackPressed();
		return true;
	}
	
}
