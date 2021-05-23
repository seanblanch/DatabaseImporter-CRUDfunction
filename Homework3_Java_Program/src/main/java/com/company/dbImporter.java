package com.company;

import java.util.Scanner;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class dbImporter {

    public static void main(String[] args) throws FileNotFoundException{
        //Scanner scan = new Scanner(new File("C:\\Users\\sean1\\Desktop\\COMP420-HW3\\Homework3_Java_Program\\titles_5000.csv"));
        //Scanner scan = new Scanner(new File("crew_5000.csv"));
        //Scanner scan = new Scanner(new File("episodes_5000.csv"));
        //Scanner scan = new Scanner(new File("people_5000.csv"));
        //Scanner scan = new Scanner(new File("ratings_5000.csv"));
        Scanner scan = new Scanner(new File("titles_5000.csv"));
        while(scan.hasNextLine())
        {
            String line = scan.nextLine();
            String [] linearray = line.split(",");
            if(linearray[6].equals(""))
                linearray[6]="NULL";
            Connection con = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                String uid="root";
                String pwd="comp420";
                con = DriverManager.getConnection("jdbc:mysql:///imdb", uid, pwd);
                if (!con.isClosed())
                    System.out.println("Connected to MySQL server.");
                Statement statement = con.createStatement();
                //statement.executeUpdate("insert into akas values('"+linearray[0]+"','"+linearray[1]+"','"+linearray[2]+"','"+linearray[3]+"','"+linearray[4]+"','"+linearray[5]+"','"+linearray[6]+"')");//akas
                //statement.executeUpdate("insert into crew values('"+linearray[0]+"','"+linearray[1]+"','"+linearray[2]+"','"+linearray[3]+"')");//crew
                //statement.executeUpdate("insert into episodes values('"+linearray[0]+"','"+linearray[1]+"','"+linearray[2]+"','"+linearray[3]+"')");//episodes
                //statement.executeUpdate("insert into people values('"+linearray[0]+"','"+linearray[1]+"','"+linearray[2]+"')");//people
                //statement.executeUpdate("insert into ratings values('"+linearray[0]+"','"+linearray[1]+"','"+linearray[2]+"')");//ratings
                statement.executeUpdate("insert into titles values('"+linearray[0]+"','"+linearray[1]+"','"+linearray[2]+"','"+linearray[3]+"',"+linearray[4]+","+linearray[5]+","+linearray[6]+","+linearray[7]+",'"+linearray[8]+"')");//titles
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

            //System.out.println(line);
        }
        scan.close();
    }

}



