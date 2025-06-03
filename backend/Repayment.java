package com.example.sample_project.entity; 
 
import jakarta.persistence.Entity; 
import jakarta.persistence.Table; 
 
import jakarta.persistence.*; 
 
import java.util.Date; 
 
@Entity 
@Table(name = "repayments") 
public class Repayment { 
    @Id 
    @Column(name = "repayment_id") 
    private String repaymentId; 
 
    @Column(name = "repayment_installment_amount") 
    private Double repaymentInstallmentAmount; 
 
    @Column(name = "repayment_status") 
    private String repaymentStatus; 
 
    @Column(name = "loan_status") 
    private String loanStatus; 
 
    @Column(name = "repayment_paid_date") 
    private Date repaymentPaidDate; 
 
    @Column(name = "repayment_loan_id") 
    private String repaymentLoanId; 
 
    public String getRepaymentLoanId() { 
        return repaymentLoanId; 
    } 
 
    public void setRepaymentLoanId(String repaymentLoanId) { 
        this.repaymentLoanId = repaymentLoanId; 
    } 
 
    public String getLoanStatus() { 
        return loanStatus; 
    } 
 
    public void setLoanStatus(String loanStatus) { 
        this.loanStatus = loanStatus; 
    } 
 
    public String getRepaymentId() { 
        return repaymentId; 
    } 
 
    public void setRepaymentId(String repaymentId) { 
        this.repaymentId = repaymentId; 
    } 
 
    public Double getRepaymentInstallmentAmount() { 
        return repaymentInstallmentAmount; 
    } 
 
    public void setRepaymentInstallmentAmount(Double repaymentInstallmentAmount) { 
        this.repaymentInstallmentAmount = repaymentInstallmentAmount; 
    } 
 
    public String getRepaymentStatus() { 
        return repaymentStatus; 
    } 
 
    public void setRepaymentStatus(String repaymentStatus) { 
        this.repaymentStatus = repaymentStatus; 
    } 
 
    public Date getRepaymentPaidDate() { 
        return repaymentPaidDate; 
    } 
 
    public void setRepaymentPaidDate(Date repaymentPaidDate) { 
        this.repaymentPaidDate = repaymentPaidDate; 
    } 
 
}