/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.listener;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import toantq.utils.DBHelper;

/**
 * Web application lifecycle listener.
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class MyAppServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //1. get servletContext
        ServletContext context = sce.getServletContext();
        //2. getSiteMaps
        try {
            DBHelper.getSiteMaps(context);
        } catch (IOException ex) {
            context.log("IO Exception occours in process "
                    + "at MyAppServletListener", ex.getCause());
        } 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.removeAttribute("SITE_MAPS");
    }
}
