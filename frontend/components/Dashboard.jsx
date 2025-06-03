import { useState, useEffect, useRef } from "react"; 
import { fetchLoansByUserId } from "../api/Loanapi"; 
import { Select } from "antd"; 
import { Link } from 'react-router-dom'; 
import { fetchNotificationCount } from "../api/NotificationCountapi"; 
import { format } from "date-fns"; 
import Header from "./Header"; 
import DisplayCards from "./DisplayCards"; 
import VerticleNavbar from "./VerticleNavbar"; 
import "../styles/Dashboard.css"; 
 
function Dashboard() { 
  //  State variables for loan data and UI controls 
  const [loans, setLoans] = useState([]); 
  const [error, setError] = useState(null); 
  const [hasLoan, setHasLoan] = useState('Yes'); 
  const [totalActiveLoans, setTotalActiveLoans] = useState(0); 
  const [totalPendingAmount, setTotalPendingAmount] = useState(0); 
  const [dueDate, setDueDate] = useState(null); 
  const hasFetchedNotification = useRef(false); 
 
  const userId = localStorage.getItem('userId'); 
  const token = localStorage.getItem('token'); 
 
  //  Function to calculate the total number of active loans 
  const calculateActiveLoans = (fetchedLoans) => { 
    let value = 0; 
    fetchedLoans.forEach((loan) => { 
      if (loan.loanStatus == "Open") { 
        value = value + 1 
      } 
    }) 
    setTotalActiveLoans(value) 
    setError(null); 
  } 
 
  //  Function to calculate the total pending amount for all loans 
  const calculateTotalPendingAmount = (fetchedLoans) => { 
    let value = 0; 
    fetchedLoans.forEach((loan) => { 
      value = value + loan.loanAmountPending; 
      setTotalPendingAmount(value.toFixed(2)); 
    }) 
  } 
 
  //  Fetch loans for the logged-in user 
  const handleSearch = async () => { 
    try { 
      const fetchedLoans = await fetchLoansByUserId(userId, token); 
      if (fetchedLoans === "No") { 
        setError("No loans found for this User"); 
        setLoans([]); 
        setDueDate(null); 
        setHasLoan("No"); 
        return; 
      } 
      setHasLoan("Yes"); 
 
      // Filter only 'Open' loans and sort by due date 
      const fetchedOpenLoans = fetchedLoans.filter(loan => loan.loanStatus === 'Open'); 
      const sortedLoans = fetchedOpenLoans.sort((a, b) => new Date(a.dueDate) - new Date(b.dueDate)); 
 
      setDueDate(sortedLoans.length > 0 ? sortedLoans[0].dueDate : null); 
      setLoans(fetchedLoans);  // Show all loans (both open and closed) 
 
      // But calculate values only from open loans 
      calculateActiveLoans(fetchedOpenLoans); 
      calculateTotalPendingAmount(fetchedOpenLoans); 
    } catch (err) { 
      console.log(err) 
      setError("Loans not found for this User ID"); 
    } 
  }; 
 
  //  Sort loans based on the selected filter criteria 
  const handleFilter = (selectedFilter) => { 
    if (!selectedFilter) return; 
 
    let sortedLoans = [...loans]; 
 
    if (selectedFilter === "interestRate") { 
      sortedLoans.sort((a, b) => b.loanInterestRate - a.loanInterestRate); // Highest interest rate first 
    } else if (selectedFilter === "dueDate") { 
      sortedLoans.sort((a, b) => new Date(a.dueDate || 0) - new Date(b.dueDate || 0)); // Earliest due date first, handling missing dates 
    } else if (selectedFilter === "principalAmount") { 
      sortedLoans.sort((a, b) => b.loanAmountPrinciple - a.loanAmountPrinciple); // Highest principal amount first 
    } 
    setLoans(sortedLoans); 
  }; 
 
  //  Fetch data when the component mounts (once) 
  useEffect(() => { 
    handleSearch(); 
    if ( 
      !userId || 
      sessionStorage.getItem("notificationAlertShown") === "true" || 
      hasFetchedNotification.current 
    ) { 
      console.log("im the problem") 
      return; 
    } 
 
    const getCount = async () => { 
      try { 
        const count = await fetchNotificationCount(userId); // await replaces .then() 
 
        if (count > 0) { 
          setTimeout(() => { 
            alert(`You have ${count} notification${count > 1 ? "s" : ""}!`); 
            sessionStorage.setItem("notificationAlertShown", "true"); 
            hasFetchedNotification.current = true; 
          }, 500); 
        } 
      } catch (err) { 
        console.error("Failed to fetch notification count", err); 
      } 
    }; 
 
    if (!hasFetchedNotification.current) { 
      hasFetchedNotification.current = true; 
      getCount(); 
 
    } 
  }, [userId]); 
 
  return ( 
    <div className="mb-5"> 
      <Header /> 
      <div className="d-flex flex-row w-100"> 
        <VerticleNavbar /> 
 
        <div className="outer-box w-100" style={{ marginLeft: '15rem', width: 'calc(100% - 15rem)' }}> 
          {/*  Display loan-related summary cards */} 
          <div className="d-flex flex-row justify-content-around"> 
            <DisplayCards msg="Total Active Loans" value={totalActiveLoans} icon="Bank2" color="#ffffed" /> 
            <DisplayCards msg="Total Pending Amount" value={"₹" + totalPendingAmount} icon="CashStack" color="#fde3ff" /> 
            <DisplayCards msg="Upcoming Due Date" value={dueDate ? dueDate.substring(0, 10) : "No due date available"} icon="CalendarDate" color="#e0f9f7" /> 
          </div> 
 
          <div className="container mt-5"> 
            <h3 className="text-left p-2">Loans Overview</h3> 
 
            {hasLoan === "No" ? ( 
              <div className="text-center mt-3"> 
                <h4>No loans added.</h4> 
              </div> 
            ) : ( 
              <> 
                <ul className="nav nav-tabs" id="loanTabs" role="tablist"> 
                  <li className="nav-item" role="presentation"> 
                    <button className="nav-link active" id="pending-tab" data-bs-toggle="tab" data-bs-target="#pending" type="button" role="tab" aria-controls="pending" aria-selected="true"> 
                      Pending Loans 
                    </button> 
                  </li> 
                  <li className="nav-item" role="presentation"> 
                    <button className="nav-link" id="completed-tab" data-bs-toggle="tab" data-bs-target="#completed" type="button" role="tab" aria-controls="completed" aria-selected="false"> 
                      Completed Loans 
                    </button> 
                  </li> 
                  {/*  Loan filter dropdown */} 
                  <li className="ms-auto"> 
                    <Select 
                      defaultValue="" 
                      style={{ width: 200 }} 
                      onChange={(value) => handleFilter(value)} // Now passing `value` directly 
                    > 
                      <Select.Option value="">Sort</Select.Option> 
                      <Select.Option value="interestRate">Interest Rate</Select.Option> 
                      <Select.Option value="dueDate">Due Date</Select.Option> 
                      <Select.Option value="principalAmount">Principal Amount</Select.Option> 
                    </Select> 
                  </li> 
                </ul> 
 
                {/*  Loan details (Pending & Completed loans) */} 
                <div className="tab-content" id="loanTabsContent" style={{ width: '30em' }}> 
                  <div className="tab-pane fade show active" id="pending" role="tabpanel" aria-labelledby="pending-tab"> 
                    <div className="loan-box"> 
                      <div className="loan-inner-box1"> 
                        {/*  Pending Loans */} 
                        <div className="loan-id">Loan ID</div> 
                        <div className="loan-name">Loan Name</div> 
                        <div className="loan-amount">Loan amount</div> 
                        <div className="loan-amount-pending">Pending</div> 
                        <div className="loan-interest">Interest</div> 
                        <div className="due-date">Due Date</div> 
                      </div> 
 
                      {loans.filter((loan) => 
 
                        loan.loanStatus === "Open").map((loan) => ( 
                          <Link to="/loan-details" state={{ loanId: loan.loanId }} key={loan.loanId} style={{ color: "black", textDecoration: "none" }}> 
                            <div className="loan-inner-box hover-effect"> 
                              <div className="loan-id">{loan.loanId}</div> 
                              <div className="loan-name">{loan.loanName}</div> 
                              <div className="loan-amount">{Number(loan.loanTotalAmount).toFixed(2)}</div> 
                              <div className="loan-amount-pending">{Number(loan.loanAmountPending).toFixed(2)}</div> 
                              <div className="loan-interest">{loan.loanInterestRate}%</div> 
                              <div className="due-date">{format(loan.dueDate, localStorage.getItem("dateFormat"))}</div> 
                            </div> 
                          </Link> 
                        ))} 
                      {console.log(loans)} 
                    </div> 
                  </div> 
 
                  <div className="tab-pane fade" id="completed" role="tabpanel" aria-labelledby="completed-tab"> 
                    <div className="loan-box"> 
                      <div className="loan-inner-box1"> 
                        {/*  Closed Loans */} 
                        <div className="loan-id">Loan ID</div> 
                        <div className="loan-name">Loan Name</div> 
                        <div className="loan-amount">Loan amount</div> 
                        <div className="loan-amount-pending">Pending</div> 
                        <div className="loan-interest">Interest</div> 
                        <div className="due-date">Due Date</div> 
                      </div> 
 
                      {loans.filter((loan) => loan.loanStatus === "Closed").map((loan) => ( 
                        <Link to="/loan-details" state={{ loanId: loan.loanId }} key={loan.loanId} style={{ color: "black", textDecoration: "none" }}> 
                          <div className="loan-inner-box hover-effect"> 
                            <div className="loan-id">{loan.loanId}</div> 
                            <div className="loan-name">{loan.loanName}</div> 
                            <div className="loan-amount">{Number(loan.loanTotalAmount).toFixed(2)}</div> 
                            <div className="loan-amount-pending">{Number(loan.loanAmountPending).toFixed(2)}</div> 
                            <div className="loan-interest">{loan.loanInterestRate}%</div> 
                            <div className="due-date">{loan.dueDate}</div> 
                          </div> 
                        </Link> 
                      ))} 
                    </div> 
                  </div> 
                </div> 
              </> 
            )} 
          </div> 
        </div> 
      </div> 
    </div> 
  ); 
} 
 
export default Dashboard;