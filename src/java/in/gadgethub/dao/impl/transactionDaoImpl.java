package in.gadgethub.dao.impl;

import in.gadgethub.dao.TransactionDao;
import in.gadgethub.ulility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class transactionDaoImpl implements TransactionDao {

    @Override
    public String getUserId(String transId) {
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String user = "User Not Found / Invalid orderid";
        try {
            ps = conn.prepareStatement("select useremail from transactions where transid=?");
            ps.setString(1, transId);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getUserId" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return user;
    }
}
