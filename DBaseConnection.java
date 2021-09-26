package network;

// -- download MySQL from: http://dev.mysql.com/downloads/
//    Community Server version
// -- Installation instructions are here: http://dev.mysql.com/doc/refman/5.7/en/installing.html
// -- open MySQL Workbench to see the contents of the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

// -- MAKE SURE THE JDBC CONNECTOR JAR IS IN THE BUILD PATH
//    workspace -> properties -> Java Build Path -> Libraries -> Add External JARs...

// https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html for example SQL statements
public class DBaseConnection {

	// -- objects to be used for database access
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rset = null;

    // -- root/admin
    // -- connect to the world database
    // -- this is the connector to the database, default port is 3306
    //    <<Your schema name here>> is the schema (database) you created using the workbench
    private String userdatabaseURL = "jdbc:mysql://localhost:3306/csc335?useSSL=false";
    //Data path:   /usr/local/mysql/data/csc335/users.ibd
    
    // -- this is the username/password, created during installation and in MySQL Workbench
    //    When you add a user make sure you give them the appropriate Administrative Roles
    //    (DBA sets all which works fine)
    private String user = "root";
    private String password = "Admin1967";

	public DBaseConnection() {
		String sqlcmd; 
		
		try {
            // -- make the connection to the database
			//    performs functionality of SQL: use <<your schema>>;
			conn = DriverManager.getConnection(userdatabaseURL, user, password);
            
			// -- These will be used to send queries to the database
            stmt = conn.createStatement();
            
            // -- simple SQL strings as they would be typed into the workbench
            sqlcmd = "SELECT VERSION()";
            rset = stmt.executeQuery(sqlcmd);

            if (rset.next()) {
                System.out.println("MySQL Version: " + rset.getString(1));
            }
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
        }   
	}
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String insertIntoDatabase(String uname, String pword, String email) {
        // -- test an insert statement. Note that this will throw an exception if
        //    the key already exists in the database            
        String message = "success";
        try {
      	    String sqlcmd = "INSERT INTO Users (username, password, email) VALUES('" + uname + "', '" + pword + "', '" + email+ "')";
      	    stmt.executeUpdate(sqlcmd);
        }
        catch (SQLException ex) {
        	message = "duplicateuser";
			System.out.println("SQLException: " + ex.getMessage());
        }
        finally {
        	return message;
        }
	}
	public boolean doesUsernameExist(String uname) {
		try {
            // -- select a specific username
            String sqlcmd = "SELECT * FROM users WHERE username = '" + uname + "'";
            rset = stmt.executeQuery(sqlcmd);
            // -- loop through the ResultSet one row at a time
            //    Note that the ResultSet starts at index 1
            if (rset.next())
          	    return true;
            else
            	return false;
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return false;
	     }
	}
	public String login(String uname, String pword) {
		String message = "success";
			
			String correctPass = getPass(uname);

          	if(correctPass.contentEquals(pword)) {
          		message = "success";
          		setLoggedInStatus(uname, true);
          		setLockoutCount(uname, 0);
        	}
          	else {
          		message = "invalidpass";
          	    int lockoutCount = getLockoutCount(uname);
          	    setLockoutCount(uname, lockoutCount + 1);
          	}
		
		
	  	return message;
	}
	public String getPass(String uname) {
		String password = "";
		try {
            // -- select a specific username
            String sqlcmd = "SELECT * FROM users WHERE username = '" + uname + "'";
            rset = stmt.executeQuery(sqlcmd);
            
            // -- loop through the ResultSet one row at a time
            //    Note that the ResultSet starts at index 1
            while (rset.next()) {
              	password = rset.getString(2);
            }
            return password;
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return null;
	     }
	}
	public String getEmail(String uname) {
		String email = "";
		try {
            // -- select a specific username
            String sqlcmd = "SELECT * FROM users WHERE username = '" + uname + "'";
            rset = stmt.executeQuery(sqlcmd);
            
            // -- loop through the ResultSet one row at a time
            //    Note that the ResultSet starts at index 1
            while (rset.next()) {
              	email = rset.getString(3);
            }
            return email;
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return null;
	     }
	}
	
	public int getLockoutCount(String uname) {
		int count = -1;
		try {
            // -- select a specific username
            String sqlcmd = "SELECT * FROM users WHERE username = '" + uname + "'";
            rset = stmt.executeQuery(sqlcmd);
            // -- loop through the ResultSet one row at a time
            //    Note that the ResultSet starts at index 1
          
            while (rset.next()) {
              	count = rset.getInt(4);
            }
            return count;
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return count;
	     }
	}
	
	public void setLockoutCount(String uname, int count) {
		try {
            // -- select a specific username
            String sqlcmd = "UPDATE Users SET lockoutcount = '" + count + "' WHERE (username = '" + uname + "')";
        	stmt.executeUpdate(sqlcmd);
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
	     }
	}
	public void setPass(String uname, String pass) {
		try {
            // -- select a specific username
            String sqlcmd = "UPDATE Users SET password = '" + pass + "' WHERE (username = '" + uname + "')";
        	stmt.executeUpdate(sqlcmd);
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
	     }
	}
	
	public void setLoggedInStatus(String uname, boolean status) {
		try {
            // -- select a specific username
            int stat = (status)?1:0;
            String sqlcmd = "UPDATE Users SET loggedinstatus = '" + stat + "' WHERE (username = '" + uname + "')";
        	stmt.executeUpdate(sqlcmd);
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
	     }
	}
	
	public boolean isLoggedIn(String uname) {
		boolean isLoggedIn = false;
		try {
            // -- select a specific username
            String sqlcmd = "SELECT * FROM users WHERE username = '" + uname + "'";
            rset = stmt.executeQuery(sqlcmd);
            // -- loop through the ResultSet one row at a time
            //    Note that the ResultSet starts at index 1
          
            while (rset.next()) {
              	int status = rset.getInt(5);
              	isLoggedIn = (status == 0)? false:true;
            }
            return isLoggedIn;
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			return isLoggedIn;
	     }
	}
//            /*
//             * EVERYTHING THAT FOLLOWS IS DEPENDENT ON THE TABLES AND COLUMNS
//             * THAT YOU CREATED WITHIN YOUR SCHEMA. YOU MUST MODIFY THIS CODE
//             * TO MATCH THE DATABASE CONFIGURATION. THESE ARE ONLY EXAMPLES.
//             */
//		try {
//            // -- a query will return a ResultSet
//            sqlcmd = "SELECT * FROM Users;";
//            rset = stmt.executeQuery(sqlcmd);
//            
//            // -- the metadata tells us how many columns in the data
//            System.out.println("Table Columns:");
//            ResultSetMetaData rsmd = rset.getMetaData();
//            int numberOfColumns = rsmd.getColumnCount();
//            for (int i = 1; i <= numberOfColumns; ++i) {
//            	System.out.print(rsmd.getColumnLabel(i) + "\t");
//            }
//            System.out.println();
//            
//            // -- loop through the ResultSet one row at a time
//            //    Note that the ResultSet starts at index 1
//            while (rset.next()) {
//            	// -- loop through the columns of the ResultSet
//            	for (int i = 1; i < numberOfColumns; ++i) {
//            		System.out.print(rset.getString(i) + "\t\t");
//            	}
//            	System.out.println();
//            }
//
	
	
	
	
//            // -- select a specific username
//            System.out.println("getting user data for ccrdoc");
//            String selname = "ccrdoc";     
//            //        SELECT * FROM users WHERE username = 'ccrdoc'
//            sqlcmd = "SELECT * FROM users WHERE username = '" + selname + "'";
//            rset = stmt.executeQuery(sqlcmd);
//            // -- loop through the ResultSet one row at a time
//            //    Note that the ResultSet starts at index 1
//            while (rset.next()) {
//            	// -- loop through the columns of the ResultSet
//            	for (int i = 1; i <= numberOfColumns; ++i) {
//            		System.out.print(rset.getString(i) + "\t\t");
//            	}
//            	System.out.println();
//            }
//
	
	
	
//
//            // -- test an insert statement. Note that this will throw an exception if
//            //    the key already exists in the database            
//            System.out.println("inserting into the database");
//            String uname = "ccrdoc2";
//            String pword = "ccrdoc1234";
//            String email = "ccrdoc@gmail.com";
//            try {
//            	//        INSERT INTO Users VALUES('ccrdoc2', 'ccrdoc1234', 'ccrdoc@gmail.com')
//            	sqlcmd = "INSERT INTO Users VALUES('" + uname + "', '" + pword + "', '" + email + ")";
//            	stmt.executeUpdate(sqlcmd);
//            }
//            catch (SQLException ex) {
//    			System.out.println("SQLException: " + ex.getMessage());
//            }
	
	
	
	
	
//            // UPDATE `csc335`.`users` SET `password` = '1234ccrdoc' WHERE (`username` = 'ccrdoc2');   
//            try {
//            	//        INSERT INTO Users VALUES('ccrdoc2', 'ccrdoc1234', 'ccrdoc@gmail.com')
//            	sqlcmd = "UPDATE Users SET password = '1234ccrdoc' WHERE (username = 'ccrdoc2')";
//            	stmt.executeUpdate(sqlcmd);
//            }
//            catch (SQLException ex) {
//    			System.out.println("SQLException: " + ex.getMessage());
//            }
//           
//            System.out.println("selecting all records from data base");
//            sqlcmd = "SELECT * FROM Users;";
//            rset = stmt.executeQuery(sqlcmd);
//            // -- loop through the ResultSet one row at a time
//            //    Note that the ResultSet starts at index 1
//            while (rset.next()) {
//            	// -- loop through the columns of the ResultSet
//            	for (int i = 1; i < numberOfColumns; ++i) {
//            		System.out.print(rset.getString(i) + "\t\t");
//            	}
//            	System.out.println(rset.getString(numberOfColumns));
//            }
//
//		} catch (SQLException ex) {
//			// handle any errors
//			System.out.println("SQLException: " + ex.getMessage());
//			System.out.println("SQLState: " + ex.getSQLState());
//			System.out.println("VendorError: " + ex.getErrorCode());
//		}

	//}


}
