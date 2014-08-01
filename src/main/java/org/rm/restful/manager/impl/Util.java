package org.rm.restful.manager.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class Util {

	/**
	 * <p>
	 * Rest client handle sending and receiving req and responses
	 * </p>
	 * 
	 * @return Client for calling the Rest services
	 */
	public static Client getServiceHandle() {

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);		
		Client client = Client.create(clientConfig);
		return client;

	}
}
