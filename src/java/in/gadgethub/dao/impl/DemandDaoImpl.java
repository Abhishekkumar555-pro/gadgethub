package in.gadgethub.dao.impl;

import in.gadgethub.dao.DemandDao;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.ulility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DemandDaoImpl implements DemandDao {

    @Override
    public boolean addProduct(DemandPojo demandPojo) {
        boolean status = false;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            ps1 = conn.prepareStatement("Update userdemand set quantity=quantity +? where useremail=? and prodid=?");
            ps1.setInt(1, demandPojo.getDemandQuantity());
            ps1.setString(2, demandPojo.getUseremail());
            ps1.setString(3, demandPojo.getProdId());

            int ans = ps1.executeUpdate();
            if (ans == 0) {
                ps2 = conn.prepareStatement("insert into userdemand values(?,?,?)");
                ps2.setString(1, demandPojo.getUseremail());
                ps2.setString(2, demandPojo.getProdId());
                ps2.setInt(3, demandPojo.getDemandQuantity());
                ps2.executeUpdate();
            }
            status = true;
        } catch (SQLException ex) {
            System.out.println("Error in addproduct" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

    @Override
    public boolean removeProduct(String userid, String Prodid) {
        boolean status = false;
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("delete from userdemand where useremail=?,prodid=?");
            ps.setString(1, userid);
            ps.setString(2, Prodid);

            int ans = ps.executeUpdate();
            if (ans > 0) {
                status = true;
            }

        } catch (SQLException ex) {
            System.out.println("Error in removeproduct" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public List<DemandPojo> haveDemanded(String Prodid) {
        List<DemandPojo> demandList = new ArrayList<>();
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from userdemand where prodid=?");
            ps.setString(1, Prodid);
            rs = ps.executeQuery();
            while (rs.next()) {
                DemandPojo demandPojo = new DemandPojo();
                demandPojo.setProdId(rs.getString("prodid"));
                demandPojo.setUseremail(rs.getString("usermail"));
                demandPojo.setDemandQuantity(rs.getInt("quantity"));
                demandList.add(demandPojo);
            }
        } catch (SQLException ex) {
            System.out.println("Error in haveDemanded" + ex);
            ex.printStackTrace();
        }
        return demandList;  

    }

}
