package com.foxminded.obotezatu;

import java.util.ListIterator;

public class DivisionFormatter {

	public String format(DivisionResult divisionResult) {
		if (divisionResult.getDividend() == 0) {
			return "0";
		}
		StringBuilder formattedResult = new StringBuilder();
		ListIterator<Step> stepsIntegerIterator = divisionResult.getSteps().listIterator();
		ListIterator<Step> stepsDecimalIterator = divisionResult.getDecimalStep().listIterator();
		formattedResult.append(formatHead(divisionResult, stepsIntegerIterator, stepsDecimalIterator))
				.append(formatBody(divisionResult, stepsIntegerIterator, stepsDecimalIterator));
		return formattedResult.toString();
	}
	
	private String formatHead(DivisionResult divisionResult, ListIterator<Step> stepsIntegerIterator,
			ListIterator<Step> stepsDecimalIterator) {
		int dividendLength = String.valueOf(divisionResult.getDividend()).length();
		StringBuilder formattedResult = new StringBuilder();
		Step currentStep = stepsIntegerIterator.next();
		while (currentStep.getDividerMultiple() == 0 && stepsIntegerIterator.hasNext()) {
			currentStep = stepsIntegerIterator.next();
		}
		int partialDividendLength = (int) Math.log10(currentStep.getPartialDividend()) + 1;
		if (currentStep.getDividerMultiple() == 0) {
			currentStep = stepsDecimalIterator.next();
			while (currentStep.getDividerMultiple() == 0 && stepsDecimalIterator.hasNext()) {
				currentStep = stepsDecimalIterator.next();
			}
		}
		int dividerMultipleLength = currentStep.getDividerMultiple() != 0
				? (int) Math.log10(currentStep.getDividerMultiple()) + 1
				: 1;
		if (dividendLength < dividerMultipleLength) {
			formattedResult.append(String.format("_%-" + dividerMultipleLength + "d | %d%n",
					divisionResult.getDividend(), divisionResult.getDivider()));
		} else {
			formattedResult.append(String.format("_%" + dividendLength + "d | %d%n", divisionResult.getDividend(),
					divisionResult.getDivider()));
		}
		if (partialDividendLength > dividerMultipleLength) {
			formattedResult.append(String.format(" %s%-" + (dividendLength - 1) + "d |--------%n", " ",
					currentStep.getDividerMultiple()));
		} else {
			formattedResult
					.append(String.format(" %-" + dividendLength + "d |--------%n", currentStep.getDividerMultiple()));
		}
		formattedResult.append(String.format(" %-" + dividendLength + "s | %s%n",
				countDashes(currentStep.getPartialDividend()), divisionResult.getResult()));
		return formattedResult.toString();
	}

	private String formatBody(DivisionResult divisionResult, ListIterator<Step> stepsIntegerIterator,
			ListIterator<Step> stepsDecimalIterator) {
		StringBuilder formattedResult = new StringBuilder();
		StringBuilder indent = new StringBuilder(countIndents(stepsIntegerIterator));
		ListIterator<Step> stepsIterator = null;
		int decimalSize = getDecimalStepsSize(divisionResult);
		Step currentStep = null;
		while (stepsIntegerIterator.hasNext() || (stepsDecimalIterator.hasNext() && decimalSize > 0)) {
			if (stepsIntegerIterator.hasNext()) {
				currentStep = stepsIntegerIterator.next();
				stepsIterator = stepsIntegerIterator;
			} else {
				currentStep = stepsDecimalIterator.next();
				stepsIterator = stepsDecimalIterator;
				decimalSize--;
			}
			int partialDividentLength = String.valueOf(currentStep.getPartialDividend()).length();
			int dividerMultipleLength = String.valueOf(currentStep.getDividerMultiple()).length();
			if (currentStep.getPartialDividend() != 0 && currentStep.getDividerMultiple() != 0) {
				formattedResult.append(String.format("%s_%s%n", indent.toString(), currentStep.getPartialDividend()));
				if (partialDividentLength > dividerMultipleLength) {
					formattedResult.append(
							String.format("%s%s% d%n", indent.toString(), " ", currentStep.getDividerMultiple()));
				} else {
					formattedResult
							.append(String.format("%s% d%n", indent.toString(), currentStep.getDividerMultiple()));
				}
				formattedResult.append(
						String.format(" %s%s%n", indent.toString(), countDashes(currentStep.getPartialDividend())));
				indent.append(countIndents(stepsIterator));
			}
		}
		if (!stepsDecimalIterator.hasNext() || currentStep != null) {
			formattedResult.append(String.format("%s% d", indent.toString(),
					(currentStep.getPartialDividend() - currentStep.getDividerMultiple())));
		}
		return formattedResult.toString();
	}

	private String countDashes(long partialDividend) {
		StringBuilder dashes = new StringBuilder();
		int dashCount = String.valueOf(partialDividend).length();
		for (int i = 0; i < dashCount; i++) {
			dashes.append("-");
		}
		return dashes.toString();
	}

	private String countIndents(ListIterator<Step> stepsIntegerIterator) {
		StringBuilder indent = new StringBuilder();
		if (stepsIntegerIterator.hasPrevious()) {
			stepsIntegerIterator.previous();
			Step currentStep = stepsIntegerIterator.next();
			long indentCount = countDigits(currentStep);
			for (int i = 0; i < indentCount; i++) {
				indent.append(" ");
			}
		}
		return indent.toString();
	}

	private long countDigits(Step currentStep) {
		long partialDividentLength = (long) (Math.log10(currentStep.getPartialDividend()) + 1);
		long partialDividentLengthDiff = currentStep.getPartialDividend() - currentStep.getDividerMultiple();
		return partialDividentLengthDiff != 0
				? partialDividentLength - ((long) (Math.log10(partialDividentLengthDiff) + 1))
				: partialDividentLength;
	}

	private int getDecimalStepsSize(DivisionResult divisionResult) {
		int decimalSize = 0;
		String[] digits = divisionResult.getResult().split("\\D");
		for (int i = 1; i < digits.length; i++) {
			decimalSize += digits[i].length();
		}
		return decimalSize;
	}
}
