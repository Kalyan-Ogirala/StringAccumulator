package service;

import org.junit.*;
import java.util.Optional;
import service.CalculatorService;

public class CalculatorServiceTest {

    private CalculatorService service = CalculatorService.getCalculatorServiceInstance();

    @Test
    public void addTest_Empty_String(){
        Assert.assertEquals(Optional.of(0), service.add(""));
    }

    @Test
    public void addTest_String_With_Numbers(){
        Assert.assertEquals(Optional.of(10), service.add("1,2,3,4"));
    }

    @Test
    public void addTest_Invalid_String(){
        Assert.assertFalse(service.add(",4,1,\\n").isPresent());
    }

    @Test
    public void addTest_String_With_NewLine(){
        Assert.assertEquals(Optional.of(6), service.add("1\\n2,3"));
    }
    
    @Test(expected = RuntimeException.class)
    public void addTest_String_With_Negative_Numbers(){
        service.add("1,-2,-3");
    }    
    
    @Test
    public void addTest_String_With_Negative_Numbers_Message(){
    	try {
    	  service.add("1,-2,-3");
    	}
    	catch(RuntimeException ex)
    	{
    	  Assert.assertEquals("negatives not allowed: [-2, -3]", ex.getMessage());
    	}    	
    }
    
    @Test
    public void addTest_String_With_Numbers_MoreThan_1000(){
        Assert.assertEquals(Optional.of(112), service.add("//!|@|*\\n10,2000@1!100*1"));
    }
    
    @Test
    public void addTest_String_With_Numbers_around_1000_Boundary(){
        Assert.assertEquals(Optional.of(1111), service.add("//!|@|*\\n10,2000@1000!100*1"));
    }

    @Test
    public void addTest_String_With_Delimiters_Length_1(){
        Assert.assertEquals(Optional.of(6), service.add("//*|%\\n1*2%3"));
    }

    @Test
    public void addTest_String_With_Delimiters_Length_MoreThan_1(){
        Assert.assertEquals(Optional.of(20), service.add("//kalyan|%\\n5kalyan6%9"));
    }
}
