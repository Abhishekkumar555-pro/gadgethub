package in.gadgethub.pojo;

public class Userpojo {

    private String username;
    private String emailid;
    private String mobile;
    private String address;
    private int pincode;
    private String password;

    public Userpojo() {
        
    }

    public Userpojo(String username, String emailid, String mobile, String address, int pincode, String password) {
        this.username = username;
        this.emailid = emailid;
        this.mobile = mobile;
        this.address = address;
        this.pincode = pincode;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    @Override
    public String toString() {
        return "Userpojo{" + "username=" + username + ", emailid=" + emailid + ", mobile=" + mobile + ", address=" + address + ", pincode=" + pincode + ", password=" + password + '}';
    }

    

}
