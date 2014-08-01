package org.rm.restful.manager.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.rm.restful.manager.ISampleManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("sampleManager")
public class SampleManager implements ISampleManager {

	/**
	 * Logger for the class
	 */
	private static final Logger logger = Logger.getLogger(SampleManager.class);

	/**
	 * @throws Exception 
	 * @return
	 * @throws
	 */
	@Path("/getObjectsFromServer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public String getObjectsFromServer() throws Exception {
		logger.info("Inside Method.. ");
		Object request = "";
		Object response = "";
		try {
			response = restServiceCall(request,
					ApplicationConstants.CHANGE_RECORDS_LIST,
					MediaType.APPLICATION_JSON_TYPE);
		} catch (Exception exception) {
			throw exception;
		}
		if (response.equals("success")) {
			logger.info("success.. ");
			return response.toString();
		} else {
			logger.info("response failure message:" + response.toString());
				return null;
		}
	
	}

	/**
	 * To invoke restful service call to server
	 * 
	 * @param request
	 * @param serviceURL
	 * @param mediaType
	 * @return response
	 * @throws Exception
	 */
	private String restServiceCall(Object request, String serviceURL,
			MediaType mediaType) throws Exception {
		Object response = null;
		try {
			// Getting the client handle
			Client client = Util.getServiceHandle();
			// Get the service url
			String rootUrl = ApplicationConstants.SERVER_URL_KEY;
			logger.debug("serverUrl url:" + rootUrl);
			// Get the agent
			logger.debug("service url:" + serviceURL);
			if (StringUtils.isNotBlank(rootUrl)
					&& StringUtils.isNotBlank(serviceURL)) {
				WebResource webResource = client.resource(rootUrl + serviceURL);
				ClientResponse clientResponse = webResource.type(mediaType)
						.accept(mediaType).post(ClientResponse.class, request);
				logger.debug("response status is : "
						+ clientResponse.getStatus());
				if (clientResponse.getStatus() == 200) {
					response = clientResponse.getEntity(Object.class);
					logger.info("Response data:" + response);
				} else {
					logger.info("Request failed with status : "
							+ clientResponse.getStatus());

				}
			} else {
				logger.error("Missing Server Url");
			}
		} catch (ClientHandlerException clientHandlerException) {
			logger.error("Could not connect to :" + clientHandlerException);
			throw new Exception(clientHandlerException);
		}
		return response.toString();
	}
}
