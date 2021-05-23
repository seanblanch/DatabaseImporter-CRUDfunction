package com.company;

import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Formatter;

public class Main {


    // write your code here
    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("Hello!");
        File outFile = new File("vendors.csv");
        Formatter output = new Formatter(outFile);
        output.format("Vendor Name, Vendor Contact, Vendor Phone, Vendor State\n");
        Connection con = null;
        try {
            String jdbcURL = "jdbc:mysql://%s:%d/%s";
            String uid="root";
            //String uid="seanblan_root";
            String pwd="comp420";
            //String pwd="M@gnum15";
            //System.out.println("Connect here");
            con = buildConnection(jdbcURL,"localhost", 3306, "saleco", uid, pwd);
            //System.out.println("Connected");
            if (!con.isClosed())
                System.out.println("Connected to MySQL server.");
            Statement statement = con.createStatement();
            //Result set get the result of the SQL query
            ResultSet resultSet = statement.executeQuery("SELECT v.* FROM saleco.vendor v");
            while (resultSet.next()){
                String vName = resultSet.getString("v.V_NAME");
                String vContact = resultSet.getString("v.V_CONTACT");
                String vPhone = resultSet.getString("v.V_PHONE");
                String vState = resultSet.getString("v.V_STATE");
                output.format(vName + "," + vContact + "," + vPhone + "," + vState + "\n");
            }

        }
        catch (Exception e){
            System.err.println("Exception:" + e.getMessage());
        }
        finally {
            try {
                if (con != null){
                    con.close();
                    System.out.println("Disconnected from MySQL server");
                }
            }
            catch (SQLException e){
                System.err.println("Exception:" + e.getMessage());
            }
        }
        output.close();
    }

    public static Connection buildConnection(String jdbcURL, String host, int port, String schema, String user, String password) throws SQLException {
        return DriverManager.getConnection(String.format(jdbcURL, host, port, schema), user, password);
    }

}
