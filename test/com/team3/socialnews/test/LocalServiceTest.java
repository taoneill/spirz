package com.team3.socialnews.test;

import java.io.File;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Base TestCase class for tests which require mocking
 * of AppEngine.
 * see http://code.google.com/appengine/docs/java/tools/localunittesting.html
 */
public class LocalServiceTest {
	private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(), new LocalMemcacheServiceTestConfig());
	
	@Before
    public void setUp() throws Exception {
		helper.setUp();
    }

	@After
    public void tearDown() throws Exception {
		helper.tearDown();
    }
}
