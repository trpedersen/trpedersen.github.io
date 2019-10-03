package <package-name>.tests;
import <package-name>.<test-classes>;

import junit.framework.*;
import java.util.Vector;
import junit.extensions.*;

public class <test-class>Test extends TestCase 
{
   /**
     * the person object to test
     */
    protected <test-class> testObject;
    
    public <test-class>Test(String name)
    {
        super(name);
    }

    public static void main(String [] args)
    {
        junit.textui.TestRunner.run(suite());
    }

    protected void setUP() 
    {
        testObject = new <test-class>();
    }

    public static Test suite() 
    {
        return new TestSuite(<test-class>Test.class);
    }
    
    //sample test method
    public void testMethod() 
    {
        testObject = new <test-class>();
        // invalid member no
        //int aMemberStatus=5;
       // memberTest.setStatus(aMemberStatus);
       // assert("test setStatus failed",memberTest.getStatus()==aMemberStatus);
    }
}

