# Matt's Simple Service Locator

## Motivation

After my blog post [lamenting the dearth of a Java Simple Service Locators](http://www.matthewjosephtaylor.com/post/131898779419/no-simple-service-locator-standardframework-for), I wrote my own.

I've been using it for a couple of projects and think it is a good enough 
solution to share.


## Basic Usage


```Java

		import com.matthewjosephtaylor.simple.service.ServiceLocator;
		
		...

		ServiceLocator.register(TestService.class, new TestServiceImpl());
		TestService testService = ServiceLocator.get(TestService.class);
		int sum = testService.add(2, 3);
		
		...

		ServiceLocator.unregister(TestService.class);
```

## ThreadLocal Usage


At times it is interesting to have a service that is bound to a thread instead
of being globally available.

```Java

		import com.matthewjosephtaylor.simple.service.ServiceLocator;
		
		...	
		
		ServiceLocator.registerThreadLocal(TestService.class, new TestServiceImpl());
		final TestService testService = ServiceLocator.getThreadLocal(TestService.class);
		final int sum = testService.add(2, 3);
		
		...	
		
		ServiceLocator.unregisterThreadLocal(TestService.class);

```


## License
* [MIT License](http://www.opensource.org/licenses/mit-license.php)