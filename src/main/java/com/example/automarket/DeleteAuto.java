package com.example.automarket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAuto {

    void initialize() throws SQLException, ClassNotFoundException {
        DatabaseHandler table = new DatabaseHandler();

        Connection conn;
        conn = table.getDbConnection();

        String sql = "DELETE from auto where idauto = ?";


        try {
            PreparedStatement prSt = table.getDbConnection().prepareStatement(sql);
            prSt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

    }


}
