import React, { useState, useEffect } from 'react'; 
import axios from 'axios'; 
import { useLocation } from 'react-router-dom'; 
import VerticleNavbar from './VerticleNavbar'; 
import Header from './Header'; 
import "../styles/AddLoan.css"; 
import * as Icons from 'react-bootstrap-icons'; 
 
const AddLoan = () => { 
  //  Retrieve user details from local storage 
  const token = localStorage.getItem("token") 
  const panCard = localStorage.getItem("panCard") 
  const userId = localStorage.getItem("userId") 
 
  //  State management 
  const [updated, setUpdated] = useState("") 
  const [loans, setLoans] = useState([]); 
  const [sucess, setSuccess] = useState('') 
  const [hasLoan, setHasLoan] = useState("Yes"); 
  const [successMessage, setSuccessMessage] = useState(""); 
 
  // Masking the first five characters 
  const maskedPanCard = panCard ? panCard.replace(/^.{5}/, "XXXXX") : "Not available"; 
 
  //  Fetch loans associated with the PAN card 
  const handleSubmit = async () => { 
    try { 
      const response = await axios.get(`http://localhost:8090/api/loanUser/${panCard}`, { 
        headers: { Authorization: `Bearer ${token}` }, 
      }); 
      setLoans(response.data); 
    } catch (err) { 
      setHasLoan("No");// If no loans found, update state 
      setSuccess(null); 
    } 
  }; 
 
  //  Add loan for the user 
  const handleButtonClick = async (loanId) => { 
    const selectedLoan = loans.find(loan => loan.loanId === loanId); 
 
    if (selectedLoan) { 
      const { loanId, 
        loanLenderId, 
        loanName, 
        loanType, 
        loanTotalAmount, 
        loanAmountPrinciple, 
        loanAmountPending, 
        loanInterestRate, 
        loanTenure, 
        loanStartDate, 
        loanEstimatedEndDate, 
        loanActualEndDate, 
        loanStatus, 
        dueDate } = selectedLoan 
      try { 
        const payLoad = { 
          loanId, 
          loanUserId: userId, 
          loanLenderId, 
          loanName, 
          loanType, 
          loanTotalAmount, 
          loanAmountPrinciple, 
          loanAmountPending, 
          loanInterestRate, 
          loanTenure, 
          loanStartDate, 
          loanEstimatedEndDate, 
          loanActualEndDate, 
          loanStatus, 
          dueDate 
        } 
 
        //  Send loan addition request 
        const res = await axios.post('http://localhost:8090/api/loans', payLoad, { 
          headers: { Authorization: `Bearer ${token}` }, 
          "Content-Type": "application/json", 
          Accept: "application/json", 
        }); 
        setHasLoan("yes"); 
        setSuccessMessage("Loan successfully added!"); 
 
      } catch (err) { 
        if (err.response && err.response.status === 404) { 
          setHasLoan("No"); 
        } 
      } 
 
      //  Update loan display status in database 
      try { 
        const res = await axios.patch(`http://localhost:8090/api/loanUser/${loanId}`, { "loan_display_status": "Added" }, { 
          headers: { Authorization: `Bearer ${token}` } 
        }) 
        if (updated == "") { 
          setUpdated("added") 
        } else { 
          setUpdated("") 
        } 
      } catch (err) { 
        console.log(err) 
      } 
    } else { 
      console.error("Loan not found!"); 
    } 
  }; 
 
  //  Fetch loans on component mount or when `updated` changes 
  useEffect(() => { 
    handleSubmit(); 
  }, [updated]); 
 
 
  //  Show success message when a loan is added 
  useEffect(() => { 
    if (successMessage) { 
      alert(successMessage); 
      setSuccessMessage(""); // Clears the message after showing the alert 
    } 
  }, [successMessage]); 
 
  return ( 
    <div> 
      <Header /> 
      <div className='d-flex flex-row w-100'> 
        <VerticleNavbar /> 
        {/* Loan details section */} 
        <div className="container-fluid p-5 outer-box" style={{ marginLeft: '15rem', width: 'calc(100% - 15rem)' }}> 
          <h2 className='ms-5'>Loans taken under Pan Card Number:{maskedPanCard}</h2> 
 
          {/*  No loans found */} 
          {hasLoan === "No" ? ( 
            <div className="text-center mt-3"> 
              <h4>No loan found .</h4> 
            </div> 
          ) : ( 
            <div> 
              {/* Loan details */} 
              {loans.length > 0 && ( 
                <div className='add-loan-box'> 
                  <div className='loan-id'>Loan ID</div> 
                  <div className='loan-name'>Loan Name</div> 
                  <div className='loan-amount'>Loan Amount</div> 
                  <div className='loan-pending'>Loan Pending</div> 
                  <div className='loan-interest'>Loan Interest</div> 
                  <div className='loan-add'>Add Loan</div> 
                </div> 
              )} 
              <hr /> 
              {/*  Display loans available for addition */} 
              <div> 
                {loans.filter((loan) => loan.loanDisplayStatus === "No").map((loan) => ( 
                  <div className="add-loan-inner-box"> 
                    <div className="icons"> {loan.loanId}</div> 
                    <div className="loan-name"> {loan.loanName}</div> 
                    <div className="loan-amount">{loan.loanTotalAmount} </div> 
                    <div className="loan-amount-pending">{loan.loanAmountPending}</div> 
                    <div className="loan-interest">{loan.loanInterestRate} </div> 
                    <div className="loan-add"><button className='btn btn-success' onClick={() => { handleButtonClick(loan.loanId) }}><Icons.Plus /></button></div> 
                  </div> 
                ))} 
              </div> 
            </div> 
          )} 
        </div> 
      </div> 
    </div> 
  ); 
}; 
 
export default AddLoan;