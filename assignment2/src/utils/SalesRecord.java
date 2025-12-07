package utils;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable domain model representing one row of sales data.
 */
public final class SalesRecord {

    private final String orderId;
    private final String region;
    private final String country;
    private final String item;
    private final LocalDate orderDate;
    private final int units;
    private final double unitPrice;

    public SalesRecord(String orderId,
                       String region,
                       String country,
                       String item,
                       LocalDate orderDate,
                       int units,
                       double unitPrice) {
        this.orderId = orderId;
        this.region = region;
        this.country = country;
        this.item = item;
        this.orderDate = orderDate;
        this.units = units;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getItem() {
        return item;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public int getUnits() {
        return units;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getRevenue() {
        return units * unitPrice;
    }

    @Override
    public String toString() {
        return "SalesRecord{" +
                "orderId='" + orderId + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", item='" + item + '\'' +
                ", orderDate=" + orderDate +
                ", units=" + units +
                ", unitPrice=" + unitPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesRecord)) return false;
        SalesRecord that = (SalesRecord) o;
        return units == that.units
                && Double.compare(that.unitPrice, unitPrice) == 0
                && Objects.equals(orderId, that.orderId)
                && Objects.equals(region, that.region)
                && Objects.equals(country, that.country)
                && Objects.equals(item, that.item)
                && Objects.equals(orderDate, that.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, region, country, item, orderDate, units, unitPrice);
    }
}
