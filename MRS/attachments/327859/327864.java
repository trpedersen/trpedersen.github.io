package example2;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.*;
import java.rmi.*;

import junit.framework.*;

public class TelephoneNumberTests extends TestCase {
    private TelephoneNumberHome home;

    public TelephoneNumberTests(String testname) {
	super(testname);
    }

    public static TestSuite suite() {
	return new TestSuite(TelephoneNumberTests.class);
    }

    public void setUp() throws Exception {
	Context initial = new InitialContext();
	Object objRef = initial.lookup("TelephoneNumber");
	
	home = (TelephoneNumberHome)PortableRemoteObject.narrow(objRef, TelephoneNumberHome.class);
    }

    protected TelephoneNumber createTelephoneNumber(String areaCode, String prefix, String number, String extension) throws RemoteException, CreateException {
	return home.create(areaCode, prefix, number, extension);
    }

    public void testSimpleStringFormatting() throws Exception {
	// Build a complete phone number
	TelephoneNumber number = createTelephoneNumber("612", "630", "1063", "1623");
	assertEquals("Bad string", "(612) 630-1063 x1623", number.formatNumber());
    }

    public void testNullAreaCode() throws Exception {
	// Build a phone number without area code
	TelephoneNumber number = createTelephoneNumber(null, "630", "1063", "1623");
	assertEquals("Bad string", "630-1063 x1623", number.formatNumber());
    }

    public void testNullExtension() throws Exception {
	// Build a phone number without an extension
	TelephoneNumber number = createTelephoneNumber("612", "630", "1063", null);
	assertEquals("Bad string", "(612) 630-1063", number.formatNumber());
    }

    public void testNullAreaCodeAndExtension() throws Exception {
	// Build a phone number without area code or extension
	TelephoneNumber number = createTelephoneNumber(null, "630", "1063", null);
	assertEquals("Bad string", "630-1063", number.formatNumber());
    }

    public void testNullExchange() throws Exception {
	// Build a phone number without exchange
	TelephoneNumber number = createTelephoneNumber("612", null, "1063", "1623");
	try {
	    number.formatNumber();
	    assert("Should have thrown a NullPointerException", false);
	} catch(NullPointerException npe) {
	    // expected behavior
	}
    }
}
