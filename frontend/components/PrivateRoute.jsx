import { Navigate } from "react-router-dom"; 
  
/* 
 * PrivateRoute Component 
 * This component restricts access to certain routes based on user authentication. 
 * If a valid token exists in local storage, it allows access to the requested route. 
 * Otherwise, it redirects the user to the login page. 
 */ 
const PrivateRoute = ({ children }) => { 
  const token = localStorage.getItem("token"); 
  return token ? children : <Navigate to="/login" replace />; 
}; 
  
export default PrivateRoute;