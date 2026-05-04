package in.gadgethub.dao.impl;


import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.Userpojo;
import in.gadgethub.ulility.DBUtil;
import in.gadgethub.ulility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class UserDAOImpl implements UserDao {

    @Override
    public String registerUser(Userpojo user) {
        String status = "Registration failed";
        boolean isUserRegisterd = isRegistered(user.getEmailid());
        if (isUserRegisterd) {
            status = "Email Already Registerd . Try Again";
            return status;
        }
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into users values (?,?,?,?,?,?)");
            ps.setString(1, user.getEmailid());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getMobile());
            ps.setString(4, user.getAddress());
            ps.setInt(5, user.getPincode());
            ps.setString(6, user.getPassword());
            int count = ps.executeUpdate();
            if (count == 1) {
                status = "Registration successfull";
               // MailMessage.registrationSuccess(user.getEmailid(), user.getUsername());
               MailMessage.registrationSuccess(user.getEmailid(), user.getUsername());
                System.out.println("Mail sent for registration");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR in registerUser !");
            ex.printStackTrace();
        } catch (MessagingException ex) { 
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public boolean isRegistered(String emailId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = DBUtil.provideconnection();
        boolean flag = false;
        try {
            ps = conn.prepareStatement("Select 1 from users where useremail=?");

            ps.setString(1, emailId);
            rs = ps.executeQuery();
            if (rs.next()) {
                flag = true;
            }
        } catch (SQLException ex) {
            System.out.println("ERROR in isregistered !");
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return flag;
    }

    @Override
    public String isValidCredentials(String emailId, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = DBUtil.provideconnection();
        String status = "Login Denied.Invalid username or password !";
        try {
            ps = conn.prepareStatement("select 1 from users where useremail=? and password=?");
            ps.setString(1, emailId);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                status = "Login successful";
            }
        } catch (SQLException ex) {
            status = "ERROR :" + ex.getMessage();
            System.out.println("ERROR in isValidCredentials !" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return status;
    }

    @Override
    public Userpojo getUserDetails(String emailid) {
        Connection conn = DBUtil.provideconnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Userpojo user = null;
        try {
            ps = conn.prepareStatement("Select * from users where useremail=?");
            ps.setString(1, emailid);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new Userpojo();
                user.setEmailid(rs.getString("useremail"));
                user.setUsername(rs.getString("username"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                user.setPincode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException ex) {

            System.out.println("Error in userDetails" + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return user;
    }

    @Override
    public String getUserFirstName(String emailId) {
        Connection conn = DBUtil.provideconnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String fname = null;
        try {
            ps = conn.prepareStatement("select username from users where useremail=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String fullname = rs.getString(1);
                fname = fullname.split(" ")[0];
            }
        } catch (SQLException ex) {
            System.out.println("Error in getUserFirstName");
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return fname;
    }

    @Override
    public String getUserAddr(String emailId) {
        Connection conn = DBUtil.provideconnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String address = null;
        try {
            ps = conn.prepareStatement("Select address from users where useremail=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();
            if (rs.next()) {
                address = rs.getString(1);
            }
        } catch (SQLException ex) {
            address = "Error:" + ex.getMessage();
            System.out.println("Error in getUserAddr");
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);

        return address;
    }

}
