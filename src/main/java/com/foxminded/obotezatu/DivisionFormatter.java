package com.foxminded.obotezatu;

import java.util.List;
import java.util.ListIterator;

public class DivisionFormatter {

	public String format(DivisionResult divisionResult) {
		if (divisionResult.getDividend() == 0) {
			return "0";
		}
		StringBuilder formattedResult = new StringBuilder();
		ListIterator<Step> stepsIntegerIterator = divisionResult.getSteps().listIterator();
		formattedResult.append(formatHead(divisionResult, stepsIntegerIterator))
				.append(formatBody(divisionResult, stepsIntegerIterator));
		return formattedResult.toString();
	}

	private String formatHead(DivisionResult divisionResult, ListIterator<Step> stepsIntegerIterator) {
		int dividendLength = String.valueOf(divisionResult.getDividend()).length();
		StringBuilder formattedResult = new StringBuilder();
		Step currentStep = stepsIntegerIterator.next();
		while (currentStep.getDividerMultiple() == 0 && stepsIntegerIterator.hasNext()) {
			currentStep = stepsIntegerIterator.next();
		}
		int partialDividendLength = (int) Math.log10(currentStep.getPartialDividend()) + 1;
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
			formattedResult.append(String.format("%s%-" + (dividendLength - 1) + "d |--------%n", " ",
					currentStep.getDividerMultiple()));
		} else {
			formattedResult
					.append(String.format(" %-" + dividendLength + "d |--------%n", currentStep.getDividerMultiple()));
		}
		formattedResult.append(String.format(" %-" + dividendLength + "s | %s%n",
				countDashes(currentStep.getDividerMultiple()), formatResult(divisionResult.getSteps())));
		return formattedResult.toString();
	}

	private String formatBody(DivisionResult divisionResult, ListIterator<Step> stepsIntegerIterator) {
		StringBuilder formattedResult = new StringBuilder();
		StringBuilder indent = new StringBuilder(countIndents(stepsIntegerIterator));
		ListIterator<Step> stepsIterator = null;
		Step currentStep = null;
		while (stepsIntegerIterator.hasNext()) {
			currentStep = stepsIntegerIterator.next();
			stepsIterator = stepsIntegerIterator;
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
		if (currentStep != null) {
			formattedResult.append(String.format("%s%d", indent.toString(),
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

	private String formatResult(List<Step> steps) {
		StringBuilder result = new StringBuilder();
		String integerPart = "";
		String decimalPart = "";
		String periodPart = "";
		long integer = 0;
		long decimal = 0;
		long period = 0;
		for (int i = 0; i < steps.size(); i++) {
			if (steps.get(i).isIntegerPart) {
				integer = integer * 10 + steps.get(i).getDivideResult();
				integerPart = String.valueOf(integer);
			} else if (!steps.get(i).isPeriod) {
				decimal = decimal * 10 + steps.get(i).getDivideResult();
				decimalPart = String.valueOf(decimal);
			} else {
				period = period * 10 + steps.get(i).getDivideResult();
				periodPart = String.valueOf(period);
			}
		}
		if ("".equals(periodPart)) {
			if ("".equals(decimalPart)) {
				result.append(integerPart);
			} else {
				result.append(integerPart).append(".").append(decimalPart);
			}
		} else {
			result.append(integerPart).append(".").append(decimalPart).append("(").append(periodPart).append(")");
		}
		return result.toString();
	}
}