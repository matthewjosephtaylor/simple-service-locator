package com.matthewjosephtaylor;

import org.junit.Assert;
import org.junit.Test;

import com.matthewjosephtaylor.simple.service.ServiceLocator;

public class ServiceLocatorTest {

	@Test
	public void testBasicFunctionality() {
		ServiceLocator.register(TestService.class, new TestServiceImpl());
		final TestService testService = ServiceLocator.get(TestService.class);
		final int sum = testService.add(2, 3);
		Assert.assertEquals("Unexpected result for service method invokation", 5, sum);
		ServiceLocator.unregister(TestService.class);
	}

	@Test(expected = RuntimeException.class)
	public void testBasicServiceUnregistration() {
		ServiceLocator.register(TestService.class, new TestServiceImpl());
		ServiceLocator.unregister(TestService.class);
		ServiceLocator.get(TestService.class);
	}

	@Test
	public void testThreadLocal() {
		ServiceLocator.registerThreadLocal(TestService.class, new TestServiceImpl());
		final TestService testService = ServiceLocator.getThreadLocal(TestService.class);
		final int sum = testService.add(2, 3);
		Assert.assertEquals("Unexpected result for service method invokation", 5, sum);
		ServiceLocator.unregisterThreadLocal(TestService.class);
	}

	@Test(expected = RuntimeException.class)
	public void testThreadLocalDoesntPolluteGlobal() {
		ServiceLocator.registerThreadLocal(TestService.class, new TestServiceImpl());
		ServiceLocator.get(TestService.class);
	}

	@Test(expected = RuntimeException.class)
	public void testThreadLocalServiceUnregistration() {
		ServiceLocator.registerThreadLocal(TestService.class, new TestServiceImpl());
		ServiceLocator.unregisterThreadLocal(TestService.class);
		ServiceLocator.getThreadLocal(TestService.class);
	}

}
