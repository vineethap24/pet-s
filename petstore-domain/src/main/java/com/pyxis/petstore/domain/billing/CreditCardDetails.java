package com.pyxis.petstore.domain.billing;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Access(AccessType.FIELD)
@DiscriminatorValue("credit_card")
public class CreditCardDetails extends PaymentMethod implements Serializable {

    @Enumerated(EnumType.STRING)
    private CreditCardType cardType;
    @NotEmpty 
    private String cardNumber;
    private String cardExpiryDate;

    @Embedded @AttributeOverrides({
        @AttributeOverride(name = "firstName", column = @Column(name = "billing_first_name")),
        @AttributeOverride(name = "lastName", column = @Column(name = "billing_last_name")),
        @AttributeOverride(name = "emailAddress", column = @Column(name = "billing_email"))
    })
    private Address billingAddress;

    public CreditCardDetails() {}

    public CreditCardDetails(CreditCardType cardType, String cardNumber, String cardExpiryDate, Address billingAddress) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.billingAddress = billingAddress;
    }

    public CreditCardType getCardType() {
        return cardType;
    }

    public void setCardType(CreditCardType cardType) {
        this.cardType = cardType;
    }

    public String getCardCommonName() {
        return cardType.commonName();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}
