package com.bfd.es;

import org.junit.*;

/**
 * Created by BFD-483 on 2017/6/6.
 */
public class TestUnitTestSpec {
    //before each test
    @Before
    public void before(){

    }
    //after each test
    @After
    public void after(){

    }

    //run once only
    @BeforeClass
    public void beforeClass(){

    }

    //run once only
    @AfterClass
    public void afterClass(){

    }

    @Test
    public void test(){

    }

    @Test(timeout=100)
    public void testWithTimeout(){

    }

    @Ignore("i have been ignored")
    public void testIgnored(){

    }

}
