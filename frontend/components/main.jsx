import { StrictMode } from 'react'; 
import { createRoot } from 'react-dom/client'; 
import './index.css'; 
import App from './App.jsx'; 
// import Footer from './Footer.jsx'; 
 
createRoot(document.getElementById('root')).render( 
  <StrictMode> 
    <App /> 
  </StrictMode>, 
) 
 
const copyrightText = document.getElementById('copyright-text'); 
copyrightText.innerText = `©${new Date().getFullYear()} LOAN-A-WALA. All Rights Reserved.`; 
copyrightText.style.color = "black"; 
copyrightText.style.margin = "0px"; 
copyrightText.style.padding = "5px"; 
copyrightText.style.fontSize = "0.75em";