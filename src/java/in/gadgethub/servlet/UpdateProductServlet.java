package in.gadgethub.servlet;

import in.gadgethub.dao.impl.ProductDaoImpl;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateProductServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String userType = (String) session.getAttribute("userType");
        String password = (String) session.getAttribute("password");
        
        if (userType == null || !userType.equalsIgnoreCase("admin")) {
            response.sendRedirect("login.jsp?message=Access Denied ! Please login as Admin");
            
        } else if (userName == null || password == null) {
            response.sendRedirect("login.jsp?message=Session Expired ! Please login Again");
        }
        String prodId = request.getParameter("pid");
        String prodName = request.getParameter("name");
        String prodInfo = request.getParameter("info");
        String prodType = request.getParameter("type");
        Double prodPrice = Double.parseDouble(request.getParameter("price"));
        Integer prodQuantity = Integer.parseInt(request.getParameter("quantity"));
        
        ProductPojo product = new ProductPojo();
        product.setProdId(prodId);
        product.setProdName(prodName);
        product.setProdInfo(prodInfo);
        product.setProdType(prodType);
        product.setProdPrice(prodPrice);
        product.setProdQuantity(prodQuantity);
        
        ProductDaoImpl productDao = new ProductDaoImpl();
        String status = productDao.updateProductWithoutImage(prodId, product);
        
        RequestDispatcher rd = request.getRequestDispatcher("updateProduct.jsp?message=" + status);
        request.setAttribute("product", product);
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
