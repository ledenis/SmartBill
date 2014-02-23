package com.me.smartbill.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
		
		// bill
		text = (TextView) findViewById(R.id.toPayText);
		text.setText("" + result.getToPay());
		
		// tipping
		text = (TextView) findViewById(R.id.tipPercentText);
		text.setText("" + result.getNewTip());
		text = (TextView) findViewById(R.id.tipValueText);
		text.setText("" + result.getNewTip());
		
		// total
		text = (TextView) findViewById(R.id.totalText);
		text.setText("" + result.getTotal());
		
		// nb persons
		text = (TextView) findViewById(R.id.splitText);
		text.setText("" + split);
	}
	
}
