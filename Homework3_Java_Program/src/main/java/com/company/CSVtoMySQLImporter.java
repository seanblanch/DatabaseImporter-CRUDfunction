package com.company;


import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Formatter;

public class CSVtoMySQLImporter {

    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("Hello!");
        File outFile = new File("vendors.csv");
        Formatter output = new Formatter(outFile);
        output.format("Vendor Name, Vendor Contact, Vendor Phone, Vendor State\n");
        Connection con = null;
        try {
            String jdbcURL = "jdbc:mysql://%s:%d/%s";
            String uid = "seanblan_root";
            String pwd = "M@gnum15";
            String csvFilePath = "akas.csv";
            //String csvFilePath = "crew_5000.csv";
            //String csvFilePath = "titles_5000.csv";

            int batchSize = 20;

            con = buildConnection(jdbcURL, "seanblanch.cikeys.com", 3306, "seanblan_comp_420_spring_2021", uid, pwd);
            if (!con.isClosed())
                System.out.println("Connected to MySQL server.");
            //Statement statement = con.createStatement();
            //Result set get the result of the SQL query

            //Write the table in
            Statement stmt = null;
            System.out.println("Creating a table in given database... ");
            stmt = con.createStatement();
//            String sql = " CREATE TABLE akas " +
//                         "(title_id VARCHAR(15), " +
//                         " title VARCHAR(50), " +
//                         " region VARCHAR(10), " +
//                         " language VARCHAR(10), " +
//                         " types VARCHAR(25), " +
//                         " attributes VARCHAR(50), " +
//                         " is_original_title INTEGER ) ";

//            String sql =
//                         " CREATE TABLE crew " +
//                         "(title_id VARCHAR(15), " +
//                         " person_id VARCHAR(10), " +
//                         " category VARCHAR(25), " +
//                         " job VARCHAR(100) ) ";

//            String sql = " CREATE TABLE titles " +
//                         "(title_id VARCHAR(15), " +
//                         " type VARCHAR(25), " +
//                         " primary_title VARCHAR(50), " +
//                         " original_title VARCHAR(50), " +
//                         " is_adult VARCHAR(10), " +
//                         " premiered DATE, " +
//                         " ended VARCHAR(25), " +
//                         " runtime_minutes INTEGER, " +
//                         " genres VARCHAR(50) ) ";

                        String sql = " CREATE TABLE ratings " +
                         "(title_id VARCHAR(15), " +
                         " rating float, " +
                         " votes INTEGER  ) ";

            stmt.executeUpdate(sql);

            // Call the Insert into
            PreparedStatement statement = con.prepareStatement("INSERT INTO akas (title_id, title, region, language, types, attributes, is_original_title) VALUES (?, ?, ?, ?, ?, ?, ?)");
            //PreparedStatement statement = con.prepareStatement("INSERT INTO crew (title_id, type, category, job) VALUES (?, ?, ?, ?)");
            //PreparedStatement statement = con.prepareStatement("INSERT INTO seanblan_comp_420_spring_2021.titles (title_id, type, primary_title, original_title, is_adult, premiered, ended, runtime_minutes, genres) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            //PreparedStatement statement = con.prepareStatement("INSERT INTO seanblan_comp_420_spring_2021.ratings (title_id, rating, votes) VALUES (?, ?, ?)");

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine();

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String titleID = data[0];
                String titles = data[1];
                String regions = data[2];
                String languages = data[3];
                String type = data[4];
                String attribute = data[5];
                Integer ogTitle = Integer.valueOf(data[6]);

                statement.setString(1, titleID);
                statement.setString(2, titles);
                statement.setString(3, regions);
                statement.setString(4, languages);
                statement.setString(5, type);
                statement.setString(6, attribute);
                statement.setInt(7, ogTitle);

//                String[] data = lineText.split(",");
//                String titleID = data[0];
//                String personID = data[1];
//                String categorys = data[2];
//                String jobs = data[3];
//
//                statement.setString(1, titleID);
//                statement.setString(2, personID);
//                statement.setString(3, categorys);
//                statement.setString(4, jobs);

//                String[] data = lineText.split(",");
//                String titleID = data[0];
//                String types = data[1];
//                String primary_title = data[2];
//                String original_title = data[3];
//                String is_adult = data[4];
//                String premiered = data[5];
//                String ended = data[6];
//                Integer runtime_minutes = Integer.valueOf(data[7]);
//                String genres = data[8];
//
//                statement.setString(1, titleID);
//                statement.setString(2, types);
//                statement.setString(3, primary_title);
//                statement.setString(4, original_title);
//                statement.setString(5, is_adult);
//                statement.setDate(6, Date.valueOf(premiered));
//                statement.setString(7, ended);
//                statement.setInt(7, runtime_minutes);
//                statement.setString(8, genres);

//                String[] data = lineText.split(",");
//                String titleID = data[0];
//                Double rating = Double.valueOf(data[1]);
//                Integer categorys = Integer.valueOf(data[2]);
//
//                statement.setString(1, titleID);
//                statement.setDouble(2, rating);
//                statement.setInt(3, categorys);

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            lineReader.close();

            statement.executeBatch();

            con.commit();
            con.close();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


    public static Connection buildConnection(String jdbcURL, String host, int port, String schema, String user, String password) throws SQLException {
        return DriverManager.getConnection(String.format(jdbcURL, host, port, schema), user, password);
    }

}
