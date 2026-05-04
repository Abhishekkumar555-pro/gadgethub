package in.gadgethub.servlet;

import in.gadgethub.dao.impl.UserDAOImpl;
import in.gadgethub.pojo.Userpojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("username");
        String userEmail = request.getParameter("useremail");
        String userAddress = request.getParameter("useraddress");
        String userMobile = request.getParameter("usermobile");
        int userPin = Integer.parseInt(request.getParameter("userpincode"));
        String password = request.getParameter("password");
        String cnfPassword = request.getParameter("cnfpassword");
        String status = null;
        UserDAOImpl userDao = new UserDAOImpl();
        if (!password.equals(cnfPassword)) {
            status = "Password doesnot Match";
               response.sendRedirect("register.jsp?message="+status);
        } else {

            boolean result = userDao.isRegistered(userEmail);
            if (!result) {
                  Userpojo user = new Userpojo();
                user.setUsername(userName);
                user.setEmailid(userEmail);
                user.setMobile(userMobile);
                user.setAddress(userAddress);
                user.setPincode(userPin);
                user.setPassword(password);
                status = userDao.registerUser(user);
                  response.sendRedirect("login.jsp?message="+status);
             
           }else{
                 status = "User Already Registerd !";
                response.sendRedirect("register.jsp?message="+status);
           
            }
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
