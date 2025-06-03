import React, { useState, useEffect } from 'react'; 
import { addMonths, format } from 'date-fns'; 
import { useLocation, useNavigate } from "react-router-dom"; 
import { updateDueDate, postRepayment, fetchLatestRepaymentId } from "../api/Repaymentapi"; 
import axios from 'axios'; 
import VerticleNavbar from "./VerticleNavbar"; 
import Header from './Header'; 
import "../styles/CalculateRepayment.css"; 
import RepaymentHistory from './RepaymentHistory'; 
 
/* 
 * CalculateRepayment Component 
 * This component calculates the EMI for a loan, displays repayment details, and allows users to make repayments. It fetches loan data from the backend 
 * and updates repayment history accordingly. 
 */ 
 
function CalculateRepayment() { 
    const location = useLocation(); 
    const navigate = useNavigate(); 
    const { loanId } = location.state || {}; 
    const token = localStorage.getItem("token"); 
 
    // State variables for loan details and repayment calculations 
    const [loanData, setLoanData] = useState(null); 
    const [emi, setEmi] = useState(0); 
    const [nextRepaymentDate, setNextRepaymentDate] = useState(''); 
    const [totalPendingAmount, setTotalPendingAmount] = useState(); 
    const [paymentDetails, setPaymentDetails] = useState({}); 
 
    //Fetch updated loan details from the backend when the component mounts. 
    useEffect(() => { 
        const fetchUpdatedLoanData = async () => { 
            if (!loanId) return; 
            try { 
                const response = await axios.get(`http://localhost:8090/api/loans/loan-details/${loanId}`, { 
                    headers: { Authorization: `Bearer ${token}` }, 
                }); 
                if (response.data) { 
                    setLoanData(response.data); 
                    setTotalPendingAmount(response.data.loanAmountPending); 
                } 
            } catch (error) { 
                console.error("Error fetching loan data:", error); 
            } 
        }; 
        fetchUpdatedLoanData(); 
    }, [loanId]); 
 
    //Calculate EMI and update repayment details when loan data changes. 
    useEffect(() => { 
        if (loanData) { 
            const monthlyInterestRate = loanData.loanInterestRate / (12 * 100); 
            const monthlyPayment = (loanData.loanAmountPrinciple * monthlyInterestRate) / 
                (1 - Math.pow(1 + monthlyInterestRate, -loanData.loanTenure)); 
 
            let latestPendingAmount = loanData.loanAmountPending; 
            let monthlyInterest = latestPendingAmount * monthlyInterestRate; 
            let principalPaid = monthlyPayment - monthlyInterest; 
            let newTotalPendingAmount = latestPendingAmount - monthlyPayment; 
            if (newTotalPendingAmount <= 0) newTotalPendingAmount = 0; 
 
            setNextRepaymentDate(format(new Date(loanData.dueDate).toISOString().split("T")[0], 'yyyy-MM-dd')); 
            setEmi(monthlyPayment.toFixed(2)); 
            setTotalPendingAmount(newTotalPendingAmount); 
 
            setPaymentDetails({ 
                installmentDate: nextRepaymentDate, 
                openingBalance: latestPendingAmount.toFixed(2), 
                principalPaid: principalPaid.toFixed(2), 
                monthlyInterest: monthlyInterest.toFixed(2), 
                monthlyPayment: monthlyPayment.toFixed(2), 
                totalPendingAmount: newTotalPendingAmount.toFixed(2), 
            }); 
        } 
    }, [loanData]); 
 
    //Generate a unique repayment ID based on loan ID. 
    const generateRepaymentId = async () => { 
        try { 
            const latestRepaymentId = await fetchLatestRepaymentId(loanData.loanId); 
            let nextId = 1; 
 
            if (latestRepaymentId) { 
                const lastNumber = parseInt(latestRepaymentId.replace(loanData.loanId + "R", ""), 10); 
                nextId = lastNumber + 1; 
            } 
 
            const newRepaymentId = `${loanData.loanId}R${nextId}`; 
            return newRepaymentId; 
        } catch (error) { 
            console.error("Error generating repayment ID:", error); 
            return `${loanData.loanId}R1`; 
        } 
    }; 
 
    //Handle repayment submission, update loan status, and navigate to dashboard. 
    const handleSubmit = async (e) => { 
        e.preventDefault(); 
        try { 
            const monthlyInterestRate = loanData.loanInterestRate / (12 * 100); 
            let monthlyInterest = totalPendingAmount * monthlyInterestRate; 
            let principalPaid = emi - monthlyInterest; 
 
            setTotalPendingAmount(prev => { 
                const newTotalPendingAmount = prev - emi; 
                return newTotalPendingAmount <= 0 ? 0 : newTotalPendingAmount; 
            }); 
 
            let newNextRepaymentDate = format(addMonths(new Date(nextRepaymentDate), 1), 'yyyy-MM-dd'); 
            let loanStatus = totalPendingAmount === 0 ? "Closed" : "Open"; 
 
            setPaymentDetails(prev => ({ 
                ...prev, 
                installmentDate: newNextRepaymentDate, 
                openingBalance: prev.totalPendingAmount, 
                principalPaid: principalPaid.toFixed(2), 
                monthlyInterest: monthlyInterest.toFixed(2), 
                monthlyPayment: emi, 
                totalPendingAmount: totalPendingAmount.toFixed(2), 
            })); 
 
            setNextRepaymentDate(newNextRepaymentDate); 
 
            const repaymentId = await generateRepaymentId(); 
            const repaymentData = { 
                repaymentId: repaymentId, 
                repaymentInstallmentAmount: emi, 
                repaymentStatus: "Paid", 
                loanStatus: totalPendingAmount === 0 ? "Closed" : "Open", 
                repaymentPaidDate: new Date().toISOString(), 
                repaymentLoanId: loanData.loanId, 
 
 
            }; 
 
            await postRepayment(repaymentData); 
            await updateDueDate(loanData.loanId, newNextRepaymentDate, totalPendingAmount, loanStatus); 
 
            alert("Repayment done successfully"); 
            navigate('/dashboard'); 
        } catch (err) { 
            console.error("Repayment Error:", err); 
            alert("Something went wrong during repayment."); 
        } 
    }; 
 
    return ( 
        <div> 
            <Header /> 
            <div className="d-flex flex-row w-100"> 
                <VerticleNavbar /> 
                <div className="outer-box w-100" style={{ marginLeft: '15rem', width: 'calc(100% - 15rem)' }}> 
                    <div className="p-4"> 
                        <div className="card shadow p-4"> 
                            <h2 className="mb-4 text-center">Repayment Details</h2> 
                            <div className="table-responsive"> 
                                <table className="table table-bordered table-striped table-hover"> 
                                    <thead className="bg-primary text-white text-center"> 
                                        <tr> 
                                            <th>Installment Date</th> 
                                            <th>Total Opening Balance (P+I)</th> 
                                            <th>Principal Amount</th> 
                                            <th>Interest Amount</th> 
                                            <th>Monthly Payment</th> 
                                            <th>Total Pending Amount (P+I)</th> 
                                        </tr> 
                                    </thead> 
                                    <tbody className="text-center"> 
                                        <tr> 
                                            {Object.values(paymentDetails).map((value, index) => ( 
                                                index === 0 ? 
                                                    (<td key={index} className="border p-3">{value ? format(new Date(value), localStorage.getItem("dateFormat")) : "YYYY/MM/DD"}</td>) : 
                                                    (<td key={index} className="border p-3">{value || "N/A"}</td>) 
                                            ))} 
                                        </tr> 
                                    </tbody> 
                                </table> 
                            </div> 
                             
                            <div className="d-flex justify-content-center mt-3 mx-5 px-5"> 
                                <button type="primary" className="btn btn-success mx-5" onClick={handleSubmit}>Repay</button> 
                                <button type="primary" className="btn btn-secondary mx-5" onClick={() => navigate('/dashboard')}>Cancel</button> 
                            </div> 
                        </div> 
                    </div> 
                    <div className="p-4"> 
                        <RepaymentHistory loanId={loanId} /> 
                    </div> 
                </div> 
            </div> 
        </div> 
    ); 
} 
 
export default CalculateRepayment;