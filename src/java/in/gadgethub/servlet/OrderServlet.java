package in.gadgethub.servlet;

import in.gadgethub.dao.impl.OrderDaoImpl;
import in.gadgethub.dao.impl.UserDAOImpl;
import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.ulility.MailMessage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MessagingException {

        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String password = (String) session.getAttribute("password");
        if (password == null || userName == null) {
            response.sendRedirect("login.jsp?message=Access denied ! Please login first");
            return;
        }

        String amount = request.getParameter("amount");
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDAOImpl user = new UserDAOImpl();
       
        if (amount != null && !amount.isEmpty()) {
            double paidAmount = Double.parseDouble(amount);

            String status = orderDao.paymentSuccess(userName, paidAmount);
         
          MailMessage.transactionSuccess(userName, user.getUserFirstName(userName), paidAmount);
           
            request.setAttribute("paymentStatus", status);
        }
        List<OrderDetailsPojo> orders = orderDao.getAllOrderDetails(userName);
     
        
        request.setAttribute("orders", orders);
        RequestDispatcher rd = request.getRequestDispatcher("orderDetails.jsp");
        rd.forward(request, response);

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
