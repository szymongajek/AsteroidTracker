package com.sz.asteroid;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;


public class FeedProcessorTest {

	@Test
	public void testCalcStartDateForMissing() {
		
		assertEquals( LocalDate.of(2017, 5, 2), FeedProcessor.calcStartDateForMissing(LocalDate.of(2017, 5, 3),LocalDate.of(2017, 5, 1)  ));
		assertEquals( LocalDate.of(2017, 4, 27), FeedProcessor.calcStartDateForMissing(LocalDate.of(2017, 5, 3),LocalDate.of(2017, 4, 26)  ));
		assertEquals( LocalDate.of(2017, 4, 26 ), FeedProcessor.calcStartDateForMissing(LocalDate.of(2017, 5, 3),LocalDate.of(2017, 4, 25)  ));
		assertEquals( LocalDate.of(2017, 4, 26 ), FeedProcessor.calcStartDateForMissing(LocalDate.of(2017, 5, 3),LocalDate.of(2017, 4, 1)  ));
		
		
	}

}
