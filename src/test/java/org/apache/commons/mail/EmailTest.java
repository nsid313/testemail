package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.mail.internet.MimeMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EmailTest {

	private static final String[] t_emailS = {"ab@bc.com", "ab@c.org", 
			"hjdfh@fjdfh.com"};
	
	private static final String t_email = "ab@bc.com";

	
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception {
		
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception{
		
	}
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	

	
	/*
	 * Test addBcc(String...) by comparing the size 
	 */
	@Test
	public void testaddBcc_2() throws Exception{
		
		email.addBcc(t_emailS);
		assertEquals(3, email.getBccAddresses().size());
	}
	
	
	/*
	 * Test addCc(String) by Comparing the string value from getCcAddresses with 
	 * the expected email value
	 */
	@Test
	public void testaddCc() throws Exception{
		
		email.addCc(t_email);
		String expected = "ab@bc.com";
		assertEquals(expected, email.getCcAddresses().get(0).toString());
	}
	
	

	private static String t_header = "testheader";
	
	
	/*
	 * Test addHeader(String name, String value) by Comparing the value of the 
	 * header with the expected result
	 */
	@Test
	public void testaddHeader() throws Exception{
		email.addHeader(t_header, "testheader");
		String expected = "testheader";
		assertEquals(expected, email.headers.get("testheader"));
	}
	
	/*
	 * Test addHeader(String name, String value) by checking for exception when 
	 * the name of the header is empty
	 */ 
	@Test
	public void testaddHeaderKey() throws Exception{
		
		thrown.expectMessage("name can not be null or empty");
		email.addHeader("", "testheader");
		
	}
	
	
	/*
	 * Test addHeader(String name, String value) by checking for exception when the 
	 * value of the header is empty
	 */
	@Test
	public void testaddHeaderValue() throws Exception{
		thrown.expectMessage("value can not be null or empty");
		email.addHeader(t_header, "");
		
	}
	
	
	
	
	/*
	 * Test addReplyTo(String email, String name) by comparing the sizes 
	 * of expected and actual result
	 */
	@Test
	public void testaddReplyTo() throws Exception{
		
		email.addReplyTo(t_email, "abcd");
		assertEquals(1, email.getReplyToAddresses().size());
	}
	
	
	
	/*
	 * Test buildMimeMessage() by comparing the value of getSubject with an expected value
	 */
	@Test
	public void testbuildMimeMessage_1() throws Exception{
		email.setHostName("host");
		email.setFrom(t_email);
		email.addReplyTo(t_email, "abcd");
		email.addTo("ac@d.com");
		email.addBcc("bj@g.com");
		email.addCc("ab@c.org");
		email.addHeader("header", "testheader");
		email.setSubject("subject");

		email.buildMimeMessage();
		MimeMessage message = email.getMimeMessage();
		message.saveChanges();
		
		assertEquals("subject", message.getSubject());
	}
	
	
	
	/* 
	 * Test buildMimeMessage() by checking for exception when no From address is provided.
	 */
	@Test
	public void testbuildMimeMessage_2() throws Exception{
		
		email.setHostName("host");
		email.addHeader("header", "testheader");
		email.setSubject("subject");
		email.addTo("ac@d.com");
		email.addCc("ab@c.org");


		thrown.expectMessage("From address required");
		email.buildMimeMessage();
	}
	
	
	/*
	 * Test buildMimeMessage() by checking for exception when no receiver address is provided
	 */
	@Test
	public void testbuildMimeMessage_3() throws Exception{
		
		email.setHostName("host");
		email.setFrom(t_email);
		email.addHeader("header", "testheader");
		email.setSubject("subject");

		thrown.expectMessage("At least one receiver address required");
		email.buildMimeMessage();
	}
	
	
	/*
	 * Test buildMimeMessage() by checking for exception when the message already exists. 
	 */
	@Test
	public void testbuildMimeMessage_4() throws Exception{
		email.setHostName("host");
		email.setFrom(t_email);
		email.addTo("ac@d.com");
		email.addBcc("bj@g.com");
		email.addCc("ab@c.org");
		email.content = "Hello";

		email.buildMimeMessage();
		
		
		thrown.expectMessage("The MimeMessage is already built.");
		email.buildMimeMessage();

	
	}
      
	
	/*
	 * Test getHostName() by comparing the value of getHostName with the expected result when hostName is not null
	 */
	@Test
	public void testgetHostName_1() throws Exception{
		
		email.setHostName("host");
		assertEquals("host", email.getHostName());
	}
	
	
	/*
	 * Test getHostName() by checking when the hostName is null
	 */
	@Test
	public void testgetHostName_2() throws Exception{
		
		String expected = null;
		assertEquals(expected, email.getHostName());
	}
	
	
	/*
	 * Test getMailSession() by checking for exception when valid hostName is not provided
	 */

	@Test
	public void testgetMailSession() throws Exception{
		
		
	    thrown.expectMessage("Cannot find valid hostname for mail session");
	    email.getMailSession();
	   
	}
	
	/*
	 * Test getSentDate() by checking if getSentDate returns the current sent date
	 */
	@Test
	public void testgetSentDate() throws Exception{
		
		Date test_date = new Date();
	
		email.setSentDate(test_date);
		
		assertEquals(test_date, email.getSentDate());

	}
	
	
	/*
	 * Test getSocketConnectionTimeout() by checking if the method returns 
	 * the value of socketConnectionTimeout
	 */
	@Test
	public void testgetSocketConnectionTimeout() throws Exception{
		
		int actual = email.getSocketConnectionTimeout();
		int expected = 	email.socketConnectionTimeout;

		assertEquals(expected, actual);
	}
	
	
	/*
	 * Test setFrom() by comparing the value of the From address (string) 
	 * with expected value
	 */
	@Test
	public void testsetFrom() throws Exception{
		
		email.setFrom(t_email);
		assertEquals("ab@bc.com",email.getFromAddress().toString());
	}
	
	
	
}
