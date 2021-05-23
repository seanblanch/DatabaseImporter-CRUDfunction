//Sean Blanchard
//4/6/2021
//COMP420 - HW 3

package com.company;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class CRUDFunctions {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. INSERT (Create a new record)\n"
                + "2. Search a Record\n"
                + "3. Update a Record\n"
                + "4. Delete a Record\n"
                + "5. Report a Record\n"
                + "6. Close Program Gracefully\n");
        String input = scan.nextLine();
        while(!input.equals("6"))
        {
            switch(input) {
                case "1":
                    insert();
                    break;
                case "2":
                    search();
                    break;
                case "3":
                    update();
                case "4":
                    delete();
                    break;
                case "5":
                    report();
                    break;
                case "6":
                    break;
                default:
                    System.out.println("not a valid entry");
                    break;
            }
            System.out.println("1. INSERT (Create a new record)\n"
                    + "2. Search a Record\n"
                    + "3. Update a Record\n"
                    + "4. Delete a Record\n"
                    + "5. Report a Record\n"
                    + "6. Close Program Gracefully\n");
            input = scan.nextLine();
        }
        System.out.println("You have closed the program.");

    }

    private static void report(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Intput akas title: ");
        String sql = scan.nextLine();
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String uid="root";
            String pwd="comp420";
            con = DriverManager.getConnection("jdbc:mysql:///imdb", uid, pwd);
            if (!con.isClosed())
                System.out.println("Connected to MySQL server.");
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title_id FROM imdb.akas WHERE title = '" + sql + "';");
            System.out.println("title_id: corresponding with the title " + sql );
            while (resultSet.next()){
                String title = resultSet.getString("title_id");

                System.out.printf(title + "\n");
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
    }

    private static void update() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter UPDATE SQL code: (UPDATE table_name\n" +
                "SET column1 = value1, column2 = value2, ...\n" +
                "WHERE condition;)\n" +
                "UPDATE Customers\n" +
                "SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'\n" +
                "WHERE CustomerID = 1;");
        String sql = scan.nextLine();
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String uid="root";
            String pwd="comp420";
            con = DriverManager.getConnection("jdbc:mysql:///imdb", uid, pwd);
            if (!con.isClosed())
                System.out.println("Connected to MySQL server.");
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
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
    }

    private static void delete() {
        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter Person ID: ");
//        String ID = scan.nextLine();
        System.out.println("Enter DELETE SQL code: (DELETE FROM table_name WHERE condition;)(DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';)");
        String sql = scan.nextLine();
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String uid="root";
            String pwd="comp420";
            con = DriverManager.getConnection("jdbc:mysql:///imdb", uid, pwd);
            if (!con.isClosed())
                System.out.println("Connected to MySQL server.");
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);
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

    }

    private static void search() {
        Scanner scan = new Scanner(System.in);
        //System.out.println("Enter First and Last Name to Search: ");
        //String Search = scan.nextLine();
        System.out.println("Enter SELECT SQL code: (SELECT column1, column2, ...\n" +
                                                    "FROM table_name;) (SELECT CustomerName, City FROM Customers;)");
        String sql = scan.nextLine();
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String uid="root";
            String pwd="comp420";
            con = DriverManager.getConnection("jdbc:mysql:///imdb", uid, pwd);
            if (!con.isClosed())
                System.out.println("Connected to MySQL server.");
            Statement statement = con.createStatement();
            //Result set get the result of the SQL query
            //ResultSet resultSet = statement.executeQuery("select * from people where name like '%"+Search+"%'");
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String ID = resultSet.getString("title_id");
                String Name = resultSet.getString("title");
                String DOB = resultSet.getString("region");
                String Death = resultSet.getString("language");
                System.out.println(ID+"\t"+Name+"\t"+DOB+"\t"+Death+"\t");
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

    }

//    private static void insert() {
//        Scanner scan = new Scanner(System.in);
////        System.out.println("Enter Person ID: ");
////        String ID = scan.nextLine();
////        System.out.println("Enter Person First and Last Name: ");
////        String Name = scan.nextLine();
////        System.out.println("Enter Year of Birth: ");
////        String DOB = scan.nextLine();
////        System.out.println("Enter Year of Death: ");
////        String Death = scan.nextLine();
//
//        System.out.println("Enter insert SQL code: (INSERT INTO AKAS(title_id, title, region, language, types, attributes, is_original_title) " +
//                            "VALUES('tt009020', 'Frozen', 'DK', 'cs', 'original', 'copyright', 1));");
//        String sql = scan.nextLine();
//
//        Connection con = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//            String uid="root";
//            String pwd="comp420";
//            con = DriverManager.getConnection("jdbc:mysql:///imdb", uid, pwd);
//            if (!con.isClosed())
//                System.out.println("Connected to MySQL server.");
//            Statement statement = con.createStatement();
//            //statement.executeUpdate("insert into people(person_id, name, born, died) values('"+ID+"','"+Name+"','"+DOB+"','"+Death+"')");
//            statement.executeUpdate(sql);
//        }
//        catch (Exception e){
//            System.err.println("Exception:" + e.getMessage());
//        }
//        finally {
//            try {
//                if (con != null){
//                    con.close();
//                    System.out.println("Disconnected from MySQL server");
//                }
//            }
//            catch (SQLException e){
//                System.err.println("Exception:" + e.getMessage());
//            }
//        }
//
//    }


    private static void insert() {
        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter Person ID: ");
//        String ID = scan.nextLine();
//        System.out.println("Enter Person First and Last Name: ");
//        String Name = scan.nextLine();
//        System.out.println("Enter Year of Birth: ");
//        String DOB = scan.nextLine();
//        System.out.println("Enter Year of Death: ");
//        String Death = scan.nextLine();

        System.out.println("Enter insert SQL code: (INSERT INTO AKAS(title_id, title, region, language, types, attributes, is_original_title) " +
                "VALUES('tt009020', 'Frozen', 'DK', 'cs', 'original', 'copyright', 1));");
        String sql = scan.nextLine();

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String jdbcURL = "jdbc:mysql://%s:%d/%s";
            String uid="seanblan_root";
            String pwd="M@gnum15";
            con = buildConnection(jdbcURL, "seanblanch.cikeys.com", 3306, "seanblan_comp_420_spring_2021", uid, pwd);
            if (!con.isClosed())
                System.out.println("Connected to MySQL server.");
            Statement statement = con.createStatement();
            //statement.executeUpdate("insert into people(person_id, name, born, died) values('"+ID+"','"+Name+"','"+DOB+"','"+Death+"')");
            //statement.executeUpdate("insert into animal(animal_ID) values(2)");
            statement.executeUpdate(sql);
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

    }

    public static Connection buildConnection(String jdbcURL, String host, int port, String schema, String user, String password) throws SQLException {
        return DriverManager.getConnection(String.format(jdbcURL, host, port, schema), user, password);
    }

}

