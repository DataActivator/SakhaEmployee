package com.ems1.dao;
import com.ems1.model.Employee;
import java.util.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

public class EmployeeDaoImpl implements EmployeeDao {

	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	Employee emp;
	@Override
	public Connection getConnection() throws SQLException {
		
		con=DriverManager.getConnection("jdbc:mysql://localhost/ems","root","@Shubham123");
		return con;
	}
	
	@Override
	public boolean saveEmployee(Employee e) throws SQLException {
		con=getConnection();
		ps=con.prepareStatement("insert into employee values(?,?,?,?)");
		ps.setString(1, e.getEmpId());
		ps.setString(2, e.getEmpName());
		LocalDate dobRaw = e.getDOB();
//		String strDate = dobRaw.getYear()+"-"+dobRaw.getMonthValue()+"-"+dobRaw.getDayOfMonth();
//		ps.setString(3,strDate);
		ps.setFloat(4,e.getBasicSalary());
		ps.setDate(3,new java.sql.Date(dobRaw.getYear(),dobRaw.getMonthValue(),dobRaw.getDayOfMonth()));
	
		
		int r=ps.executeUpdate();
		return r>0?true:false;
		
	}
	
	@Override
	public boolean deleteEmployee(String EmpId) throws SQLException {
		con=getConnection();
		ps=con.prepareStatement("delete from employee where EmpId=?");
		ps.setString(1, EmpId);
		
		
		int r=ps.executeUpdate();
		return r>0?true:false;
		
	}
	
	@Override
	public Employee getEmployee(String empid) throws SQLException {
		con=getConnection();
		ps=con.prepareStatement("select * from employee where EmpId=?");
		ps.setString(1, empid);
		rs=ps.executeQuery();
		if(rs.next())
		{
			java.sql.Date rowdate=rs.getDate(3);
			Employee emp=new Employee(rs.getString(1),rs.getString(2),LocalDate.of(rowdate.getYear(),rowdate.getMonth(),rowdate.getDate()),rs.getFloat(4));
			return emp;
		}
		return null;
	}
	
	@Override
	public List<Employee> getAllEmployees() throws Exception{
	/*	con=getConnection();
		ps=con.prepareStatement("select * from employee");
		rs=ps.executeQuery();
		List<Employee> emplist= new ArrayList<>();
		while(rs.next()) {
			java.sql.Date rowdate=rs.getDate(3);
			Employee emp=new Employee(rs.getString(1),rs.getString(2),LocalDate.of(rowdate.getYear(),rowdate.getMonth(),rowdate.getDate()),rs.getFloat(4));
			emplist.add(emp);
		}
		return emplist;   */  
		
		
		con = getConnection();
		PreparedStatement ps = null;
		List<Employee> emp = new ArrayList<>();
		//Employee e;
		ps = con.prepareStatement("select * from employee");
		System.out.println("ehllo");
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{

	Employee e = new Employee();
		e.setEmpId(rs.getString(1));
		e.setEmpName(rs.getString(2));
		//LocalDate dob = e.getDob();
		//String strDate = dob.getYear()+"-"+dob.getMonthValue()+"-"+dob.getDayOfMonth();
		//ps.setString(3,strDate);
		//ps.setDate(3, new java.sql.Date(dob.getYear()-1900,dob.getMonthValue()-1,dob.getDayOfMonth()));
		//e.setDob(dob);
		//e.setSalary(rs.getFloat(4));
		Date dob1 = rs.getDate(3);
		//LocalDate.of(dob1.getYear(), dob1.getMonth(), dob1.getDate());
		e.setDOB(dob1.toLocalDate());
		//ps.setDate(3, new java.sql.Date(dob1.getYear()-1900,dob1.getMonthValue()-1,dob1.getDayOfMonth()));
		//e.setDOB(dob1.toLocalDate());
		e.setBasicSalary(rs.getFloat(4));
		System.out.println("hello");
		emp.add(e);

		}
		return emp;    
	}
	
	@Override
	public boolean updateEmployee(Employee emp) throws SQLException {
		con=getConnection();
		PreparedStatement ps = con.prepareStatement("Update employee set EmpName = ?,Dob=?,BasicSalary=? where EmpId = ?");

		ps.setString(1,emp.getEmpName());
		LocalDate dobRaw = emp.getDOB();
		ps.setDate(2,new java.sql.Date(dobRaw.getYear(),dobRaw.getMonthValue(),dobRaw.getDayOfMonth()));
	//   Date dob1=rs.getDate(3)
		
		ps.setFloat(3,emp.getBasicSalary());
		ps.setString(4, emp.getEmpId());
		int r=ps.executeUpdate();
		return r>0?true:false;
		
	}
}