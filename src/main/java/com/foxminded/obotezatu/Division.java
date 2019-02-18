package com.foxminded.obotezatu;

import java.util.HashMap;

public class Division {

	public DivisionResult divide(long dividend, long divider) {
		if (divider == 0) {
			throw new ArithmeticException("The divider cannot be 0!");
		}
		DivisionResult divisionResult = new DivisionResult();
		divisionResult.setDividend(dividend);
		divisionResult.setDivider(divider);
		long integerPart = dividend / divider;
		long[] dividendDigits = splitDividend(dividend);
		long partialDividend = 0;
		for (long digit : dividendDigits) {
			partialDividend = partialDividend * 10 + digit;
			long dividerMultiple = (partialDividend / divider) * divider;
			Step divisionStep = new Step();
			if (dividerMultiple != 0 || digit == dividendDigits[dividendDigits.length - 1]) {
				divisionStep.setPartialDividend(partialDividend);
				divisionStep.setDividerMultiple(dividerMultiple);
			}
			partialDividend = partialDividend - dividerMultiple;
			divisionResult.getSteps().add(divisionStep);
		}
		String result = "";
		HashMap<Long, Integer> multiples = new HashMap<>();
		int i = 0;
		multiples.put(partialDividend, i);
		i++;
		while (true) {
			Step divisionStep = new Step();
			if (partialDividend == 0 || i > 10) {
				if ("".equals(result)) {
					divisionResult.setResult(String.format("%d", integerPart));
				} else {
					divisionResult.setResult(String.format("%d.%s", integerPart, result));
				}
				divisionStep.setPartialDividend(partialDividend);
				return divisionResult;
			}
			divisionStep.setPartialDividend(partialDividend * 10);
			divisionStep.setDividerMultiple(((partialDividend * 10) / divider) * divider);
			divisionResult.getSteps().add(divisionStep);
			long digit = (partialDividend * 10) / divider;
			partialDividend = (partialDividend * 10) % divider;
			result += digit;
			if (!multiples.containsKey(partialDividend)) {
				multiples.put(partialDividend, i);
				i++;
			} else {
				int val = multiples.get(partialDividend);
				divisionResult.setResult(
						String.format("%d.%s(%s)", integerPart, result.substring(0, val), result.substring(val)));
				return divisionResult;
			}
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