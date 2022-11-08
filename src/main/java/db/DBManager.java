package db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static DBManager instance;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }


//---------Pool Connection
//    public Connection getConnection(){
//        Context ctx;
//        Connection c = null;
//        try {
//            ctx = new InitialContext();
//            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/fpool");
//            c = ds.getConnection();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return c;
//    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facultative", "root", "132465");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

}
