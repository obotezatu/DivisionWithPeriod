package com.foxminded.obotezatu;

import java.util.ArrayList;
import java.util.List;

public class DivisionResult {

	private long dividend;
	private long divider;
	private List<Step> steps = new ArrayList<>();
	private double result;

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

	public void setResult(List<Step> steps) {
		long integer = 0;
		long decimal = 0;
		long decimalCount = 1;
		for (int i = 0; i < steps.size(); i++) {
			if (steps.get(i).isIntegerPart) {
				integer = integer * 10 + steps.get(i).getDivideResult();
			} else {
				decimal = decimal * 10 + steps.get(i).getDivideResult();
				decimalCount *= 10;
			}
		}
		this.result = integer + ((double) decimal / decimalCount);
	}

	public double getResult() {
		return result;
	}
}
