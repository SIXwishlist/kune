package org.ourproject.rack.filters.rest;

import java.lang.reflect.Method;
import java.util.HashMap;

public class DefaultRESTMethodFinder implements RESTMethodFinder {
	private HashMap<Class<?>, RESTServiceDefinition> definitionCache;

	public DefaultRESTMethodFinder() {
		this.definitionCache = new HashMap<Class<?>, RESTServiceDefinition>();
	}
	
	public RESTMethod findMethod(String methodName, Parameters parameters, Class<?> serviceType) {
		RESTServiceDefinition serviceDefinition = getServiceDefinition(serviceType);
		Method[] serviceMethods = serviceDefinition.getMethods();
		for (Method method : serviceMethods) {
			if (method.getName().equals(methodName)) {
				REST methodAnnotation = method.getAnnotation(REST.class);
				if (checkParams(methodAnnotation, parameters)) {
					return new RESTMethod(method, methodAnnotation.params(), parameters, methodAnnotation.format());
				}
			}
		}
		return null;
	}

	private RESTServiceDefinition getServiceDefinition(Class<?> serviceType) {
		RESTServiceDefinition serviceDefinition = definitionCache.get(serviceType);
		if (serviceDefinition == null) {
			serviceDefinition = new RESTServiceDefinition(serviceType);
			definitionCache.put(serviceType, serviceDefinition);
		}
		return serviceDefinition;
	}

	private boolean checkParams(REST methodAnnotation, Parameters parameters) {
		for (String name : methodAnnotation.params()) {
			if (parameters.get(name) == null) {
				return false;
			}
		}
		return true;
	}
}
