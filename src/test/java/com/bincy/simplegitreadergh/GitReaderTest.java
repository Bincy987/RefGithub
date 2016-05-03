package com.bincy.simplegitreadergh;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kohsuke.github.GitHub;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner; 



@RunWith(PowerMockRunner.class)
@PrepareForTest(GitHub.class)
public class GitReaderTest {

	private static GitReader gitReaderObj = null;
	private GitHub gitHubMock;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gitReaderObj = new GitReader();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		gitReaderObj = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gitHubMock = EasyMock.createMock(GitHub.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link  com.bincy.simplegitreadergh.GitReader#connect(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testConnectWithValidArguments() throws IOException {

		mockStatic(GitHub.class);
		expect(GitHub.connectUsingPassword("username","password")).andReturn(gitHubMock);
		replayAll();
		gitReaderObj.connect("username", "password");
		verifyAll();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConnectWithInvalidArguments() throws IOException {

		mockStatic(GitHub.class);
		expect(GitHub.connectUsingPassword(null,null)).andReturn(gitHubMock);
		replayAll();
		gitReaderObj.connect(null,null);
		verifyAll();
	}

}
