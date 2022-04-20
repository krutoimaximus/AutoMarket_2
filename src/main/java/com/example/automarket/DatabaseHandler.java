package com.example.automarket;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler implements Configs.Config, Configs.Const {

    Connection con;

    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            con = DriverManager.getConnection(url, dbUser, dbPass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return con;

     }


    public void signUpAuto(String stamp, String model, String category, String number, String type, String production) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + AUTO_TABLE + "(" + AUTO_STAMP + "," +
                AUTO_MODEL + "," + AUTO_CATEGORY + "," +
                AUTO_NUMBER + "," + AUTO_TYPE + "," +
                AUTO_PRODUCTION + ")" +
                "VALUE(?,?,?,?,?,?) ";


       try {
           PreparedStatement prSt = getDbConnection().prepareStatement(insert);
           prSt.setString(1, stamp);
           prSt.setString(2, model);
           prSt.setString(3, category);
           prSt.setString(4, number);
           prSt.setString(5, type);
           prSt.setString(6, production);

           prSt.executeUpdate();
       } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
       }

    }

}



