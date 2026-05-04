package in.gadgethub.pojo;

import java.io.InputStream;


public class ProductPojo {

  
private String ProdId;
    private String  ProdName;
    private String ProdType;
    private String ProdInfo;
    private double ProdPrice;
    private int ProdQuantity;
    private InputStream prodImage;
    
  public ProductPojo() {
    }
  
    public ProductPojo(String ProdId, String ProdName, String ProdType, String ProdInfo, double ProdPrice, int ProdQuantity, InputStream prodImage) {
        this.ProdId = ProdId;
        this.ProdName = ProdName;
        this.ProdType = ProdType;
        this.ProdInfo = ProdInfo;
        this.ProdPrice = ProdPrice;
        this.ProdQuantity = ProdQuantity;
        this.prodImage = prodImage;
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String ProdId) {
        this.ProdId = ProdId;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String ProdName) {
        this.ProdName = ProdName;
    }

    public String getProdType() {
        return ProdType;
    }

    public void setProdType(String ProdType) {
        this.ProdType = ProdType;
    }

    public String getProdInfo() {
        return ProdInfo;
    }

    public void setProdInfo(String ProdInfo) {
        this.ProdInfo = ProdInfo;
    }

    public double getProdPrice() {
        return ProdPrice;
    }

    public void setProdPrice(double ProdPrice) {
        this.ProdPrice = ProdPrice;
    }

    public int getProdQuantity() {
        return ProdQuantity;
    }

    public void setProdQuantity(int ProdQuantity) {
        this.ProdQuantity = ProdQuantity;
    }

    public InputStream getProdImage() {
        return prodImage;
    }

    public void setProdImage(InputStream prodImage) {
        this.prodImage = prodImage;
    }

    @Override
    public String toString() {
        return "ProductPojo{" + "ProdId=" + ProdId + ", ProdName=" + ProdName + ", ProdType=" + ProdType + ", ProdInfo=" + ProdInfo + ", ProdPrice=" + ProdPrice + ", ProdQuantity=" + ProdQuantity + '}';
    }
    
    
}
