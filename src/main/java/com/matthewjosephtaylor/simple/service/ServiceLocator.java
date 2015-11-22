package com.matthewjosephtaylor.simple.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * It's either spring DI or something like this.
 *
 * http://martinfowler.com/articles/injection.html#UsingAServiceLocator
 *
 * I prefer the service locator pattern as it is a bit less magicky than
 * spring annotation DI.
 */
public class ServiceLocator {

	public static ThreadLocal<Map<Class<?>, Object>> threadLocalServices = new ThreadLocal<Map<Class<?>, Object>>() {
		@Override
		protected Map<Class<?>, Object> initialValue() {
			return new ConcurrentHashMap<Class<?>, Object>();

		}
	};

	private static Map<Class<?>, Object> services = new ConcurrentHashMap<Class<?>, Object>();

	public static <T> void assemble(final Class<T> clazz, final Supplier<T> instantiator) {
		final T serviceObject = instantiator.get();
		register(clazz, serviceObject);
	}

	public static <T> T get(final Class<T> clazz) {
		@SuppressWarnings("unchecked")
		final T object = (T) services.get(clazz);
		if (object == null) {
			throw new RuntimeException("Unable to locate service for class: " + clazz.getCanonicalName());
		}
		return object;
	}

	public static <T> T getThreadLocal(final Class<T> clazz) {
		@SuppressWarnings("unchecked")
		final T object = (T) threadLocalServices.get().get(clazz);
		if (object == null) {
			throw new RuntimeException("Unable to locate service for class: " + clazz.getCanonicalName());
		}
		return object;
	}

	public static <T> void register(final Class<T> clazz, final T serviceImpl) {
		services.put(clazz, serviceImpl);
	}

	public static <T> void registerThreadLocal(final Class<T> clazz, final T serviceImpl) {
		threadLocalServices.get().put(clazz, serviceImpl);
	}

	public static void unregister(final Class<?> clazz) {
		services.remove(clazz);
	}

	public static void unregisterThreadLocal(final Class<?> clazz) {
		threadLocalServices.get().remove(clazz);
	}

	private ServiceLocator() {
	}
}
