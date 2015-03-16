package co.hodler;

import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Proxy {

  public static void main(String[] args) throws Exception {
    Proxy p = new Proxy(8888);
  }

  public Proxy(int port) throws Exception {
    Server server = new Server();
    ServerConnector connector = new ServerConnector(server);
    connector.setPort(port);
    server.addConnector(connector);

    ConnectHandler proxy = new ConnectHandler();
    server.setHandler(proxy);

    ServletContextHandler context = new ServletContextHandler(proxy, "/", ServletContextHandler.SESSIONS);
    ServletHolder proxyServlet = new ServletHolder(ProxyServlet.class);
    context.addServlet(proxyServlet, "/*");

    server.start();
  }

}
