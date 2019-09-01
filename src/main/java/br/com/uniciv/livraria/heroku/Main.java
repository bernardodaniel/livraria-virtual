package br.com.uniciv.livraria.heroku;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * This class launches the web application in an embedded Jetty container. This is the entry point to your application. The Java
 * command that is used for launching should fire this main method.
 */
public class Main {

    public static void main(String[] args) throws Exception{
    	File keystoreFile = new File("server.keystore");
    	
    	Server server = new Server();
    	
    	HttpConfiguration httpConfig = new HttpConfiguration();
    	httpConfig.setSecureScheme("https");
    	httpConfig.setSecurePort(8443);
    	
    	ServerConnector http = new ServerConnector(server,
    			new HttpConnectionFactory(httpConfig));
    	http.setPort(8080);
    	
    	SslContextFactory sslContextFactory = new SslContextFactory();
    	sslContextFactory.setKeyStorePath(keystoreFile.getAbsolutePath());
    	sslContextFactory.setKeyStorePassword("livraria");
    	sslContextFactory.setKeyManagerPassword("livraria");
    	
    	HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);

    	ServerConnector https = new ServerConnector(server,
    			new SslConnectionFactory(sslContextFactory, 
    					HttpVersion.HTTP_1_1.asString()),
    			new HttpConnectionFactory(httpsConfig));
    	https.setPort(8443);
    	
    	server.setConnectors(new ServerConnector[] {http, https});

        final WebAppContext root = new WebAppContext();
        root.setContextPath("/livraria-virtual");
        root.setParentLoaderPriority(true);

        final String webappDirLocation = "src/main/webapp/";
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);
        
    	HashLoginService loginService = new HashLoginService("MyRealm");
        loginService.setConfig("myrealm.properties");
        
    	server.addBean(loginService);
    	
    	Constraint constraint = new Constraint();
    	constraint.setName("auth");
    	constraint.setAuthenticate(true);
    	constraint.setRoles(new String[] {"user", "admin"});
    	
    	ConstraintMapping mapping = new ConstraintMapping();
    	mapping.setPathSpec("/*");
    	mapping.setConstraint(constraint);

    	ConstraintSecurityHandler security = new ConstraintSecurityHandler();
    	server.setHandler(security);
    	
    	security.setConstraintMappings(Arrays.asList(mapping));
    	security.setAuthenticator(new BasicAuthenticator());
    	security.setLoginService(loginService);
    	
    	security.setHandler(root);
        
//        server.addBean(loginService);

//        server.setHandler(root);
        server.start();
        server.join();
    }
}

