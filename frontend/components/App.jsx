import reactLogo from './assets/react.svg' 
import viteLogo from '/vite.svg' 
import {BrowserRouter as Router,Route, Routes } from "react-router-dom"  
import Login from './components/Login' 
import Signup from './components/Signup' 
import Dashboard from './components/Dashboard' 
import Header from './components/Header' 
import AddLoan from './components/AddLoan' 
import CalculateRepayment from './components/CalculateRepayment' 
import Profile from './components/Profile' 
import PrivateRoute from './components/PrivateRoute' 
import LoanDetails from './components/LoanDetails' 
import Notifications from './components/Notifications' 
import ActionHistory from './components/ActionHistory' 
import Homepage from './page/Homepage' 
function App() { 
   
 
  return ( 
    <> 
    <Router> 
      <Routes> 
        <Route exact path='/login'  element={<Login/>} /> 
        <Route exact path='/'  element={<Homepage/>} /> 
        <Route exact path='/signup'  element={<Signup/>} />  
        <Route exact path='/dashboard'  element={<PrivateRoute><Dashboard/></PrivateRoute>} /> 
        <Route exact path='/add-loan'  element={<PrivateRoute><AddLoan/></PrivateRoute>} /> 
        <Route exact path='/calculate-repayment'  element={<PrivateRoute><CalculateRepayment/></PrivateRoute>} /> 
        <Route exact path='/profile'  element={<PrivateRoute><Profile/></PrivateRoute>} /> 
        <Route exact path='/notifications' element={<PrivateRoute><Notifications/></PrivateRoute>} /> 
        <Route exact path='/history' element={<PrivateRoute><ActionHistory/></PrivateRoute>} /> 
        <Route exact path='/loan-details' element={<LoanDetails />} /> 
      </Routes> 
    </Router> 
 
       
      {/* <Login/> */} 
       
    </> 
  ) 
} 
 
export default App