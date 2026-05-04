
package in.gadgethub.dao;

import in.gadgethub.pojo.Userpojo;

public interface UserDao {
    String registerUser(Userpojo user);
    boolean isRegistered(String emailId);
    String isValidCredentials(String emailId,String password);
    Userpojo getUserDetails(String emailid);
    String getUserFirstName(String emailId);
    String getUserAddr(String emailId);
}
