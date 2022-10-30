package edu.jsu.mcis.lab6;

import edu.jsu.mcis.lab6.dao.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

public class AttendeesServerlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
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

            int attendeeid = Integer.parseInt(request.getParameter("attendeeid"));

            AttendeesDAO dao = daoFactory.getAttendeesDAO();

            out.println(dao.find(attendeeid));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
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
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String displayname = request.getParameter("displayname");
            AttendeesDAO dao = daoFactory.getAttendeesDAO();
            out.println(dao.create(firstname,lastname, displayname));
        } catch (Exception e) {
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

            System.err.println(parameters);
            int id = Integer.parseInt(parameters.get("attendeeid"));

            
            String firstname = (String) parameters.get("firstname");
            String lastname = (String) parameters.get("lastname");
            String displayname = (String) parameters.get("displayname");

            DAOFactory daoFactory = null;

            ServletContext context = request.getServletContext();

            if (context.getAttribute("daoFactory") == null) {
                System.err.println("*** Creating new DAOFactory ...");
                daoFactory = new DAOFactory();
                context.setAttribute("daoFactory", daoFactory);
            } else {
                daoFactory = (DAOFactory) context.getAttribute("daoFactory");
            }

            AttendeesDAO dao = daoFactory.getAttendeesDAO();

            out.println(dao.update(id, firstname, lastname, displayname));

        }

        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
