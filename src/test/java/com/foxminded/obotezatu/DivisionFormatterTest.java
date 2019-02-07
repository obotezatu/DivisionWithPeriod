package com.foxminded.obotezatu;

import static org.junit.Assert.assertEquals;
import static java.lang.System.lineSeparator;

import org.junit.Before;
import org.junit.Test;

public class DivisionFormatterTest {

	private Division division;
	private DivisionFormatter divisionFormatter;
	private StringBuilder expected;

	@Before
	public void setUp() {
		division = new Division();
		divisionFormatter = new DivisionFormatter();
		expected = new StringBuilder();
	}

	@Test
	public void testDivisionFormatterFormat() {
		expected.append("_78945 | 4").append(lineSeparator()).append(" 4     |--------").append(lineSeparator())
				.append(" -     | 19736.25").append(lineSeparator()).append("_38").append(lineSeparator()).append(" 36")
				.append(lineSeparator()).append(" --").append(lineSeparator()).append(" _29").append(lineSeparator())
				.append("  28").append(lineSeparator()).append("  --").append(lineSeparator()).append("  _14")
				.append(lineSeparator()).append("   12").append(lineSeparator()).append("   --")
				.append(lineSeparator()).append("   _25").append(lineSeparator()).append("    24")
				.append(lineSeparator()).append("    --").append(lineSeparator()).append("    _10").append(lineSeparator()).append("      8").append(lineSeparator()).append("     --")
				.append(lineSeparator()).append("     _20").append(lineSeparator()).append("      20").append(lineSeparator()).append("      --").append(lineSeparator()).append("        0");
		assertEquals(expected.toString(), divisionFormatter.format(division.divide(78945, 4)));
	}

	@Test
	public void testThousandsDivisionFormatterFormat() {
		expected.append("_10000 | 10").append(lineSeparator()).append(" 10    |--------").append(lineSeparator())
				.append(" --    | 1000.0").append(lineSeparator()).append("   0");
		assertEquals(expected.toString(), divisionFormatter.format(division.divide(10000, 10)));
	}

	@Test
	public void testZeroDivisionFormatterFormat() {
		String expectedResult = "0";
		assertEquals(expectedResult, divisionFormatter.format(division.divide(0, 4)));
	}
}
