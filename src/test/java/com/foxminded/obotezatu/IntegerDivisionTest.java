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
		String actualDivisionResult = division.divide(78945, 4).getResult();
		assertEquals("19736.25", actualDivisionResult);
	}
	
	@Test
	public void testThousandsDivision() {
		String actualDivisionResult = division.divide(10000, 10).getResult();
		assertEquals("1000.0", actualDivisionResult);
	}
	
	@Test
	public void testSmallToBigDivision() {
		String actualDivisionResult = division.divide(123, 456).getResult();
		assertEquals("0.269736842105", actualDivisionResult);
	}
	
	@Test
	public void testRepeatedWithoutPeriod() {
		String actualDivisionResult = division.divide(1236, 125).getResult();
		assertEquals("9.888", actualDivisionResult);
	}
	
	@Test
	public void testRepeatedWithPeriod() {
		String actualDivisionResult = division.divide(7, 12).getResult();
		assertEquals("0.58(3)", actualDivisionResult);
		actualDivisionResult = division.divide(1000, 3).getResult();
		assertEquals("333.(3)", actualDivisionResult);
	}
}
