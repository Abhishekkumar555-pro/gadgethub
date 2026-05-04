package in.gadgethub.pojo;

import java.io.InputStream;
import java.util.Date;

public class OrderDetailsPojo {

    private String orderId;
    private String ProdId;
    private String ProdName;
    private int quantity;
    private double amount;
    private int shipped;
    private Date time;
    private InputStream ProdImage;

    public OrderDetailsPojo() {
    }

    public OrderDetailsPojo(String orderId, String ProdId, String ProdName, int quantity, double amount, int shipped, Date time, InputStream ProdImage) {
        this.orderId = orderId;
        this.ProdId = ProdId;
        this.ProdName = ProdName;
        this.quantity = quantity;
        this.amount = amount;
        this.shipped = shipped;
        this.time = time;
        this.ProdImage = ProdImage;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public InputStream getProdImage() {
        return ProdImage;
    }

    public void setProdImage(InputStream ProdImage) {
        this.ProdImage = ProdImage;
    }

}
