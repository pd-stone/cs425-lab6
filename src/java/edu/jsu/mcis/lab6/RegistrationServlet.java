package edu.jsu.mcis.lab6;

import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;

import edu.jsu.mcis.lab6.dao.*;


public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        
        DAOFactory daoFactory = null;

        ServletContext context = request.getServletContext();

        if (context.getAttribute("daoFactory") == null) {
            System.err.println("*** Creating new DAOFactory ...");
            daoFactory = new DAOFactory();
            context.setAttribute("daoFactory", daoFactory);
        }
        else {
            daoFactory = (DAOFactory) context.getAttribute("daoFactory");
        }
        
        response.setContentType("application/json; charset=UTF-8");
        
        try ( PrintWriter out = response.getWriter()) {
            
            int sessionid = Integer.parseInt(request.getParameter("sessionid"));
            int attendeeid = Integer.parseInt(request.getParameter("attendeeid"));
            
            RegistrationDAO dao = daoFactory.getRegistrationDAO();
            
            out.println(dao.find(sessionid, attendeeid));
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        DAOFactory daoFactory = null;
        
        ServletContext context = request.getServletContext();
        
        if (context .getAttribute("daoFactory") == null) {
            System.err.println("*** Creating new DAOFactory ...");
            daoFactory = new DAOFactory();
            context.setAttribute("daoFactory", daoFactory);
        } else {
            daoFactory = (DAOFactory) context.getAttribute("daoFactory");
        }
        
        response.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int sessionid = Integer.parseInt(request.getParameter("sessionid"));
            int attendeeid = Integer.parseInt(request.getParameter("attendeeid"));
            
            RegistrationDAO dao = daoFactory.getRegistrationDAO();
            out.println(dao.create(sessionid, attendeeid));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        
           BufferedReader br = null;
           response.setContentType("application/json;charset=UTF-8");
        
            try (PrintWriter out = response.getWriter()) {
                    br = new BufferedReader(new InputStreamReader(request.getInputStream()));
                    String p = URLDecoder.decode(br.readLine().trim(), Charset.defaultCharset());
                    HashMap<String, String> parameters = new HashMap<>();
                    String[] pairs = p.trim().split("&");

                    for (int i = 0; i < pairs.length; ++i) {
                        String[] pair = pairs[i].split("=");
                        parameters.put(pair[0], pair[1]);
                    }
                    DAOFactory daoFactory = null;

                    ServletContext context = request.getServletContext();

                    if (context.getAttribute("daoFactory") == null) {
                        System.err.println("*** Creating new DAOFactory ...");
                        daoFactory = new DAOFactory();
                        context.setAttribute("daoFactory", daoFactory);
                    } else {
                        daoFactory = (DAOFactory) context.getAttribute("daoFactory");
                    }

                    response.setContentType("application/json; charset=UTF-8");

                        int sessionid_old = Integer.parseInt(request.getParameter("sessionid_old"));
                        int attendeeid_old = Integer.parseInt(request.getParameter("attendeeid_old"));
                        int sessionid_new = Integer.parseInt(request.getParameter("sessionid_new"));
                        int attendeeid_new = Integer.parseInt(request.getParameter("attendeeid_new"));

                        RegistrationDAO dao = daoFactory.getRegistrationDAO();

                        out.println(dao.update(sessionid_old, attendeeid_old, sessionid_new, attendeeid_new));
                    }

                catch (Exception e) {
                    e.printStackTrace();
                }

    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        DAOFactory daoFactory = null;

        ServletContext context = request.getServletContext();

        if (context.getAttribute("daoFactory") == null) {
            System.err.println("*** Creating new DAOFactory ...");
            daoFactory = new DAOFactory();
            context.setAttribute("daoFactory", daoFactory);
        } else {
            daoFactory = (DAOFactory) context.getAttribute("daoFactory");
        }

        response.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            int sessionid = Integer.parseInt(request.getParameter("sessionid"));
            int attendeeid = Integer.parseInt(request.getParameter("attendeeid"));

            RegistrationDAO dao = daoFactory.getRegistrationDAO();

            out.println(dao.delete(attendeeid, sessionid));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Registration Servlet";
    }
}