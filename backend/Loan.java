package com.example.sample_project.entity; 
 
import jakarta.persistence.Entity; 
import jakarta.persistence.Table; 
 
import jakarta.persistence.*; 
 
import java.util.Date; 
 
@Entity 
@Table(name = "LOANS") 
public class Loan { 
 
    @Id 
    @Column(name = "loan_id") 
    private String loanId; 
 
    @Column(name = "loan_user_id" ,nullable = false) 
    private String loanUserId; 
 
    @Column(name = "loan_lender_id", nullable = false) 
    private String loanLenderId; 
 
    @Column(name = "loan_name", nullable = false) 
    private String loanName; 
 
    @Column(name = "loan_type", nullable = false) 
    private String loanType; 
 
    @Column(name = "loan_total_amount", nullable = false) 
    private double loanTotalAmount; 
 
    @Column(name = "loan_amount_principle", nullable = false) 
    private double loanAmountPrinciple; 
 
    @Column(name = "loan_amount_pending", nullable = false) 
    private double loanAmountPending; 
 
    @Column(name = "loan_interest_rate", nullable = false) 
    private double loanInterestRate; 
 
    @Column(name = "loan_tenure", nullable = false) 
    private int loanTenure; 
 
    @Column(name = "loan_start_date", nullable = false) 
    @Temporal(TemporalType.DATE) 
    private Date loanStartDate; 
 
    @Column(name = "loan_estimated_end_date", nullable = false) 
    @Temporal(TemporalType.DATE) 
    private Date loanEstimatedEndDate; 
 
    @Column(name = "loan_actual_end_date") 
    @Temporal(TemporalType.DATE) 
    private Date loanActualEndDate; 
 
    @Column(name = "loan_status", nullable = false) 
    private String loanStatus; 
 
    @Column(name = "due_date", nullable = false) 
    @Temporal(TemporalType.DATE) 
    private Date dueDate; 
 
    // Getters and Setters 
 
    public String getLoanId() { 
        return loanId; 
    } 
 
    public void setLoanId(String loanId) { 
        this.loanId = loanId; 
    } 
 
    public String getLoanUserId() { 
        return loanUserId; 
    } 
 
    public void setLoanUserId(String loanUserId) { 
        this.loanUserId = loanUserId; 
    } 
 
    public String getLoanLenderId() { 
        return loanLenderId; 
    } 
 
    public void setLoanLenderId(String loanLenderId) { 
        this.loanLenderId = loanLenderId; 
    } 
 
    public String getLoanName() { 
        return loanName; 
    } 
 
    public void setLoanName(String loanName) { 
        this.loanName = loanName; 
    } 
 
    public String getLoanType() { 
        return loanType; 
    } 
 
    public void setLoanType(String loanType) { 
        this.loanType = loanType; 
    } 
 
    public double getLoanTotalAmount() { 
        return loanTotalAmount; 
    } 
 
    public void setLoanTotalAmount(double loanTotalAmount) { 
        this.loanTotalAmount = loanTotalAmount; 
    } 
 
    public double getLoanAmountPrinciple() { 
        return loanAmountPrinciple; 
    } 
 
    public void setLoanAmountPrinciple(double loanAmountPrinciple) { 
        this.loanAmountPrinciple = loanAmountPrinciple; 
    } 
 
    public double getLoanAmountPending() { 
        return loanAmountPending; 
    } 
 
    public void setLoanAmountPending(double loanAmountPending) { 
        this.loanAmountPending = loanAmountPending; 
    } 
 
    public double getLoanInterestRate() { 
        return loanInterestRate; 
    } 
 
    public void setLoanInterestRate(double loanInterestRate) { 
        this.loanInterestRate = loanInterestRate; 
    } 
 
    public int getLoanTenure() { 
        return loanTenure; 
    } 
 
    public void setLoanTenure(int loanTenure) { 
        this.loanTenure = loanTenure; 
    } 
 
    public Date getLoanStartDate() { 
        return loanStartDate; 
    } 
 
    public void setLoanStartDate(Date loanStartDate) { 
        this.loanStartDate = loanStartDate; 
    } 
 
    public Date getLoanEstimatedEndDate() { 
        return loanEstimatedEndDate; 
    } 
 
    public void setLoanEstimatedEndDate(Date loanEstimatedEndDate) { 
        this.loanEstimatedEndDate = loanEstimatedEndDate; 
    } 
 
    public Date getLoanActualEndDate() { 
        return loanActualEndDate; 
    } 
 
    public void setLoanActualEndDate(Date loanActualEndDate) { 
        this.loanActualEndDate = loanActualEndDate; 
    } 
 
    public String getLoanStatus() { 
        return loanStatus; 
    } 
 
    public void setLoanStatus(String loanStatus) { 
        this.loanStatus = loanStatus; 
    } 
 
    public Date getDueDate() { 
        return dueDate; 
    } 
 
    public void setDueDate(Date dueDate) { 
        this.dueDate = dueDate; 
    } 
}