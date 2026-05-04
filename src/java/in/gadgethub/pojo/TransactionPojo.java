package in.gadgethub.pojo;

import java.util.Date;

public class TransactionPojo {

    private String transactionId;
    private String userEmail;
    private Date transtime;
    private Double Amount;

    public TransactionPojo() {
    }

    public TransactionPojo(String transactionId, String userEmail, Date transtime, Double Amount) {
        this.transactionId = transactionId;
        this.userEmail = userEmail;
        this.transtime = transtime;
        this.Amount = Amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getTranstime() {
        return transtime;
    }

    public void setTranstime(Date transtime) {
        this.transtime = transtime;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double Amount) {
        this.Amount = Amount;
    }

    @Override
    public String toString() {
        return "TransactionPojo{" + "transactionId=" + transactionId + ", userEmail=" + userEmail + ", transtime=" + transtime + ", Amount=" + Amount + '}';
    }

}
