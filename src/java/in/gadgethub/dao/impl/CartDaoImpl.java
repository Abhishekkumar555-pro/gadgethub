package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.ulility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImpl implements CartDao {

    @Override
    public String addProduct(CartPojo cart) {
        String status = "Failed to add product in cart";
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            ps1 = conn.prepareStatement("select * from usercart where prodid=? and useremail=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2, cart.getUsermail());
            rs = ps1.executeQuery();
            if (rs.next()) {
                int cartQuantity = rs.getInt("quantity");
                ProductDaoImpl pdo = new ProductDaoImpl();
                int stockQuantity = pdo.getProductQuantity(cart.getProdId());
                int newQuantity = cartQuantity + cart.getProdQuantity();
                if (newQuantity > stockQuantity) {
                    cart.setProdQuantity(stockQuantity);
                    updateProductInCart(cart);
                    status = "only " + stockQuantity + " available, so we are adding " + stockQuantity + " in your cart";

                    int requiredQuantity = newQuantity - stockQuantity;

                    DemandPojo dpo = new DemandPojo();
                    dpo.setProdId(cart.getProdId());
                    dpo.setUseremail(cart.getUsermail());
                    dpo.setDemandQuantity(requiredQuantity);
                    DemandDaoImpl dmdDaoImpl = new DemandDaoImpl();
                    boolean result = dmdDaoImpl.addProduct(dpo);
                    if (result == true) {
                        status += "<br/>we will mail you when items will be available";
                    }
                } else {
                    cart.setProdQuantity(newQuantity);
                    status = updateProductInCart(cart);
                }
            }
        } catch (SQLException ex) {
            status = "Updation Failed due to exception";
            System.out.println("Error in addProduct in cart" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public String updateProductInCart(CartPojo cart) {
        String status = "Failed to Add Into Cart";
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        ResultSet rs = null;
        try {
            ps1 = conn.prepareStatement("select quantity from usercart where prodid=? and useremail=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2, cart.getUsermail());
            rs = ps1.executeQuery();
            if (rs.next()) {
                int quantity = cart.getProdQuantity();
                if (quantity > 0) {
                    ps2 = conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
                    ps2.setInt(1, cart.getProdQuantity());
                    ps2.setString(2, cart.getUsermail());
                    ps2.setString(3, cart.getProdId());
                    int ans = ps2.executeUpdate();
                    if (ans == 1) {
                        status = "Product SuccessFully Updated into Cart";
                    } else {
                        status = "Could Not update  product into Cart";
                    }
                } else {
                    ps2 = conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
                    ps2.setString(1, cart.getUsermail());
                    ps2.setString(2, cart.getProdId());
                    int ans = ps2.executeUpdate();
                    if (ans == 1) {
                        status = "Product SuccessFully Removed From Cart";
                    } else {
                        status = "Could Not Removed  productFrom Cart";
                    }
                }
            }  else {
                ps2 = conn.prepareStatement("insert into usercart values(?,?,?)");
                ps2.setString(1, cart.getUsermail());
                ps2.setString(2, cart.getProdId());
                ps2.setInt(3, cart.getProdQuantity());
                int ans = ps2.executeUpdate();
                if (ans == 1) {
                    status = "Product SuccessFully Added in the Cart";
                } else {
                    status = "Could Not Add  product into Cart  ! sorry";
                }
            }
        } catch (SQLException ex) {
            status = "Updation Failed due to exception";
            System.out.println("Error in updateProduct in cart" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        DBUtil.closeResultSet(rs);
        return status;

    }

    @Override
    public List<CartPojo> getAllCartItems(String userId) {
        List<CartPojo> cartList = new ArrayList<>();
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from usercart where useremail=?");
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                CartPojo cartpojo = new CartPojo();
                cartpojo.setProdId(rs.getString("prodid"));
                cartpojo.setUsermail(rs.getString("useremail"));
                cartpojo.setProdQuantity(rs.getInt("quantity"));
                cartList.add(cartpojo);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAllCartItems in cart" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);

        return cartList;
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        if(userId==null ||  itemId==null){
        return 0;
        }
        
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            ps = conn.prepareStatement("select quantity from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, itemId);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getCartItemCount in cart" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);

        return count;
    }

    @Override
    public String removeProductFromCart(String userId, String prodId) {

        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        String status = "Removed Product From Cart Failed";
        try {
            ps = conn.prepareStatement("select quantity from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
//                int quantity = rs.getInt("quantity");
//                quantity =quantity- 1;
//                if (quantity > 0) {
//                    ps = conn.prepareStatement("update usercart set quantity=? where useremail=? and prodid=?");
//                    ps.setInt(1, quantity);
//                    ps.setString(2, userId);
//                    ps.setString(3, prodId);
//                    int ans = ps.executeUpdate();
//                    if (ans == 1) {
//                        status = "Product successfully removed from cart";
//                    }
//
//                } else {
                    ps2 = conn.prepareStatement("delete from  usercart  where useremail=? and prodid=?");
                    ps2.setString(1, userId);
                    ps2.setString(2, prodId);
                    int ans = ps2.executeUpdate();
                    if (ans == 1) {
                        status = "Product removed from cart";
                    }
//                }

            }
        } catch (SQLException ex) {
            System.out.println("Error in removeProductFromCart" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeStatement(ps2);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public boolean removeAProduct(String userId, String prodId) {
        boolean result = false;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from usercart where useremail=? and prodid=?");
            ps.setString(1, userId);
            ps.setString(2, prodId);
            int ans = ps.executeUpdate();
            if (ans == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error in removeAProduct" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return result;
    }
}
