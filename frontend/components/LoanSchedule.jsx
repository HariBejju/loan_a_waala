import { useState, useEffect } from "react"; 
import { format } from "date-fns"; 
import ChartSection from "./ChartSection"; 
import jsPDF from "jspdf"; 
import autoTable from "jspdf-autotable"; 
import html2canvas from "html2canvas"; 
import "../styles/LoanSchedule.css"; 
 
/* 
    LoanSchedule calculates and displays a loan repayment schedule with monthly installments, 
    interest breakdowns, and generates a downloadable PDF report. 
*/ 
 
function LoanSchedule({ principalAmount, annualInterestRate, tenure, startDate }) { 
 
     //  State management 
    const [schedule, setSchedule] = useState([]); 
    const [error, setError] = useState(null); 
    const [totalInterestPaid, setTotalInterestPaid] = useState(0); 
    const [monthlyInstallment, setMonthlyInstallment] = useState(0); 
    const [totalRepayments, setTotalRepayments] = useState(0); 
 
    useEffect(() => { 
        try { 
            const monthlyInterestRate = annualInterestRate / (12 * 100); 
            const monthlyPayment = (principalAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -tenure)); 
            const totalPaymentToBePaid = monthlyPayment * tenure; 
            let remainingLoanAmount = principalAmount; 
            let totalInterest = 0; 
            let installmentDate = new Date(startDate); 
            let tempSchedule = []; 
 
            for (let installmentNumber = 1; installmentNumber <= tenure; installmentNumber++) { 
                let monthlyInterest = remainingLoanAmount * monthlyInterestRate; 
                let principal = monthlyPayment - monthlyInterest; 
                let openingBalance = remainingLoanAmount; 
 
                remainingLoanAmount -= principal; 
 
                if (remainingLoanAmount < 0) { 
                    principal += remainingLoanAmount; 
                    remainingLoanAmount = 0; 
                } 
                totalInterest += monthlyInterest; 
                let installment = { 
                    installmentNumber, 
                    installmentDate: installmentDate.toLocaleDateString(), 
                    openingBalance: openingBalance.toFixed(2), 
                    principal: principal.toFixed(2), 
                    monthlyInterest: monthlyInterest.toFixed(2), 
                    monthlyPayment: monthlyPayment.toFixed(2), 
                    remainingLoanAmount: remainingLoanAmount.toFixed(2), 
                }; 
 
                tempSchedule.push(installment); 
                installmentDate.setMonth(installmentDate.getMonth() + 1); 
 
                if (remainingLoanAmount <= 0) break; 
            } 
 
            setTotalInterestPaid(totalInterest.toFixed(2)); 
            setMonthlyInstallment(monthlyPayment.toFixed(2)); 
            setTotalRepayments(totalPaymentToBePaid.toFixed(2)); 
            setSchedule(tempSchedule); 
        } catch (err) { 
            setError("Failed to generate schedule."); 
        } 
    }, [principalAmount, annualInterestRate, tenure, startDate]); 
 
     // Function to generate PDF with Pie Chart 
    const downloadPDF = async () => { 
        const doc = new jsPDF(); 
        doc.text("Loan Repayment Schedule", 14, 16); 
 
        const chartElement = document.getElementById("chart-section"); 
        if (chartElement) { 
            const canvas = await html2canvas(chartElement); 
            const imgData = canvas.toDataURL("image/png"); 
 
            doc.addImage(imgData, "PNG", 15, 30, 180, 60);  
        } 
 
        autoTable(doc, { 
            startY: 110, 
            head: [["#", "Date", "Opening Balance", "Principal", "Interest", "Monthly Payment", "Remaining Balance"]], 
            body: schedule.map(s => [ 
                s.installmentNumber, 
                s.installmentDate, 
                s.openingBalance, 
                s.principal, 
                s.monthlyInterest, 
                s.monthlyPayment, 
                s.remainingLoanAmount 
            ]) 
        }); 
 
        doc.save(`LoanSchedule${format(new Date(), "yyyyMMdd")}.pdf`); 
    }; 
 
    return ( 
        <div> 
            {error && <p className="text-danger">{error}</p>} 
 
            <section id="loan-detail-cards"> 
                <div className="row align-items-end"> 
                    <div className="col pb-3"> 
                        <div className="card"> 
                            <div className="card-header"><h4>Total Estimated Interest</h4></div> 
                            <div className="card-body"><p className="h5">{totalInterestPaid}</p></div> 
                        </div> 
                    </div> 
                    <div className="col pb-3"> 
                        <div className="card"> 
                            <div className="card-header"><h4>Monthly Installment</h4></div> 
                            <div className="card-body"><p className="h5">{monthlyInstallment}</p></div> 
                        </div> 
                    </div> 
                    <div className="col pb-3"> 
                        <div className="card"> 
                            <div className="card-header"><h4>Total Payment Amount</h4></div> 
                            <div className="card-body"><p className="h5">{totalRepayments}</p></div> 
                        </div> 
                    </div> 
                </div> 
            </section> 
 
            {/* Download PDF Button - Moved to Top */} 
        <div className="d-flex mb-4" style={{width: '25%'}}> 
            <button onClick={downloadPDF} className="btn btn-warning">Download PDF</button> 
        </div> 
 
            <div id="chart-section"> 
                <ChartSection 
                    principal={principalAmount} 
                    totalInterest={totalRepayments - principalAmount} 
                    schedule={schedule} 
                /> 
            </div> 
 
            <table className="table table-bordered table-striped mt-3"> 
                <thead> 
                    <tr> 
                        <th>#</th> 
                        <th>Installment Date</th> 
                        <th>Opening Principal Balance</th> 
                        <th>Installment Principal Amount</th> 
                        <th>Monthly Interest Amount</th> 
                        <th>Monthly Payment</th> 
                        <th>Remaining Principal Balance</th> 
                    </tr> 
                </thead> 
                <tbody> 
                    {schedule.length > 0 ? ( 
                        schedule.map((s) => ( 
                            <tr key={s.installmentNumber}> 
                                <td>{s.installmentNumber}</td> 
                                <td>{s.installmentDate}</td> 
                                <td>{s.openingBalance}</td> 
                                <td>{s.principal}</td> 
                                <td>{s.monthlyInterest}</td> 
                                <td>{s.monthlyPayment}</td> 
                                <td>{s.remainingLoanAmount}</td> 
                            </tr> 
                        )) 
                    ) : ( 
                        <tr> 
                            <td colSpan="7" style={{ textAlign: "center" }}>No schedule data available.</td> 
                        </tr> 
                    )} 
                </tbody> 
            </table> 
 
             
        </div> 
    ); 
} 
 
export default LoanSchedule;