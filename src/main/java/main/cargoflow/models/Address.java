package main.cargoflow.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String cep;
    private String street;
    private String state;
    private String number;
    private String additionalInfo;

    public Address() {}

    public Address(String cep, String street, String state, String number, String additionalInfo) {
        this.cep = cep;
        this.street = street;
        this.state = state;
        this.number = number;
        this.additionalInfo = additionalInfo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "Address [cep=" + cep + ", street=" + street + ", state=" + state + ", number=" + number + ", additionalInfo=" + additionalInfo + "]";
    }
}
