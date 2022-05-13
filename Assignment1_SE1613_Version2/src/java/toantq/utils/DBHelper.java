/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class DBHelper implements Serializable{
    public static Connection makeConnection() 
            throws NamingException, SQLException {
        //1. get current context
        Context context = new InitialContext();
        //2. get server context
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        //3. user datasource
        DataSource ds = (DataSource) tomcatContext.lookup("DBCONNECTION");
        //4. open connection 
        Connection con = ds.getConnection();
        
        return con;
    }
    
    public static void getSiteMaps(ServletContext context) throws IOException {
        //1. get siteMaps file
        String siteMapsFile = context.getInitParameter("SITE_MAPS_FILE_PATH");
        //2. load properties from context and siteMapsFile to getResourceAsStream
        InputStream is = null;
        is = context.getResourceAsStream(siteMapsFile);
        Properties properties = new Properties();
        properties.load(is);
        
        //3. tạo attribute trong contextScope
        context.setAttribute("SITE_MAPS", properties);
    }
}
