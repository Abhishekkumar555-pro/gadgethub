package in.gadgethub.servlet;

import in.gadgethub.dao.impl.ProductDaoImpl;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize = 161777215)
//used for accepting file  from Jsp or frontend
//request.getpart for recive file from frondend
public class AddProductServlet extends HttpServlet {

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
        RequestDispatcher rd = null;
        String status = "Product Registration Failed !";
        String prodName = request.getParameter("name");
        String prodtype = request.getParameter("type");
        String prodInfo = request.getParameter("info");
        double prodPrice = 0.0;
        int prodQuantity = 0;
        String priceParam = request.getParameter("price");
        if (priceParam != null) {
            try {
                prodPrice = Double.parseDouble(priceParam);
            } catch (NumberFormatException ex) {
                status = "Invalid unit Price";
                request.setAttribute("message", status);
                rd = request.getRequestDispatcher("addProduct.jsp");
                rd.forward(request, response);
                return;
            }
        } else {
            status = "Please Enter Price";
            request.setAttribute("message", status);
            rd = request.getRequestDispatcher("addProduct.jsp");
            rd.forward(request, response);
            return;
        }
//        --------------------------------------------
        String qtyParam = request.getParameter("quantity");
        if (qtyParam != null) {
            try {
                prodQuantity = Integer.parseInt(qtyParam);
            } catch (NumberFormatException ex) {
                status = "invalid Quantity";
                request.setAttribute("message", status);
                rd = request.getRequestDispatcher("addProduct.jsp");
                rd.forward(request, response);
                return;
            }
        } else {
            status = "Quantity cannot be blank";
            request.setAttribute("message", status);
            rd = request.getRequestDispatcher("addProduct.jsp");
            rd.forward(request, response);
            return;
        }
//-----------------Fetching The File--------------------------------------
        Part part = request.getPart("image");
        InputStream img = part.getInputStream();

        ProductDaoImpl productDao = new ProductDaoImpl();
        ProductPojo product = new ProductPojo();
        product.setProdId(null);
        product.setProdName(prodName);
        product.setProdType(prodtype);
        product.setProdInfo(prodInfo);
        product.setProdPrice(prodPrice);
        product.setProdQuantity(prodQuantity);
        product.setProdImage(img);

        status = productDao.addProduct(product);
        request.setAttribute("message", status);
        rd = request.getRequestDispatcher("addProduct.jsp");
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
