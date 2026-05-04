
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.OrderDaoImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ShipmentServlet extends HttpServlet {

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
          HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");      
        String password = (String) session.getAttribute("password");
        if (password == null || !userType.equalsIgnoreCase("admin")) {
            response.sendRedirect("login.jsp?message = Access denied ! Please login first");
            return;
        }
        String orderid=request.getParameter("orderid");
        String amount=request.getParameter("amount");
        double amt=Double.parseDouble(amount);
        String userName=request.getParameter("userid");
        String prodId=request.getParameter("prodid");
        OrderDaoImpl orderDao=new OrderDaoImpl();
        String status =  orderDao.shipNow(orderid, prodId);
        RequestDispatcher rd = request.getRequestDispatcher("./ShippedItemServlet");
        request.setAttribute("message", status);     
        rd.forward(request, response);
        
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
