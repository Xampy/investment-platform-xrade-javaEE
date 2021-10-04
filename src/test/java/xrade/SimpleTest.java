package xrade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class SimpleTest extends TestCase {
	
	@Test
	@DisplayName("Simple test")
	public void test(){
		assertEquals(2, 1 + 1);
	}

}
