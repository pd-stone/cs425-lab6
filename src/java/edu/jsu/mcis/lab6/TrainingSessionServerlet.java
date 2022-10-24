package edu.jsu.mcis.lab6;

import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import edu.jsu.mcis.lab6.dao.*;

public class TrainingSessionServerlet extends HttpServlet {
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

            TrainingSessionsDAO dao = daoFactory.getTrainingSessionsDAO();
            if (request.getParameterMap().containsKey("id")) {
                int id = Integer.parseInt(request.getParameter("id"));
                out.println(dao.find(id));
            }

            else{
                out.println(dao.list());
            }
        }
        catch (Exception e){
            e.printStackTrace();
            
        }
    }
}
