package com.foxminded.obotezatu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Division {

	public DivisionResult divide(long dividend, long divider) {
		if (divider == 0) {
			throw new ArithmeticException("The divider cannot be 0!");
		}
		DivisionResult divisionResult = new DivisionResult();
		divisionResult.setDividend(dividend);
		divisionResult.setDivider(divider);
		long[] dividendDigits = splitDividend(dividend);
		long partialDividend = 0;

		for (long digit : dividendDigits) {
			partialDividend = partialDividend * 10 + digit;
			long dividerMultiple = (partialDividend / divider) * divider;
			Step divisionStep = new Step();
			if (dividerMultiple != 0 || digit == dividendDigits[dividendDigits.length - 1]) {
				divisionStep.setPartialDividend(partialDividend);
				divisionStep.setDividerMultiple(dividerMultiple);
				divisionStep.setDivideResult(partialDividend / divider);
			}
			partialDividend = partialDividend - dividerMultiple;
			divisionResult.getSteps().add(divisionStep);
		}
		HashMap<Long, Integer> multiples = new HashMap<>();
		int i = 0;
		multiples.put(partialDividend, i);
		i++;
		while (true) {
			Step divisionStep = new Step();
			divisionStep.isIntegerPart = false;
			if (partialDividend == 0 || i > 10) {
				divisionResult.setResult(divisionResult.getSteps());
				return divisionResult;
			}
			divisionStep.setPartialDividend(partialDividend * 10);
			divisionStep.setDividerMultiple(((partialDividend * 10) / divider) * divider);
			long digit = (partialDividend * 10) / divider;
			divisionStep.setDivideResult(digit);
			partialDividend = (partialDividend * 10) % divider;
			if (!multiples.containsKey(partialDividend)) {
				multiples.put(partialDividend, i);
				i++;
			} else {
				divisionResult.getSteps().add(divisionStep);
				List<Step> steps = divisionResult.getSteps();
				Iterator<Step> iterator = steps.iterator();
				int periodStart = 0;
				while (iterator.hasNext()) {
					Step step = (Step) iterator.next();
					if (step.getPartialDividend() == partialDividend * 10) {
						periodStart = steps.indexOf(step);
						break;
					}
				}
				for (int j = periodStart; j < steps.size(); j++) {
					steps.get(j).isPeriod = true;
				}
				divisionResult.setResult(divisionResult.getSteps());
				return divisionResult;
			}
			divisionResult.getSteps().add(divisionStep);
		}
	}

	private long[] splitDividend(long dividend) {
		int dividendLength = String.valueOf(dividend).length();
		long[] dividendDigits = new long[dividendLength];
		for (int i = dividendLength - 1; i >= 0; i--) {
			dividendDigits[i] = dividend % 10;
			dividend = dividend / 10;
		}
		return dividendDigits;
	}
}