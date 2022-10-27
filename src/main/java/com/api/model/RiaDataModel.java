package com.api.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;
@Entity
@Table(name="ria_data_table")
public class RiaDataModel {
    @Id
    @Column(name = "row_id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @GeneratedValue(strategy = SEQUENCE, generator = "riaSeqGen")
    @SequenceGenerator(name = "riaSeqGen", sequenceName = "ria_seq", initialValue = 1)
    private int id;
    @Column(name = "pin")
    private String pin;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "orderno")
    private String orderNo;
    @Column(name = "remitter")
    private String remitter;
    @Column(name = "incentive")
    private Double incentive;
    @Column(name = "sender_country")
    private String senderCountry;
    @Column(name = "ben_name")
    private String beneficiaryName;
    @Column(name = "ben_account")
    private String beneficiaryAccount;
    @Column(name = "t24_status")
    private String t24Status;
    @Column(name = "status")
    private String status;
    @Column(name = "paid_date")
    private String paidDate;
    @Column(name = "paid_by")
    private String paidBy;


    public RiaDataModel(String pin, Double amount, String orderNo, String remitter, Double incentive, String senderCountry, String beneficiaryName, String beneficiaryAccount, String t24Status, String status, String paidDate, String paidBy) {
        this.pin = pin;
        this.amount = amount;
        this.orderNo = orderNo;
        this.remitter = remitter;
        this.incentive = incentive;
        this.senderCountry = senderCountry;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccount = beneficiaryAccount;
        this.t24Status = t24Status;
        this.status = t24Status;
        this.paidDate = paidDate;
        this.paidBy = paidBy;
    }

    public RiaDataModel() {

    }

    @Override
    public String toString() {
        return "RiaDataModel{" +
                "id=" + id +
                ", pin='" + pin + '\'' +
                ", amount=" + amount +
                ", orderNo='" + orderNo + '\'' +
                ", remitter='" + remitter + '\'' +
                ", incentive=" + incentive +
                ", senderCountry='" + senderCountry + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", t24Status='" + t24Status + '\'' +
                ", status='" + status + '\'' +
                ", paidDate='" + paidDate + '\'' +
                ", paidBy='" + paidBy + '\'' +
                '}';
    }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemitter() {
        return remitter;
    }

    public void setRemitter(String remitter) {
        this.remitter = remitter;
    }

    public Double getIncentive() {
        return incentive;
    }

    public void setIncentive(Double incentive) {
        this.incentive = incentive;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getT24Status() {
        return t24Status;
    }

    public void setT24Status(String t24Status) {
        this.t24Status = t24Status;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }
}
