/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toantq.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import toantq.utils.MyApplicationConstants;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String button = request.getParameter("btnAction");
        ServletContext context = this.getServletContext();
        Properties properties = (Properties) context.getAttribute("SITE_MAPS");
        String url="";
        if (button == null) {
                Enumeration param =  request.getParameterNames();
                boolean next = true;
                while (param.hasMoreElements()&& next) {
                    String paramName = (String) param.nextElement();
                    if (paramName.startsWith("btnAction_")) {
                        button = request.getParameter(paramName);
                        next = false;
                    }
                }
            }
        try {
            if ("Remove item".equals(button)) {
                url = properties.getProperty(
                        MyApplicationConstants.ViewCartFeatures.REMOVE_ITEM_FROM_CART_CONTROLER);
            } else if ("Check out".equals(button)) {
                url = properties.getProperty(
                        MyApplicationConstants.ViewCartFeatures.CHECK_OUT_CONTROLLER);
            } else if ("Update Item's Quantity".equals(button)) {
                url = properties.getProperty(
                        MyApplicationConstants.ViewCartFeatures.UPDATE_ITEM_QUANTITY_CONTROLLER);
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
