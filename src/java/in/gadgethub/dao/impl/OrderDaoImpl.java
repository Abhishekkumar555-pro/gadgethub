package in.gadgethub.dao.impl;

import in.gadgethub.dao.OrderDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.OrderDetailsPojo;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.TransactionPojo;
import in.gadgethub.ulility.DBUtil;
import in.gadgethub.ulility.IDUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    
    @Override
    public boolean addOrder(OrderPojo order) {
        boolean result = false;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into orders  values (?,?,?,?,?) ");
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getProdId());
            ps.setInt(3, order.getQuantity());
            ps.setDouble(4, order.getAmount());
            ps.setInt(5, 0);
            int ans = ps.executeUpdate();
            if (ans == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error in addOrder" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return result;
    }
    
    @Override
    public boolean addTransaction(TransactionPojo trans) {
        boolean result = false;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into transactions  values (?,?,?,?) ");
            ps.setString(1, trans.getTransactionId());
            ps.setString(2, trans.getUserEmail());
            java.util.Date d1 = trans.getTranstime();
            System.out.println("add transacion d1"+d1);
            Timestamp d2 = new java.sql.Timestamp(d1.getTime());
             System.out.println("add transacion d2"+d2);
            ps.setTimestamp(3, d2);
            ps.setDouble(4, trans.getAmount());
            int ans = ps.executeUpdate();
            if (ans == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error in addTransaction" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return result;
        
    }
    
    @Override
    public List<OrderPojo> getAllOrders() {
        
        List<OrderPojo> orderList = new ArrayList<>();
        ResultSet rs = null;
        Connection conn = DBUtil.provideconnection();
        Statement st = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from orders");
            while (rs.next()) {
                OrderPojo orderpojo = new OrderPojo();
                orderpojo.setOrderId(rs.getString("orderid"));
                orderpojo.setProdId(rs.getString("prodid"));
                orderpojo.setQuantity(rs.getInt("quantity"));
                orderpojo.setAmount(rs.getDouble("amount"));
                orderpojo.setShipped(rs.getInt("shipped"));
                orderList.add(orderpojo);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAllOrders " + ex.toString());
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(st);
        return orderList;
    }
    
    @Override
    public List<OrderDetailsPojo> getAllOrderDetails(String userEmailId) {
        List<OrderDetailsPojo> orderDetailsList = new ArrayList<>();
        
        ResultSet rs = null;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select p.pid as prodid,o.orderid as orderid ,"
                    + " o.shipped as shipped , p.image as image"
                    + ",p.pname as pname,o.quantity as qty,"
                    + "o.amount as amount,t.transtime as time "
                    + "FROM orders o,products p ,transactions t"
                    + " where o.orderid=t.transid and o.prodid = p.pid "
                    + "and t.useremail = ?");
            ps.setString(1, userEmailId);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetailsPojo orderpojo = new OrderDetailsPojo();
                
                orderpojo.setOrderId(rs.getString("orderid"));
                orderpojo.setProdId(rs.getString("prodid"));
                orderpojo.setQuantity(rs.getInt("qty"));
                orderpojo.setAmount(rs.getDouble("amount"));
                orderpojo.setShipped(rs.getInt("shipped"));
                orderpojo.setProdName(rs.getString("pname"));
                orderpojo.setTime(rs.getTimestamp("time"));
                orderpojo.setProdImage(rs.getAsciiStream("image"));
                
                orderDetailsList.add(orderpojo);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAllOrderDetails" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return orderDetailsList;
    }
    
    @Override
    public String shipNow(String orderId, String prodId) {
        String status = "failure";
        
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("update orders set shipped=1  where orderid=?and prodid=?");
            ps.setString(1, orderId);
            ps.setString(2, prodId);
            int ans = ps.executeUpdate();
            if (ans == 1) {
                status = "Order has been shipped successfully";
            }
        } catch (SQLException ex) {
            System.out.println("Error in shipnow" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    
    @Override
    public String paymentSuccess(String username, double paidamount) {
        
        String status = "Order Placement  Failed !";
        CartDaoImpl cartdaoimpl = new CartDaoImpl();
        List<CartPojo> cartlist = cartdaoimpl.getAllCartItems(username);
        if (cartlist.isEmpty()) {
            return status;
        }
        String tid = IDUtil.generateTransId();
        
        TransactionPojo trans = new TransactionPojo();
        trans.setTransactionId(tid);
        trans.setTranstime(new java.util.Date());
        trans.setUserEmail(username);
        trans.setAmount(paidamount);
        boolean result = addTransaction(trans);
        if (result == false) {
            return status;
        }
        
        boolean ordered = true;
        ProductDaoImpl pdao = new ProductDaoImpl();
        for (CartPojo c : cartlist) {
            int prodqty = c.getProdQuantity();
            String pid = c.getProdId();
            String useremail = c.getUsermail();
            double pprice = pdao.getProductPrice(pid);
            double orderamount = (prodqty * pprice);
            OrderPojo orderpojo = new OrderPojo();
            orderpojo.setOrderId(tid);
            orderpojo.setProdId(pid);
            orderpojo.setQuantity(prodqty);
            orderpojo.setAmount(orderamount);
            orderpojo.setShipped(0);
            ordered = addOrder(orderpojo);
            if (!ordered) {
                break;
            }
            ordered = cartdaoimpl.removeAProduct(useremail, pid);
            if (!ordered) {
                break;
            }
            ordered = pdao.sellNProduct(pid, prodqty);
            if (!ordered) {
                break;
            }
        }
        if (ordered) {
            status = "Order Placed Successfully ";
            System.out.println("Transaction successful" + tid);
        } else {
            System.out.println("Transaction Failed " + tid);
        }
        return status;
    }
    
    @Override
    public int getSoldQuantity(String prodId) {
        
        ResultSet rs = null;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        int quantity = 0;
        try {
            ps = conn.prepareStatement(" select sum(quantity) from orders where prodid=?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getSoldQuantity" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return quantity;
        
    }
}
