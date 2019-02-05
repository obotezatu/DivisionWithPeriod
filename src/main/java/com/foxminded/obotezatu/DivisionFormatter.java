package com.foxminded.obotezatu;

import java.util.ListIterator;

public class DivisionFormatter {

	public String format(DivisionResult divisionResult) {
		if (divisionResult.getDividend() == 0) {
			return "0";
		}
		StringBuilder formattedResult = new StringBuilder();
		ListIterator<Step> stepsIterator = divisionResult.getSteps().listIterator();
		ListIterator<Step> stepsDecimalIterator = divisionResult.getDecimalStep().listIterator();
		formattedResult.append(formatHead(divisionResult, stepsIterator,stepsDecimalIterator))
				.append(formatBody(stepsIterator, stepsDecimalIterator));
		return formattedResult.toString();
	}

	private String formatHead(DivisionResult divisionResult, ListIterator<Step> stepsIterator, ListIterator<Step> stepsDecimalIterator) {
		int dividendLength = String.valueOf(divisionResult.getDividend()).length();
		StringBuilder formattedResult = new StringBuilder();
		Step currentStep = stepsIterator.next();
		while (currentStep.getDividerMultiple() == 0 && stepsIterator.hasNext()) {
			currentStep = stepsIterator.next();
		}
		int partialDividendLength = (int)Math.log10(currentStep.getPartialDividend()) + 1;
		int dividerMultipleLength = currentStep.getDividerMultiple() != 0? (int)Math.log10(currentStep.getDividerMultiple())+ 1 : 1;
		if (currentStep.getDividerMultiple() == 0) {
			currentStep = stepsDecimalIterator.next();
			while (currentStep.getDividerMultiple() == 0 && stepsDecimalIterator.hasNext()) {
				currentStep = stepsDecimalIterator.next();
			}
		}
			dividerMultipleLength = currentStep.getDividerMultiple() != 0? (int)Math.log10(currentStep.getDividerMultiple())+ 1 : 1;
			if (dividendLength < dividerMultipleLength) {
				formattedResult.append(String.format("_%-" + dividerMultipleLength + "d | %d%n",
						divisionResult.getDividend(), divisionResult.getDivider()));
			}
			else {
				formattedResult.append(String.format("_%" + dividendLength + "d | %d%n", divisionResult.getDividend(),
						divisionResult.getDivider()));
			}
			if (partialDividendLength > dividerMultipleLength) {
				formattedResult.append(String.format(" %s%-" + (dividendLength - 1) + "d |--------%n", " ",
						currentStep.getDividerMultiple()));
			} else {
				formattedResult.append(
						String.format(" %-" + dividendLength + "d |--------%n", currentStep.getDividerMultiple()));
			}
			formattedResult.append(String.format(" %-" + dividendLength + "s | %s%n",
					countDashes(currentStep.getPartialDividend()), divisionResult.getResult()));
		return formattedResult.toString();
	}

	private String formatBody(ListIterator<Step> stepsIterator, ListIterator<Step> stepsDecimalIterator) {
		StringBuilder formattedResult = new StringBuilder();
		StringBuilder indent = new StringBuilder(countIndents(stepsIterator));
		Step currentStep = null;
		while (stepsIterator.hasNext()) {
			currentStep = stepsIterator.next();
			if (currentStep.getPartialDividend() != 0 && currentStep.getDividerMultiple() != 0) {
				formattedResult.append(String.format("%s_%s%n", indent.toString(), currentStep.getPartialDividend()));
				if ((int)(Math.log10(currentStep.getPartialDividend()) + 1) > ((int)Math.log10(currentStep.getDividerMultiple())+ 1)) {
					formattedResult.append(
							String.format("%s%s% d%n", indent.toString(), " ", currentStep.getDividerMultiple()));
				} else {
					formattedResult
							.append(String.format("%s% d%n", indent.toString(), currentStep.getDividerMultiple()));
				}
				formattedResult.append(
						String.format(" %s%s%n", indent.toString(), countDashes(currentStep.getPartialDividend())));
				indent.append(countIndents(stepsIterator));
			} else {
				indent.append(" ");
			}
		}

		//int i = DivisionResult.getDecimalDigits();
		//currentStep = stepsDecimalIterator.next();
		while (stepsDecimalIterator.hasNext() ) { //&& i>0
			currentStep = stepsDecimalIterator.next();
			if (currentStep.getPartialDividend() != 0 && currentStep.getDividerMultiple() != 0) {
				formattedResult.append(String.format("%s_%s%n", indent.toString(), currentStep.getPartialDividend()));
				if ((int)(Math.log10(currentStep.getPartialDividend()) + 1) > ((int)Math.log10(currentStep.getDividerMultiple())+ 1)) {
					formattedResult.append(
							String.format("%s%s% d%n", indent.toString(), " ", currentStep.getDividerMultiple()));
				} else {
					formattedResult
							.append(String.format("%s% d%n", indent.toString(), currentStep.getDividerMultiple()));
				}
				formattedResult.append(
						String.format(" %s%s%n", indent.toString(), countDashes(currentStep.getPartialDividend())));
				indent.append(countIndents(stepsDecimalIterator));
			} else {
				indent.append(" ");
			}
			//i--;
		}
		formattedResult.append(
				String.format(" %s%d", indent, (currentStep.getPartialDividend() - currentStep.getDividerMultiple())));
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

	private String countIndents(ListIterator<Step> stepsIterator) {
		stepsIterator.previous();
		Step currentStep = stepsIterator.next();
		long indentCount = countDigits(currentStep);
		StringBuilder indent = new StringBuilder();
		for (int i = 0; i < indentCount; i++) {
			indent.append(" ");
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
}