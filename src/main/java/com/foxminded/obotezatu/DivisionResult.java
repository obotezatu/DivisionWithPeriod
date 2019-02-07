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

	public List<Step> getDecimalStep() {
		return decimalStep;
	}

	public void setDecimalStep(List<Step> decimalStep) {
		this.decimalStep = decimalStep;
	}

	public String getResult() {
		long integerResult = 0;
		long decimalResult = 0;
		StringBuilder result = new StringBuilder();
		for (Step step : steps) {
			integerResult = (integerResult * 10) + step.getDivideResult();
		}
		for (Step step : decimalStep) {
			decimalResult = (decimalResult * 10) + step.getDivideResult();
		}
		result.append(integerResult).append(".").append(findDecimalPeriod(String.valueOf(decimalResult)));
		return result.toString();
	}

	public String findDecimalPeriod(String decimalResult) {
		int beginIndex = 0;
		while (countPeriod(decimalResult.substring(beginIndex)) == decimalResult.substring(beginIndex).length()) {
			beginIndex++;
		}
		int offset = countPeriod(decimalResult.substring(beginIndex));
		if ((offset == 1 && decimalResult.length() < 10) || beginIndex == decimalResult.length()) {
			return decimalResult;
		} else {
			return String.format(decimalResult.substring(0, beginIndex) + "("
					+ decimalResult.substring(beginIndex, beginIndex + offset) + ")");
		}
	}

	private int countPeriod(String inputString) {
		int period;
		String[] digits = inputString.split("");
		period = digits.length;
		for (int i = 1; i <= (digits.length / 2); i++) {
			int j;
			for (j = 0; j < digits.length - i;) {
				if (digits[j].equals(digits[j + i])) {
					j++;
				} else {
					break;
				}
			}
			if (j == (digits.length - i)) {
				period = i;
				break;
			}
		}
		return period;
	}
}
