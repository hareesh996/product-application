package com.hari.microservices.util.http;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hari.microservices.util.Util;
import com.hari.microservices.util.logger.Logger;

@Component
public class ServiceUtil {

	private static final Logger logger = Logger.getInstance(ServiceUtil.class);
	private final int serverPort;
	private String serviceAddress;

	public ServiceUtil(@Value("${server.port}") int servicePort) {
		this.serverPort = servicePort;
	}

	public String getServiceAddress() {
		if (Util.isEmpty(this.serviceAddress)) {
			this.serviceAddress = Util.concat(this.getHostName(), "/", this.getHostAddress(), ":", this.serverPort);
		}
		return this.serviceAddress;
	}

	private String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.debug(e, "Host Name is found");
			return "unknown-host";
		}
	}

	private String getHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error(e, "Host address is not found");
			return "unkown-host-address";
		}
	}

}
