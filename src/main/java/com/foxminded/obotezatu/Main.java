package com.foxminded.obotezatu;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		long dividend;
		long divider;
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Input dividend: ");
			dividend = scanner.nextLong();
			System.out.println("Input divider: ");
			divider = scanner.nextLong();
		}
		System.out.println("----------------------------------");
		Division division = new Division();
		DivisionFormatter formatter = new DivisionFormatter();
		System.out.println(formatter.format(division.divide(dividend, divider)));
	}
}
