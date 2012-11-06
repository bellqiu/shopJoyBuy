package com.spshop.web.support;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;

/**
 * Servlet implementation class URLHandlerServlet
 */
public class URLHandlerServlet extends ActionServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public URLHandlerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        if ("/".equals(arg0.getServletPath())) {
            arg0.getRequestDispatcher("/home/").forward(arg0, arg1);
        } else {
            super.service(arg0, arg1);
        }
    }
}
