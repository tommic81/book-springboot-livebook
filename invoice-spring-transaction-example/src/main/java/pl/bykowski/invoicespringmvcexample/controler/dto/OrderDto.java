package pl.bykowski.invoicespringmvcexample.controler.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {

    private BigDecimal finalPrice;

    private String clientName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public OrderDto(BigDecimal finalPrice, String clientName, LocalDate date) {
        this.finalPrice = finalPrice;
        this.clientName = clientName;
        this.date = date;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "finalPrice=" + finalPrice +
                ", clientName='" + clientName + '\'' +
                ", date=" + date +
                '}';
    }
}
