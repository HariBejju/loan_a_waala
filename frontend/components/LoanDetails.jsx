import React, { useState, useEffect } from "react"; 
import { useLocation } from "react-router-dom"; 
import { useNavigate } from "react-router-dom"; 
import { format } from "date-fns"; 
import LoanSchedule from "./LoanSchedule"; 
import Header from "./Header"; 
import VerticalNavbar from "./VerticleNavbar"; 
import axios from "axios"; 
 
/* 
 * LoanDetails Component 
 * This component retrieves and displays details of a loan using its ID. 
 * It also provides functionality to repay the loan and view the repayment schedule. 
 */ 
 
function LoanDetails() { 
 
    const location = useLocation(); 
    const navigate = useNavigate(); 
    const { loanId } = location.state || {}; 
    const userId = localStorage.getItem("userId") 
    const token = localStorage.getItem("token") 
   
     
 
    // State variables to store loan data 
    const [loanData, setLoanData] = useState(null); 
    // State to store loan details 
    const [loanDetails, setLoanDetails] = useState({ 
        loanId: loanId, 
        loanUserId: userId, 
        loanLenderId: '', 
        loanName: '', 
        loanType: '', 
        loanTotalAmount: '', 
        loanAmountPrinciple: '', 
        loanAmountPending: '', 
        loanInterestRate: '', 
        loanTenure: '', 
        loanStartDate: '', 
        loanEstimatedEndDate: '', 
        loanActualEndDate: '', 
        loanStatus: '', 
        dueDate: '' 
    }); 
 
    //Fetch loan details from the backend based on loanId. 
    const fetchLoanData = async () => { 
        try { 
            const response = await axios.get(`http://localhost:8090/api/loans/loan-details/${loanId}`, { 
                headers: { Authorization: `Bearer ${token}` } 
            }); 
 
            // Store the fetched loan details in state 
            setLoanData(response.data); 
            setLoanDetails(prevDetails => ({ 
                ...prevDetails, 
                ...response.data 
            })); 
        } 
        catch (error) { 
            console.log(error); 
        } 
    } 
 
    //Handles repayment action by navigating to the repayment page. 
    const handleRepayment = () => { 
        navigate('/calculate-repayment', { state: { loanId } }); 
    }; 
 
    useEffect(() => { 
        fetchLoanData(); 
    }, []); 
 
    useEffect(() => { 
    }, [loanDetails]); 
 
 
    //Updates loan details state when an input field changes. 
    const handleInputChange = (e) => { 
        const { name, value } = e.target; // Get the input name and value 
        setLoanDetails((prevDetails) => ({ 
            ...prevDetails, 
            [name]: value // Update only the relevant field dynamically 
        })); 
        console.log(loanData) 
    }; 
 
    return ( 
        <div> 
            <Header /> 
            <div className="d-flex flex-row w-100 mb-5"> 
                <VerticalNavbar /> 
 
                <div className="container-fluid" style={{ marginLeft: '15rem', width: 'calc(100% - 15rem)', backgroundColor: '#f1f5f9' }}> 
                    <h2 className="row ms-5 mt-5">Loan Details</h2> 
                    <div className="col-7 col-md-3 d-flex flex-column p-10"> 
                        {/* Button to repay loan, disabled if pending amount is zero */} 
                        <button 
                            id="repay-loan-button" 
                            className="btn btn-primary mt-3" 
                            onClick={handleRepayment} 
                            disabled={(loanDetails.loanAmountPending == 0)} 
                        > 
                            Repay Loan 
                        </button> 
 
                    </div> 
 
                    {loanData ? ( 
                        <div> 
                            {/* Display loan details */} 
                            <div className="row d-flex flex-wrap p-md-3"> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Loan Id</label> 
                                    <input className="form-control" name="loanId" id="loan-id" type="text" value={loanData.loanId} readOnly /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Loan Lender Id</label> 
                                    <input className="form-control" name="loanLenderId" id="loan-lender-id" type="text" value={loanData.loanLenderId} readOnly onChange={handleInputChange}></input> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Loan Name</label> 
                                    <input className="form-control" name="loanName" id="loan-name" type="text" value={loanData.loanName} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Loan Type</label> 
                                    <input className="form-control" name="loanType" id="loan-type" type="text" value={loanData.loanType} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Total Loan Amount</label> 
                                    <input className="form-control" name="loanTotalAmount" id="loan-amount-total" type="number" value={loanData.loanTotalAmount} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Principal Amount</label> 
                                    <input className="form-control" name=" loanAmountPrinciple" id="loan-amount-principal" type="number" value={loanData.loanAmountPrinciple} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Pending Amount</label> 
                                    <input className="form-control" name="loanAmountPending" id="loan-pending-amount" type="number" value={Number(loanData.loanAmountPending).toFixed(2)} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Interest Rate</label> 
                                    <input className="form-control" name="loanInterestRate" id="loan-interest-rate" type="number" value={Number(loanData.loanInterestRate).toFixed(2)} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Tenure (Months)</label> 
                                    <input className="form-control" name="loanTenure" id="loan-tenure" type="number" value={loanData.loanTenure} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Start Date</label> 
                                    <input className="form-control" name="loanStartDate" id="loan-start-date" type="text" value={format(loanData.loanStartDate, localStorage.getItem("dateFormat"))} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Estimated End Date</label> 
                                    <input className="form-control" name="loanEstimatedEndDate" id="loan-estimated-end-date" type="text" value={format(loanData.loanEstimatedEndDate, localStorage.getItem("dateFormat"))} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>DueDate</label> 
                                    <input className="form-control" name="dueDate" id="dueDate" type="text" value={loanData.dueDate ? format(new Date(loanData.dueDate).toLocaleDateString(), localStorage.getItem("dateFormat")) : ""} readOnly onChange={handleInputChange} /> 
                                </div> 
                                <div className="col-12 col-md-3 d-flex flex-column p-2"> 
                                    <label>Status</label> 
                                    <input className="form-control" name="loanStatus" id="loan-status" type="text" value={loanData.loanStatus} readOnly onChange={handleInputChange} /> 
                                </div> 
                            </div> 
 
                            {/* Display loan repayment schedule */} 
                            <LoanSchedule principalAmount={loanData.loanAmountPrinciple} annualInterestRate={loanData.loanInterestRate} tenure={loanData.loanTenure} startDate={loanData.loanStartDate} /> 
                        </div> 
                    ) : ( 
                        <p>Loading...</p> 
                    )} 
                </div> 
            </div> 
        </div> 
    ) 
} 
 
export default LoanDetails;