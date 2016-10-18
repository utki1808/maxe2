import java.sql.*;
import java.util.Scanner;
import java.lang.*;
//import com.mysql.jdbc.PreparedStatement;
public class database {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int choice;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Initialized");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "root");
			System.out.println("Connection established");
			int ch;
			do
			{
			System.out.println("1.Select an entry");
			System.out.println("2.Insert an entry");
			System.out.println("3.Update an entry");
			System.out.println("4.Delete an entry");
			System.out.println("5.Stored Procedure");
			System.out.println("6.Views");
			Scanner sc=new Scanner(System.in);
			System.out.println("Please select an option : ");
			choice=sc.nextInt();
			switch(choice)
			{
			case 1:
				String selectQuery="select * from student";
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(selectQuery);
				while(rs.next())
				{
					int id=rs.getInt("Student_ID");
					String name=rs.getString("Student_Name");
					String address=rs.getString("Address");
					int marks=rs.getInt("Marks");
					System.out.println(id+" "+name+" "+address+" "+marks+"\n");
				}
				st.close();
				break;
			case 2:
				int id,marks;
				String name,address;
				
				System.out.println("Enter the student id : ");
				id=sc.nextInt();
				System.out.println("Enter the student name : ");
				name=sc.next();
				System.out.println("Enter the student address : ");
				address=sc.next();
				System.out.println("Enter the student marks : ");
				marks=sc.nextInt();
				
				String insertQuery="insert into student values (?,?,?,?)";
				PreparedStatement pstat=(PreparedStatement) con.prepareStatement(insertQuery);
				pstat.setInt(1, id);
				pstat.setString(2, name);
				pstat.setString(3, address);
				pstat.setInt(4, marks);
				
				int nrows=pstat.executeUpdate();
				if(nrows>0)
				{
					System.out.println("Data inserted");
				}
				else
				{
					System.out.println("Data insertion failed");
				}
				pstat.close();
				break;
			case 3:
				int studentId,studentMarks;
				String studentName,studentAddress;
				
				System.out.println("Enter the student id : ");
				studentId=sc.nextInt();
				System.out.println("Enter the student name : ");
				studentName=sc.next();
				System.out.println("Enter the student address : ");
				studentAddress=sc.next();
				System.out.println("Enter the student marks : ");
				studentMarks=sc.nextInt();
				
				String updateQuery="update student set  Student_Name =?,Address=?,Marks=? where  STUDENT_ID=?";
				PreparedStatement prepstat=(PreparedStatement) con.prepareStatement(updateQuery);
				
				prepstat.setString(1, studentName);
				prepstat.setString(2, studentAddress);
				prepstat.setInt(3, studentMarks);
				prepstat.setInt(4, studentId);
				
				int noOfRows=prepstat.executeUpdate();
				if(noOfRows>0)
				{
					System.out.println(noOfRows+" row updated");
				}
				else
				{
					System.out.println("Data updation failed");
				}
				prepstat.close();
				break;
			case 4:
				int sId;
				
				System.out.println("Enter the student id : ");
				sId=sc.nextInt();
				String query="delete from student where STUDENT_ID=?";
				PreparedStatement prstat=(PreparedStatement) con.prepareStatement(query);
				prstat.setInt(1, sId);
				int noofrows=prstat.executeUpdate();
				if(noofrows>0)
				{
					System.out.println("Deleted "+noofrows+" rows");
					
				}
				else
				{
					System.out.println("Data deletion unsuccessful");
				}
				prstat.close();
				break;
			case 5:
				int StudentId;
				
				System.out.println("Enter the student id : ");
				StudentId=sc.nextInt();
				CallableStatement stmt=null;
				System.out.println("Creating Statement...");
				String sql="{call getStudentName(?,?)}";
				stmt=con.prepareCall(sql);
				stmt.setInt(1, StudentId);
				stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
				System.out.println("Executing the stored procedure..");
				stmt.execute();
				String StudentName=stmt.getString(2);
				System.out.println("Name of the student is : "+StudentName);
				stmt.close();
				break;
			case 6:
				Statement stat=con.createStatement();
				String code = " create view Student_View as (select Student_Name,Marks from student);";
				stat.executeUpdate(code);
                System.out.println("View created successfully !!!");
                String selQuery="select * from Student_View";
                ResultSet resultSet=stat.executeQuery(selQuery);
				while(resultSet.next())
				{
					
					String s_name=resultSet.getString("Student_Name");
					int s_marks=resultSet.getInt("Marks");
					System.out.println(s_name+" "+s_marks+"\n");
				}
				stat.close();
                break;
			
		}
			System.out.println("Do you want to continue(1/0) : ");
			ch=sc.nextInt();
			}while(ch == 1);
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
