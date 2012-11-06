package com.spshop.admin.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.spshop.admin.shared.LoginInfo;
import com.spshop.model.Country;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CountryService;
import com.spshop.service.intf.SiteService;
import com.spshop.service.intf.UserService;
import com.spshop.utils.Constants;

public class LoginService extends HttpServlet {
    private static Logger logger = Logger.getLogger(LoginService.class);
    private final String SUCCESS = "ShopAdmin.jsp";
    private final String SUCCESS_DEBUG = "ShopAdmin.jsp?gwt.codesvr=127.0.0.1:9997";
    private final String FAILURE = "Admin.jsp";

    private final static Properties users = new Properties();

    static {
        try {
            users.load(LoginService.class.getResourceAsStream("/adminUser.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
	 *
	 */
    private static final long serialVersionUID = 5106197319286040491L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        String debug = req.getParameter("debug");
        if (null != username) {
            if (users.containsKey(username) && users.get(username).equals(pwd)) {
                LoginInfo info = new LoginInfo();
                info.setCountryMap(populateCountryMap());
                info.setLoginUser(ServiceFactory.getService(UserService.class).queryUserByEmail(users.getProperty(Constants.DEFAULT_ADMIN_EMAIL_KEY)));
                info.setSite(ServiceFactory.getService(SiteService.class).getSiteById(Constants.DEFAULT_SITE_ID));
                info.setUserID(username);
                req.getSession().setAttribute(Constants.ADMIN_LOGIN_INFO, info);
                // req.getRequestDispatcher(SUCCESS).forward(req, resp);
                if (null != debug) {
                    resp.sendRedirect(SUCCESS_DEBUG);
                } else {
                    resp.sendRedirect(SUCCESS);
                }
                return;
            }
        }

        // req.getRequestDispatcher(FAILURE).forward(req, resp);
        resp.sendRedirect(FAILURE);
        return;
    }

    private Map<String, Country> populateCountryMap() {
        List<Country> countries = ServiceFactory.getService(CountryService.class).getAllCountries();

        Map<String, Country> cMap = new HashMap<String, Country>();

        for (Iterator<Country> iterator = countries.iterator(); iterator.hasNext();) {
            Country country = (Country)iterator.next();
            cMap.put(country.getId() + "", country);
        }
        return cMap;
    }
}
