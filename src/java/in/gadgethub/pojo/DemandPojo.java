package in.gadgethub.pojo;

public class DemandPojo {

    
    private String useremail;
    private String ProdId;
    private int demandQuantity;

    public DemandPojo() {
    }

    public DemandPojo(String useremail, String ProdId, int demandQuantity) {
        this.useremail = useremail;
        this.ProdId = ProdId;
        this.demandQuantity = demandQuantity;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String ProdId) {
        this.ProdId = ProdId;
    }

    public int getDemandQuantity() {
        return demandQuantity;
    }

    public void setDemandQuantity(int demandQuantity) {
        this.demandQuantity = demandQuantity;
    }

    
    @Override
    public String toString() {
        return "DemandPojo{" + "useremail=" + useremail + ", ProdId=" + ProdId + ", demandQuantity=" + demandQuantity + '}';
    }
    
}
