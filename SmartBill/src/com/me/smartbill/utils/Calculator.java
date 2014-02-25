package com.me.smartbill.utils;

public class Calculator {
	private float toPay; // amount to pay, for One person
	private float total; // amount to pay, in total
	private float newTip; // tipping in %
	private float newTipValue; // tipping in £

	public Calculator() {
	}

	/**
	 * Call this before the getters
	 */
	public Calculator calculate(float bill, boolean isCash, float tip,
			int split, boolean roundUp) {
		float tippedOne; // tipped amount of the bill, for One person

		// Calculate tipped amount
		if (tip != 0f) {
			tippedOne = bill * (1 + tip) / split;
			System.out.println("tippedOne (includes splitted) " + tippedOne);
		} else {
			tippedOne = bill / split;
		}

		// Calculate rounded amount
		if (isCash && tip != 0f) {
			// tipped & rounded amount of the bill, for One person
			float roundedOne;
			float rounded; // same, but in total

			roundedOne = roundCash(tippedOne, roundUp);
			rounded = roundedOne * split;
			System.out.println("roundedOne " + roundedOne);
			System.out.println("rounded " + rounded);
			if (rounded < bill) {
				// fallback
				System.out.println("fallback");
				rounded = roundCash(tippedOne, true) * split;
				System.out.println("rounded " + rounded);
			}

			newTip = (rounded / bill) - 1;
			toPay = roundedOne;
			total = rounded;
		} else {
			toPay = tippedOne;
			newTip = tip;
			total = tippedOne * split;
		}
		newTipValue = total - bill;

		return this; // for chaining
	}

	private static float roundCash(float bill, boolean roundUp) {
		float roundBy;
		if (bill < 2) {
			roundBy = 0.10f;
		} else if (bill < 5) {
			roundBy = 0.20f;
		} else if (bill < 10) {
			roundBy = 0.50f;
		} else if (bill < 20) {
			roundBy = 1f;
		} else if (bill < 50) {
			roundBy = 2;
		} else if (bill < 100) {
			roundBy = 5f;
		} else if (bill < 200) {
			roundBy = 10f;
		} else if (bill < 500) {
			roundBy = 20f;
		} else {
			roundBy = 50f;
		}

		return roundBy(bill, roundBy, roundUp);
	}

	private static float roundBy(float bill, float by, boolean roundUp) {
		float rounded;
		if (roundUp) {
			rounded = (float) (Math.ceil(bill / by) * by);
		} else {
			rounded = (float) (Math.floor(bill / by) * by);
		}

		return rounded;
	}

//	private static float roundUpBy(float bill, float by) {
//		float rounded = (float) (Math.ceil(bill / by) * by);
//
//		return rounded;
//	}

	// ------------
	// Getters
	// ------------

	public float getToPay() {
		return toPay;
	}

	public float getTotal() {
		return total;
	}

	public float getNewTip() {
		return newTip;
	}

	public float getNewTipValue() {
		return newTipValue;
	}
}
