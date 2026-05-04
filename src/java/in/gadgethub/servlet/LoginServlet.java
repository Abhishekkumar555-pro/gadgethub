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

public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        String status = "Login Denied ! Invalid UserId/Password";
        if (userType.equalsIgnoreCase("admin")) {
            if (userName.equals("Admin@gmail.com") && password.equals("Admin")) {
                RequestDispatcher rd = request.getRequestDispatcher("./AdminViewServlet");
                HttpSession session = request.getSession();
                session.setAttribute("userName", userName);
                session.setAttribute("userType", userType);
                session.setAttribute("password", password);
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp?message=" + status);
                rd.include(request, response);
            }

        } else if (userType.equalsIgnoreCase("customer")) {

            UserDAOImpl userDao = new UserDAOImpl();
            status = userDao.isValidCredentials(userName, password);
            if (status.equalsIgnoreCase("Login successful")) {
                Userpojo userpojo = userDao.getUserDetails(userName);
                HttpSession session = request.getSession();
                session.setAttribute("userdata", userpojo);
                session.setAttribute("userName", userName);
                session.setAttribute("userType", userType);
                session.setAttribute("password", password);
                RequestDispatcher rd = request.getRequestDispatcher("./UserHomeServlet");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp?message=" + status);
                rd.include(request, response);
            }

        }

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
    }

}
