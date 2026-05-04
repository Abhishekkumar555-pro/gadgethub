package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.ulility.DBUtil;
import in.gadgethub.ulility.IDUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    @Override
    public String addProduct(ProductPojo product) {
        String status = "Product Registration Failed";

        if (product.getProdId() == null) {
            product.setProdId(IDUtil.generateProdId());
        }
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("insert into Products values (?,?,?,?,?,?,?,?)");
            ps.setString(1, product.getProdId());
            ps.setString(2, product.getProdName());
            ps.setString(3, product.getProdType());
            ps.setString(4, product.getProdInfo());
            ps.setDouble(5, product.getProdPrice());
            ps.setInt(6, product.getProdQuantity());
            ps.setBlob(7, product.getProdImage());
            ps.setString(8, "Y");
            int count = ps.executeUpdate();
            if (count == 1) {
                status = "product Added ! " + product.getProdId();
            }
        } catch (SQLException ex) {
            System.out.println("Error in Addproduct" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String updateProduct(ProductPojo prevProduct, ProductPojo updatedProduct) {
        String status = "update failed!";
        if (!prevProduct.getProdId().equals(updatedProduct.getProdId())) {
            status = "product ID doesnot Match . Updatation failed!";
            return status;
        }
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("update products set pname=?,ptype=?,pinfo=?,pprice=?,pquantity=?,image=? where pid=?");
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getProdQuantity());
            ps.setBlob(6, updatedProduct.getProdImage());
            ps.setString(7, updatedProduct.getProdId());

            int count = ps.executeUpdate();
            if (count == 1) {
                status = "Product updated!";
            }
        } catch (SQLException ex) {
            System.out.println("Error in updateproduct" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String updateProductPrice(String prodId, double updatedPrice) {
        String status = "price cloudnot updated";
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("update products set pprice=? where pid=?");
            ps.setDouble(1, updatedPrice);
            ps.setString(2, prodId);
            int ans = ps.executeUpdate();
            if (ans == 1) {
                status = "price updated!";
            }
        } catch (SQLException ex) {
            status = "ERROR!" + ex.getMessage();
            System.out.println("Error in updateprice" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public List<ProductPojo> getAllProducts() {

        List<ProductPojo> productList = new ArrayList<>();
        Connection conn = DBUtil.provideconnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("Select * from products where available = 'Y' ");
            while (rs.next()) {
                ProductPojo product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
//              product.setProdImage(rs.getBinaryStream("image"));

                productList.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getallproducts" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
        return productList;
    }

    @Override
    public List<ProductPojo> getAllProductsByType(String type) {
        //search on bar and type some word like cam and search for camera
        List<ProductPojo> productList = new ArrayList<>();
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        type = type.toLowerCase();
        try {
            ps = conn.prepareStatement("Select * from products where lower(ptype) like ? and available ='Y'");
            ps.setString(1, "%" + type + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                ProductPojo product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType("ptype");
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);

            }

        } catch (SQLException ex) {
            System.out.println("Error in getallproductsByType" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return productList;
    }

    @Override
    public List<ProductPojo> searchAllProducts(String search) {
        //when search and display all the matching pattern like mobile camera ram . all matches in productname ,info,type
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        List<ProductPojo> productList = new ArrayList<>();
        ResultSet rs = null;
        search = search.toLowerCase();
        try {
            ps = conn.prepareStatement("Select * from products where lower(pname) like ? or lower(ptype) like ? or lower(pinfo) like ? and available = 'Y '");
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ps.setString(3, "%" + search + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductPojo product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdInfo("pinfo");
                product.setProdType(rs.getString("ptype"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("Error in searchallproducts" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return productList;
    }

    @Override
    public ProductPojo getProductDetails(String prodId) {
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductPojo product = null;

        try {
            ps = conn.prepareStatement("select * from products where pid=? and available = 'Y '");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                product = new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdInfo("pinfo");
                product.setProdType(rs.getString("ptype"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
            }
        } catch (SQLException ex) {
            System.out.println("Error in getProductDetails" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return product;
    }

    @Override
    public int getProductQuantity(String prodId) {
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int ProductQuantity = 0;
        try {
            ps = conn.prepareStatement("Select pquantity from products where pid=? ");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ProductQuantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getProductDetails" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return ProductQuantity;
    }

    @Override
    public String updateProductWithoutImage(String prevProductId, ProductPojo updatedProduct) {
        
        String status = "Product Updatation Failed !";
        
        int prevproductquantity = 0;
        
        if (!prevProductId.equals(updatedProduct.getProdId())) {
            status = "Product is doesnot match. Updation Failed";
            return status;
        }
        
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;

        try {
            prevproductquantity = getProductQuantity(prevProductId);
            ps = conn.prepareStatement("update products set pname=? ,ptype=?,pinfo=?,pprice=?,pquantity=? where pid=? ");
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getProdQuantity());
            ps.setString(6, updatedProduct.getProdId());
            int ans = ps.executeUpdate();
            if (ans == 1 && prevproductquantity < updatedProduct.getProdQuantity()) {
                status = "product Updated successfully and E-Mail Sent";
                //code for sending Mail.
            } else if (ans == 1) {
                status = "product Updated successfully";
            }
        } catch (SQLException ex) {
            System.out.println("Error in updateproductwithoutImage" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
 
    @Override
    public double getProductPrice(String prodId) {
        double productPrice = 0.0;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select pprice from products where pid=?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                productPrice = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getproductprice" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return productPrice;
    }

    @Override
    public boolean sellNProduct(String prodId, int n) {

        boolean status = false;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("update products set pquantity =(pquantity-?) where pid=? and available ='Y '");
            ps.setInt(1, n);
            ps.setString(2, prodId);
            int ans = ps.executeUpdate();
            if (ans == 1) {
                status = true;
            }

        } catch (SQLException ex) {
            System.out.println("Error in sellNProducts:" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;

    }

    @Override
    public List<String> getAllProductsType() {
        List<String> productTypeList = new ArrayList<>();
        Connection conn = DBUtil.provideconnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select distinct ptype from products where available = 'Y'");
            // distinct is a Predefined Keyword of Oracle which is use for the access a items or data only one time of a same catergories
            while (rs.next()) {
                productTypeList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAllProductsType:" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
        return productTypeList;
    }

    @Override
    public byte[] getImage(String prodId) {
        byte[] arr = null;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select image from products where pid =?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                arr = rs.getBytes(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error in setimage:" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return arr;
    }

    @Override
    public String removeProduct(String prodId) {
        String status = "product Not found ";
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            ps1 = conn.prepareCall("update products set available='N' where pid =? and available = 'Y'");
            ps1.setString(1, prodId);
            int ans = ps1.executeUpdate();
            if (ans == 1) {
                status = "product Removed Successfully ";
                ps2 = conn.prepareCall("Delete from usercart where prodid=?");
                ps2.setString(1, prodId);
                ans = ps2.executeUpdate();
            } 
        } catch (SQLException ex) {
            status = "ERROR:" + ex.getMessage();
            System.out.println("Error in RemoveProduct:" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

}
