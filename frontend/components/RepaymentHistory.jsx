import React, { useState, useEffect } from 'react'; 
import { fetchRepaymentHistory } from "../api/Repaymentapi"; 
import { format } from 'date-fns'; 
import jsPDF from "jspdf"; 
import autoTable from "jspdf-autotable"; 
 
 
/* 
    RepaymentHistory Component 
    This React component fetches and displays the repayment history of a loan, including 
    installment payments, repayment status, and loan status. Users can also download the  
    repayment history as a PDF document. 
*/ 
 
function RepaymentHistory({ loanId }) { 
    // State to store repayment history data 
    const [repayments, setRepayments] = useState([]); 
 
    // Fetch repayment history when loanId changes 
    useEffect(() => { 
        const loadData = async () => { 
            if (loanId) { // Ensure loanId is available 
                const data = await fetchRepaymentHistory(loanId); 
                setRepayments(data); 
            } 
        }; 
        loadData(); 
    }, [loanId]); 
 
 
    // Function to Download Repayment History as PDF 
    const downloadRepaymentHistoryPDF = () => { 
        const doc = new jsPDF(); 
        doc.text("Repayment History", 14, 16); 
 
        autoTable(doc, { 
            startY: 20, 
            head: [["Repayment ID", "Loan ID", "Repayment Date", "Installment Amount", "Repayment Status", "Loan Status"]], 
            body: repayments.map(repayment => [ 
                repayment.repaymentId, 
                repayment.repaymentLoanId, 
                new Date(repayment.repaymentPaidDate).toLocaleDateString(), 
                repayment.repaymentInstallmentAmount, 
                repayment.repaymentStatus, 
                repayment.loanStatus, 
            ]) 
        }); 
 
        // Save the PDF file 
        doc.save(`Repayment_History${format(new Date(), "yyyyMMdd")}.pdf`); 
    }; 
 
    return ( 
        <div className="card shadow p-4 mt-4" style={{ width: '100%' }}> 
 
            <h2 className="mb-4 text-center">Repayment History</h2> 
            <div className="d-flex mb-4" style={{ width: '25%' }}> 
                {/* Button to download repayment history as PDF */} 
                <button className="btn btn-warning" onClick={downloadRepaymentHistoryPDF}> 
                    Download PDF 
                </button> 
            </div> 
 
            <div className="table-responsive"> 
                {/* Table displaying repayment history */} 
                <table className="table table-bordered table-striped table-hover"> 
                    <thead className="bg-secondary text-white text-center"> 
                        <tr> 
                            <th className="p-3">Repayment ID</th> 
                            <th className="p-3">Loan ID</th> 
                            <th className="p-3">Paid Date</th> 
                            <th className="p-3">Installment Amount</th> 
                            <th className="p-3">Repayment Status</th> 
                            <th className="p-3">Loan Status</th> 
 
 
                        </tr> 
                    </thead> 
                    <tbody className="text-center"> 
                        {repayments.length > 0 ? ( 
                            repayments.map((repayment) => ( 
                                <tr key={repayment.repaymentId}> 
                                    <td className="border p-3">{repayment.repaymentId}</td> 
                                    <td className="border p-3">{repayment.repaymentLoanId}</td> 
                                    <td className="border p-3">{format(new Date(repayment.repaymentPaidDate).toLocaleDateString(), localStorage.getItem("dateFormat"))}</td> 
                                    <td className="border p-3">{repayment.repaymentInstallmentAmount}</td> 
                                    <td className="border p-3">{repayment.repaymentStatus}</td> 
                                    <td className="border p-3">{repayment.loanStatus}</td> 
 
 
                                </tr> 
                            )) 
                        ) : ( 
                            <tr> 
                                <td colSpan="6" className="border p-3 text-center">No Repayment History Found</td> 
                            </tr> 
                        )} 
                    </tbody> 
                </table> 
            </div> 
            {/* <Footer/> */} 
        </div> 
    ); 
} 
 
export default RepaymentHistory;