package custom.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Subscription {

    private String subId;
    private int userId;
    private String subName;
    private BigDecimal cost;
    private String billingCycle;
    private LocalDate nextBilling;
    private String siteURL;

    public Subscription(String subId, int userId, String subName, BigDecimal cost, String billingCycle, LocalDate nextBilling, String siteURL) {
        this.subId = subId;
        this.userId = userId;
        this.subName = subName;
        this.cost = cost;
        this.billingCycle = billingCycle;
        this.nextBilling = nextBilling;
        this.siteURL = siteURL;
    }

    public String getSubId() {
        return subId;
    }

    public int getUserId() {
        return userId;
    }

    public String getSubName() {
        return subName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public LocalDate getNextBilling() {
        return nextBilling;
    }

    public String getSiteURL() {
        return siteURL;
    }

}
