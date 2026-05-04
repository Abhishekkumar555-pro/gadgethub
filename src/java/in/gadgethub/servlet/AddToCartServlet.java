package in.gadgethub.servlet;

import in.gadgethub.dao.impl.CartDaoImpl;
import in.gadgethub.dao.impl.DemandDaoImpl;
import in.gadgethub.dao.impl.ProductDaoImpl;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddToCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String password = (String) session.getAttribute("password");
        String userType = (String) session.getAttribute("userType");
        if (userType == null || password == null || userName == null || !userType.equalsIgnoreCase("customer")) {
            response.sendRedirect("login.jsp?message=Access denied ! Please login first");
            return;
        }

        String userId = userName;
        String prodId = request.getParameter("pid");
        int pQty = Integer.parseInt(request.getParameter("pqty"));
        String action = request.getParameter("action");
        CartDaoImpl cartDao = new CartDaoImpl();
        ProductDaoImpl productDao = new ProductDaoImpl();
        ProductPojo productPojo = productDao.getProductDetails(prodId);
        int avQty = productDao.getProductQuantity(prodId);
        int cartQty = cartDao.getCartItemCount(userId, prodId);
        pQty += cartQty;
        if (action != null) {
            //buy now 
            cartDao.updateProductInCart(new CartPojo(userId, prodId, pQty));
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailsServlet");
            rd.forward(request, response);
        } else if (pQty == cartQty) {
            //remove from cart
            String status = cartDao.removeProductFromCart(userId, prodId);
            List<ProductPojo> products = productDao.getAllProducts();
            Map<String, Integer> map = new HashMap<>();
            for (ProductPojo product : products) {
                int qty = cartDao.getCartItemCount(userName, product.getProdId());
                map.put(product.getProdId(), qty);

            }
            session.setAttribute("products", products);
            session.setAttribute("map", map);
            RequestDispatcher rd = request.getRequestDispatcher("userhome.jsp");
            request.setAttribute("message", status);
            rd.forward(request, response);

        } else if (avQty < pQty) {
            String status = null;
            if (avQty == 0) {
                status = "Product out of stock!";
            } else {
                cartDao.updateProductInCart(new CartPojo(userId, prodId, avQty));
                status = "Only " + avQty + " no of " + productPojo.getProdName() + " are available, so we are adding only " + avQty + " products in your cart";
            }
            DemandDaoImpl demand = new DemandDaoImpl();
            DemandPojo demandPojo = new DemandPojo(userId, prodId, pQty - avQty);
            boolean flag = demand.addProduct(demandPojo);
            if (flag) {
                status += "<br/>Later we will mail you when " + productPojo.getProdName() + " will be available";
            }
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailsServlet");
            request.setAttribute("message", status);
            rd.forward(request, response);
        } else {
            String status = cartDao.updateProductInCart(new CartPojo(userId, prodId, pQty));

            List<ProductPojo> products = productDao.getAllProducts();
            Map<String, Integer> map = new HashMap<>();
            for (ProductPojo product : products) {
                int qty = cartDao.getCartItemCount(userName, product.getProdId());
                map.put(product.getProdId(), qty);
            }
            RequestDispatcher rd = request.getRequestDispatcher("userhome.jsp");

            session.setAttribute("products", products);
            session.setAttribute("map", map);

            request.setAttribute("message", status);
            rd.forward(request, response);
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
