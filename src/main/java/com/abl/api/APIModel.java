package com.abl.api;
import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "apiDataTable")
public class APIModel {
    @Id
    @Column(name = "row_id")
    //@GeneratedValue(strategy=GenerationType.AUTO)
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private int id;
    @Column(name = "exCode")
    private int exCode;
    @Column(name = "tranNo")
    private String tranNo;
    @Column(name = "currency")
    private String currency;
    @Column(name = "amount")
    private double amount;
    @Column(name = "enteredDate")
    private String enteredDate;
    @Column(name = "remitter")
    private String remitter;
    @Column(name = "beneficiary")
    private String beneficiary;
    @Column(name = "beneficiaryAccount")
    private String beneficiaryAccount;
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "bankCode")
    private String bankCode;
    @Column(name = "branchName")
    private String branchName;
    @Column(name = "branchCode")
    private String branchCode;

    public APIModel(){

    }

    public APIModel(int exCode, String tranNo, String currency, double amount, String enteredDate, String remitter, String beneficiary, String beneficiaryAccount, String bankName, String bankCode, String branchName, String branchCode){
         this.exCode= exCode;
         this.tranNo= tranNo;
         this.currency = currency;
         this.amount = amount;
         this.enteredDate = enteredDate;
         this.remitter = remitter;
         this.beneficiary = beneficiary;
         this.beneficiaryAccount = beneficiaryAccount;
         this.bankName = bankName;
         this.bankCode = bankCode;
         this.branchName = branchName;
         this.branchCode = branchCode;
    }

    //--------------------getter-------------------------------
 
    public int getId(){
        return id;
    }
    public int getExCode(){
        return exCode;
    }
    public String getTranNo(){
        return tranNo;
    }
    public String getCurrency(){
        return currency;
    }
    public double getAmount(){
        return amount;
    }
    public String getEnteredDate(){
        return enteredDate;
    }
    
    public String getRemitter(){
        return remitter;
    }
    public String getBeneficiary(){
        return beneficiary;
    }
    public String getBeneficiaryAccount(){
        return beneficiaryAccount;
    }
    public String getBankName(){
        return bankName;
    }
    public String getBankCode(){
        return bankCode;
    }
    public String getBranchName(){
        return branchName;
    }
    public String getBranchCode(){
        return bankCode;
    }

    //---------------------------setter--------------------
 
    public void setId(int id){
        this.id = id;
    }
    public void setExCode(int exCode){
        this.exCode = exCode;
    }
    public void setTranNo(String tranNo){
        this.tranNo = tranNo;
    }
    public void setCurrency(String currency){
        this.currency = currency;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public void setEnteredDate(String enteredDate){
        this.enteredDate = enteredDate;
    }
    public void setRemitter(String remitter){
        this.remitter = remitter;
    }
    public void setBeneficiary(String beneficiary){
        this.beneficiary = beneficiary;
    }
    public void setBeneficiaryAccount(String beneficiaryAccount){
        this.beneficiaryAccount = beneficiaryAccount;
    }
    public void setBankName(String bankName){
        this.bankName = bankName;
    }
    public void setBankCode(String bankCode){
        this.bankCode = bankCode;
    }
    public void setBranchName(String branchName){
        this.branchName = branchName;
    }
    public void setBranchCode(String branchCode){
        this.branchCode = branchCode;
    }

    @Override
    public String toString(){
        return "API [id=" + id + ", exCode=" + exCode + ", tranNo=" + tranNo + ", currency=" + currency + ", amount=" + amount + ",enteredDate=" + enteredDate + ", remitter=" + remitter + ", beneficiary=" + beneficiary + ", beneficiaryAccount=" + beneficiaryAccount + ",bankName=" + bankName + ", bankCode=" + bankCode + ", branchName=" + branchName + ", branchCode=" + branchCode + "]";
    }
    
}
