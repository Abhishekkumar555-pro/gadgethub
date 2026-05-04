package in.gadgethub.pojo;

public class OrderPojo {

    private String orderId;
    private String ProdId;
    private int quantity;
    private double amount;
    private int shipped;

    public OrderPojo() {
    }

    public OrderPojo(String orderId, String ProdId, int quantity, double amount, int shipped) {
        this.orderId = orderId;
        this.ProdId = ProdId;
        this.quantity = quantity;
        this.amount = amount;
        this.shipped = shipped;
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

    @Override
    public String toString() {
        return "OrderPojo{" + "orderId=" + orderId + ", ProdId=" + ProdId + ", quantity=" + quantity + ", amount=" + amount + ", shipped=" + shipped + '}';
    }

}
