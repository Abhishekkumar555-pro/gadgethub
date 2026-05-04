package in.gadgethub.servlet;

import in.gadgethub.dao.impl.OrderDaoImpl;
import in.gadgethub.dao.impl.UserDAOImpl;
import in.gadgethub.dao.impl.transactionDaoImpl;
import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.Userpojo;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnshippedItemServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");      
        String password = (String) session.getAttribute("password");
        if (password == null || !userType.equalsIgnoreCase("admin")) {
            response.sendRedirect("login.jsp?message=Access denied ! Please login first");
            return;
        }
        OrderDaoImpl orderDao = new OrderDaoImpl();
        UserDAOImpl userDao = new UserDAOImpl();
        transactionDaoImpl transDao = new transactionDaoImpl();
        List<OrderPojo> orders = orderDao.getAllOrders();
        Map<String, String> user_Id = new HashMap<>();
        Map<String, String> user_address = new HashMap<>();
        for (OrderPojo order : orders) {
            String transId = order.getOrderId();
            String userid = transDao.getUserId(transId);
            user_Id.put(transId, userid);
            String address = userDao.getUserAddr(userid);
            user_address.put(userid, address);
        }
        RequestDispatcher rd = request.getRequestDispatcher("unshippedItems.jsp");
        request.setAttribute("orders", orders);
        request.setAttribute("user_Id", user_Id);
        request.setAttribute("user_address", user_address);
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
    }

}
