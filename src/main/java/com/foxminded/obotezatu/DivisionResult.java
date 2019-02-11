package com.foxminded.obotezatu;

import java.util.ArrayList;
import java.util.List;

public class DivisionResult {

	private long dividend;
	private long divider;
	private List<Step> steps = new ArrayList<>();

	public long getDividend() {
		return dividend;
	}

	public void setDividend(long dividend) {
		this.dividend = dividend;
	}

	public long getDivider() {
		return divider;
	}

	public void setDivider(long divider) {
		this.divider = divider;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public double getResult() {
		long decimalResult = 0;
		long integerCount = String.valueOf(dividend).length();
		long decimalCount = 1;
		double result = 0;
		for (Step step : steps) {
			if (integerCount > 0) {
				result = (result * 10) + step.getDivideResult();
				integerCount--;
			} else {
				decimalResult = (decimalResult * 10) + step.getDivideResult();
				decimalCount *= 10;
			}
		}
		return result + ((double) decimalResult / decimalCount);
	}
}
