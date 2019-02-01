package com.foxminded.obotezatu;

import java.util.ArrayList;
import java.util.List;

public class DivisionResult {
	
	private long dividend;
	private long divider;
	private List<Step> steps = new ArrayList<>();
	private List<Step> decimalStep = new ArrayList<>();

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

	/*public long getResult() {
		long result = 0;
		for (Step step : steps) {
			result = (result * 10) + step.getDivideResult();
		}
		return result;
	}*/
	public String getResult() {
		long integerResult = 0;
		long decimalResult =0;
		StringBuilder result = new StringBuilder();
		for (Step step : steps) {
			integerResult = (integerResult * 10) + step.getDivideResult();
		}
		for (Step step : decimalStep) {
			decimalResult = (decimalResult * 10) + step.getDivideResult();
		}
		result.append(integerResult).append(".").append(decimalResult);
		return result.toString();
	}

	public List<Step> getDecimalStep() {
		return decimalStep;
	}

	public void setDecimalStep(List<Step> decimalStep) {
		this.decimalStep = decimalStep;
	}
}
