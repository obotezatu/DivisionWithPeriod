package com.foxminded.obotezatu;

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
		int decimalCount = 11;
		int integerCount = 0;
		long divisionStepsCount = dividendDigits.length + decimalCount;
		 while ((integerCount < dividendDigits.length || partialDividend != 0) && divisionStepsCount >= 0){
			if (integerCount < dividendDigits.length) {
				partialDividend = partialDividend * 10 + dividendDigits[integerCount];
			} else {
				partialDividend = partialDividend * 10;
			}
			long dividerMultiple = (partialDividend / divider) * divider;
			Step divisionStep = new Step();
			if (dividerMultiple != 0) {
				divisionStep.setDivideResult(partialDividend / divider);
				divisionStep.setPartialDividend(partialDividend);
				divisionStep.setDividerMultiple(dividerMultiple);
			}
			partialDividend = partialDividend - dividerMultiple;
			if (integerCount < dividendDigits.length) {
				divisionResult.getSteps().add(divisionStep);
			} else {
				divisionResult.getDecimalStep().add(divisionStep);
			}
			decimalCount--;
			integerCount++;
			divisionStepsCount--;
		}
		return divisionResult;
	}

	private long[] splitDividend(long dividend) {
		int dividendLength = String.valueOf(dividend).length();
		long[] dividendDigits = new long[dividendLength];
		long dividendRest = dividend;
		for (int i = dividendLength - 1; i >= 0; i--) {
			dividendDigits[i] = dividendRest % 10;
			dividendRest = dividendRest / 10;
		}
		return dividendDigits;
	}
}