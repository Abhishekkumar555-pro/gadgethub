  
package in.gadgethub.pojo;


public class CartPojo {
    private String usermail;
    private String ProdId;
    private int ProdQuantity;

    public CartPojo() {
    }

    public CartPojo(String usermail, String ProdId, int ProdQuantity) {
        this.usermail = usermail;
        this.ProdId = ProdId;
        this.ProdQuantity = ProdQuantity;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String ProdId) {
        this.ProdId = ProdId;
    }

    public int getProdQuantity() {
        return ProdQuantity;
    }

    public void setProdQuantity(int ProdQuantity) {
        this.ProdQuantity = ProdQuantity;
    }

    @Override
    public String toString() {
        return "CartPojo{" + "usermail=" + usermail + ", ProdId=" + ProdId + ", ProdQuantity=" + ProdQuantity + '}';
    }
    
    
}
