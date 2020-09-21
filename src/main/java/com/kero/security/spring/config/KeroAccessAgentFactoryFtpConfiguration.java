package com.kero.security.spring.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.kero.security.core.agent.KeroAccessAgentFactory;
import com.kero.security.core.agent.configurator.AccessAgentFtpResourceConfigurator;

@Configuration
public class KeroAccessAgentFactoryFtpConfiguration implements KeroAccessAgentFactorySpringConfiguration {
	
	private static Logger LOGGER = LoggerFactory.getLogger("Kero-Security-Ftp-Spring");
	
	@Value("${kero.security.lang.resource.ftp.server:#{null}}")
	private String server;
	
	@Value("${kero.security.lang.resource.ftp.port:21}")
	private int port;
	
	@Value("${kero.security.lang.resource.ftp.username:#{null}}")
	private String username;
	
	@Value("${kero.security.lang.resource.ftp.pass}")
	private String pass;
	
	@Value("${kero.security.lang.resource.ftp.path:/}")
	private String path;
	
	@Value("${kero.security.lang.resource.ftp.suffixes:.k-s,.ks}")
	private String[] rawSuffixes;
	
	@Value("${kero.security.lang.resource.cache.enabled:true}")
	private boolean resourceCacheEnabled;
	
	@Value("${kero.security.lang.provider.cache.enabled:true}")
	private boolean providerCacheEnabled;
	
	@Override
	public void configure(KeroAccessAgentFactory factory) {
		
		if(this.server == null
		&& this.username == null) return;
		
		if(this.server == null) throw new RuntimeException("kero.security.lang.resource.ftp.server not specified!");
		if(this.username == null) throw new RuntimeException("kero.security.lang.resource.ftp.username not specified!");
		
		try {

			Set<String> suffixes = new HashSet<>(Arrays.asList(this.rawSuffixes));
			
			AccessAgentFtpResourceConfigurator conf =
				new AccessAgentFtpResourceConfigurator(server, port, username, pass, path, resourceCacheEnabled, providerCacheEnabled, suffixes);
		
			factory.addConfigurator(conf);
			
			StringBuilder debugLog = new StringBuilder();
				debugLog.append("Successful add AccessAgentFtpResourceConfigurator:");
				debugLog.append("\n  server: "+this.server);
				debugLog.append("\n  port: "+this.port);
				debugLog.append("\n  username: "+this.username);
				debugLog.append("\n  pass: "+this.pass);
				debugLog.append("\n  path: "+this.path);
				
			LOGGER.debug(debugLog.toString());
		}
		catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
}