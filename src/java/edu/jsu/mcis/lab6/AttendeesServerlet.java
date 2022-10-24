package edu.jsu.mcis.lab6;

import edu.jsu.mcis.lab6.dao.*;

import java.io.PrintWriter;
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
    public String getServletInfo() {
        return "Short description";
    }

}
