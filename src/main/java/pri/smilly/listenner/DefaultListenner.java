package pri.smilly.listenner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DefaultListenner implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {

    }
}
