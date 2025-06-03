import React from "react"; 
import { Pie, Bar } from "react-chartjs-2"; 
import { 
  Chart as ChartJS, 
  ArcElement, 
  Tooltip, 
  Legend, 
  CategoryScale, 
  LinearScale, 
  BarElement, 
  Title, 
} from "chart.js"; 
 
// Register chart types globally for Pie and Bar charts 
ChartJS.register( 
  ArcElement, 
  Tooltip, 
  Legend, 
  CategoryScale, 
  LinearScale, 
  BarElement, 
  Title 
); 
 
// Define color scheme for charts 
const colors = { 
  main: "#83D2CD", 
  mainAccent: "#73E2DB", 
  sub: "#F49ADF", 
  subAccent: "#FF8FE5", 
} 
 
//ChartSection Component 
//This component visualizes the loan repayment schedule using Pie and Bar charts. 
function ChartSection({ principal, totalInterest, schedule }) { 
  // Configure Pie chart for overall repayment distribution 
  const pieData = { 
    labels: ["Principal", "Total Interest"], 
    datasets: [ 
      { 
        data: [principal, totalInterest], 
        backgroundColor: [colors.main, colors.sub], 
        hoverBackgroundColor: [colors.mainAccent, colors.subAccent], 
        borderWidth: 1, 
      }, 
    ], 
  }; 
 
  // Configure Bar chart for monthly breakdown of Principal and Interest 
  const barData = { 
    labels: schedule.map((s) => `Month ${s.installmentNumber}`), 
    datasets: [ 
      { 
        label: "Principal", 
        data: schedule.map((s) => parseFloat(s.principal)), 
        backgroundColor: colors.main 
      }, 
      { 
        label: "Interest", 
        data: schedule.map((s) => parseFloat(s.monthlyInterest)), 
        backgroundColor: colors.sub 
      }, 
    ], 
  }; 
 
  // Common chart options 
  const options = { 
    responsive: true, 
    maintainAspectRatio: true, 
    plugins: { 
      legend: { 
        position: "top", 
      }, 
    }, 
  }; 
 
  return ( 
    <div className="container-fluid mt-4"> 
      <div className="row align-items-center"> 
        {/* Pie Chart: Shows Principal vs Interest distribution */} 
        <div className="col-12 col-lg-4"> 
          <div className="card p-3 m-2"> 
            <h5 className="text-center">Repayment Distribution</h5> 
            <div className="d-flex justify-content-center"> 
              <Pie data={pieData} options={options} /> 
            </div> 
          </div> 
        </div> 
        {/* Bar Chart: Displays monthly breakdown of Principal & Interest */} 
        <div className="col-12 col-lg-8"> 
          <div className="card p-3 m-2"> 
            <h5 className="text-center">Monthly Breakdown</h5> 
            <div className="d-flex justify-content-center"> 
              <Bar data={barData} options={options} /> 
            </div> 
          </div> 
        </div> 
      </div> 
    </div> 
 
  ); 
} 
 
export default ChartSection;