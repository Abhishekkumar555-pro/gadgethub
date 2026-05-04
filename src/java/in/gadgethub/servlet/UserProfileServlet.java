package in.gadgethub.servlet;

import in.gadgethub.dao.impl.UserDAOImpl;
import in.gadgethub.pojo.Userpojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String password = (String) session.getAttribute("password");
        if (password == null || userName == null) {
            response.sendRedirect("login.jsp?message=Access denied ! Please login first");
            return;
        }

        UserDAOImpl userDao = new UserDAOImpl();
        Userpojo userpojo = userDao.getUserDetails(userName);
        RequestDispatcher rd = request.getRequestDispatcher("userProfile.jsp");
        request.setAttribute("user", userpojo);
        rd.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
