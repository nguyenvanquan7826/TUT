package nguyenvanquan7826.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnect {

	private final String className = "com.mysql.jdbc.Drive";
	private final String url = "jdbc:mysql://localhost:3306/student";
	private final String user = "root";
	private final String pass = "lamgicopass";

	private String table = "student_info";

	private Connection connection;

	// connect to database
	public void connect() {
		try {
			Class.forName(className);
			connection = DriverManager.getConnection(url, user, pass);
			System.out.println("Connect success!");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
		} catch (SQLException e) {
			System.out.println("Error connection!");
		}
	}

	// show data is selected
	public void showData(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.printf("%-10s %-20s %-5.2f \n", rs.getString(1),
						rs.getString(2), rs.getDouble(3));
			}
		} catch (SQLException e) {
		}
	}

	// get data and return Resultset with command select
	public ResultSet getData() {
		ResultSet rs = null;
		String sqlCommand = "select * from " + table;
		Statement st;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(sqlCommand);
		} catch (SQLException e) {
			System.out.println("select error \n" + e.toString());
		}
		return rs;
	}

	// get data and return Resultset with command select by Id
	public ResultSet getDataId(String id) {
		ResultSet rs = null;
		String sqlCommand = "select * from " + table + " where id = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace "?" by id
			pst.setString(1, id);
			rs = pst.executeQuery();
		} catch (SQLException e) {
			System.out.println("select error \n" + e.toString());
		}
		return rs;
	}

	// delete by Id
	public void deleteId(String id) {
		String sqlCommand = "delete from " + table + " where id = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			pst.setString(1, id);
			if (pst.executeUpdate() > 0) {
				System.out.println("delete success");
			} else {
				System.out.println("delete error \n");
			}
		} catch (SQLException e) {
			System.out.println("delete error \n" + e.toString());
		}
	}

	// insert student s to database
	public void insert(Student s) {
		String sqlCommand = "insert into " + table + " value(?, ?, ?)";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace three "?" by id, Name and point of Studnet s
			pst.setString(1, s.getId());
			pst.setString(2, s.getName());
			pst.setDouble(3, s.getPoint());
			if (pst.executeUpdate() > 0) {
				System.out.println("insert success");
			} else {
				System.out.println("insert error \n");
			}
		} catch (SQLException e) {
			System.out.println("insert error \n" + e.toString());
		}
	}

	// update studetn by Id
	public void updateId(String id, Student s) {
		String sqlCommand = "update " + table
				+ " set name = ?, point = ? where id = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			pst.setString(1, s.getName());
			pst.setDouble(2, s.getPoint());
			pst.setString(3, s.getId());
			if (pst.executeUpdate() > 0) {
				System.out.println("update success");
			} else {
				System.out.println("update error \n");
			}
		} catch (SQLException e) {
			System.out.println("update error \n" + e.toString());
		}
	}

	public static void main(String[] args) {
		MyConnect myConnect = new MyConnect();
		myConnect.connect();
		// myConnect.showData(myConnect.getDataId("DTC1"));
		// myConnect.deleteId("DTC5");
		//myConnect.updateId("DTC2", new Student("DTC2", "Vu Cong Tinh", 9.0));
		//myConnect.showData(myConnect.getData());
	}
}
