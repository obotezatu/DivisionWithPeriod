package com.foxminded.obotezatu;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class IntegerDivisionTest {

	private Division division;

	@Before
	public void setUp() {
		division = new Division();
	}

	@Test(expected = ArithmeticException.class)
	public void testDivideByZero() {
		division.divide(78945, 0);
	}

	@Test
	public void testNumberDivision() {
		double actualDivisionResult = division.divide(78945, 4).getResult();
		assertEquals(19736.25, actualDivisionResult, 0.0000000001);
	}

	@Test
	public void testThousandsDivision() {
		double actualDivisionResult = division.divide(10000, 10).getResult();
		assertEquals(1000, actualDivisionResult, 0.0000000001);
	}

	@Test
	public void testSmallToBigDivision() {
		double actualDivisionResult = division.divide(123, 456).getResult();
		assertEquals(0.2697368421, actualDivisionResult, 0.0000000001);
	}

	@Test
	public void testRepeatedDecimalsWithoutPeriod() {
		double actualDivisionResult = division.divide(1236, 125).getResult();
		assertEquals(9.888, actualDivisionResult, 0.0000000001);
	}

	@Test
	public void testRepeatedDecimalsWithPeriod() {
		double actualDivisionResult = division.divide(7, 12).getResult();
		assertEquals(0.583, actualDivisionResult, 0.0000000001);
		actualDivisionResult = division.divide(1000, 3).getResult();
		assertEquals(333.3, actualDivisionResult, 0.0000000001);
	}

}
