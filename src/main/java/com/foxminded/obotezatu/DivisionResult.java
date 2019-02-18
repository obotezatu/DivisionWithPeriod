package com.foxminded.obotezatu;

import java.util.ArrayList;
import java.util.List;

public class DivisionResult {

	private long dividend;
	private long divider;
	private List<Step> steps = new ArrayList<>();
	private String result;

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

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}
}
