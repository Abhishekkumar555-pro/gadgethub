package in.gadgethub.listener;

import in.gadgethub.ulility.DBUtil;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DBConnectionListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       
            ServletContext ctxt = sce.getServletContext();
            String dburl = ctxt.getInitParameter("url");
            String username = ctxt.getInitParameter("username");
            String password = ctxt.getInitParameter("password");
            DBUtil.openConnection(dburl, username, password);       
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBUtil.closeConnection();

    }

}
