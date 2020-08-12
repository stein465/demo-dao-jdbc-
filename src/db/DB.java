package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    private static Properties loadPropeties(){
        try(FileInputStream fs =new FileInputStream ( "db.properties" ) ){
            Properties prop = new Properties (  );
            prop.load ( fs );
            return prop;
        }
        catch (IOException e){
            throw new DbException ( e.getMessage ());
        }
    }

    public static Connection getConnection(){
        try {
            if (conn == null) {
                Properties prop = loadPropeties ();
                String url = prop.getProperty ( "dburl" );
                conn = DriverManager.getConnection ( url, prop );
            }
        }
        catch (SQLException e){
            throw new DbException ( e.getMessage () );
        }
        return conn;
    }

    public static void closeConnection(){
        if (conn != null){
            try {
                conn.close ();
            } catch (SQLException e) {
                throw new DbException (e.getMessage () );
            }
        }
    }

    public static void closeStatement(Statement st){
        if (st != null){
            try {
                st.close ();
            } catch (SQLException e) {
                throw new DbException ( e.getMessage () );
            }
        }
    }
    public static void closeResultSet(ResultSet rs){
        if (rs != null){
            try {
                rs.close ();
            } catch (SQLException e) {
                throw new DbException ( e.getMessage () );
            }
        }
    }
    

}
