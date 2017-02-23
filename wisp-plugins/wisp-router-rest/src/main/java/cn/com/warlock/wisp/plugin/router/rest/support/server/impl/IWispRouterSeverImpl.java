package cn.com.warlock.wisp.plugin.router.rest.support.server.impl;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.warlock.wisp.core.exception.WispRouterException;
import cn.com.warlock.wisp.plugin.router.rest.WispRouterRest;
import cn.com.warlock.wisp.plugin.router.rest.filter.RequestFilter;
import cn.com.warlock.wisp.plugin.router.rest.handler.EntryPointHandler;
import cn.com.warlock.wisp.plugin.router.rest.support.server.IWispRouterServer;

public class IWispRouterSeverImpl implements IWispRouterServer {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WispRouterRest.class);

    private Server jettyServer = null;

    @Override
    public void start(int port) throws WispRouterException {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addFilter(RequestFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        jettyServer = new Server(port);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                EntryPointHandler.class.getCanonicalName());

        try {

            jettyServer.start();

            LOGGER.info("{} {} start ok", "http://0.0.0.0:" + port, WispRouterRest.class.getName());

            jettyServer.join();

        } catch (InterruptedException e) {

            LOGGER.warn(e.toString());

        } catch (Exception e) {

            throw new WispRouterException(e);

        } finally {

            jettyServer.destroy();
        }
    }

    @Override
    public void stop() throws WispRouterException {

        if (jettyServer != null) {

            try {

                jettyServer.stop();

            } catch (Exception e) {

                LOGGER.warn(e.toString());
            }
        }
    }

}
