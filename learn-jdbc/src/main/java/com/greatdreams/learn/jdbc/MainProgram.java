package com.greatdreams.learn.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class MainProgram {
    private static Logger logger = LoggerFactory.getLogger(MainProgram.class);

    public static void main(String[] args) {
        connnectMySQLDB();
        connnectPostgresqlDB();
    }

    public static void connnectMySQLDB() {
        System.out.println("---------connect MySQL database-----------------");
        var url = "jdbc:mysql://127.0.0.1:3306/test";
        var username = "root";
        var password = "893557whw";

        var query = "SELECT * FROM settlement_value limit 0, 10";
        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            // fetch mysql server information
            var metaData = conn.getMetaData();
            //output these information
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getDriverVersion());
            System.out.println(metaData.getJDBCMajorVersion() + "." + metaData.getJDBCMinorVersion());
            System.out.println(metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion());
            System.out.println(metaData.getURL());

            ResultSet rs = conn.createStatement().executeQuery(query);
            while(rs.next()) {
                StringBuffer result = new StringBuffer();
                result.append(String.format("%-5d", rs.getInt("id")));
                result.append(new SimpleDateFormat("YYYY-MM-dd").format(rs.getDate("date_value")) + " ");
                result.append(String.format("%-14.6f", rs.getDouble("unit_net")));
                result.append(String.format("%-14.6f", rs.getDouble("accumated_net0")));
                result.append(String.format("%-14.6f", rs.getDouble("accumated_net1")));
                result.append(String.format("%-14.6f", rs.getDouble("settlement")));

                System.out.println(result);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connnectPostgresqlDB() {
        System.out.println("---------connect Postgresql database-----------------");
        var url = "jdbc:postgresql://127.0.0.1:5432/test";
        var username = "postgres";
        var password = "123456";

        var query = "SELECT * FROM settlement_value limit 10 offset 0";
        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            // fetch postgresql server information
            var metaData = conn.getMetaData();
            //output these information
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getDriverVersion());
            System.out.println(metaData.getJDBCMajorVersion() + "." + metaData.getJDBCMinorVersion());
            System.out.println(metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion());
            System.out.println(metaData.getURL());

            ResultSet rs = conn.createStatement().executeQuery(query);
            while(rs.next()) {
                StringBuffer result = new StringBuffer();
                result.append(String.format("%-5d", rs.getInt("id")));
                result.append(new SimpleDateFormat("YYYY-MM-dd").format(rs.getDate("date_value")) + " ");
                result.append(String.format("%-14.6f", rs.getDouble("unit_net")));
                result.append(String.format("%-14.6f", rs.getDouble("accumated_net0")));
                result.append(String.format("%-14.6f", rs.getDouble("accumated_net1")));
                result.append(String.format("%-14.6f", rs.getDouble("settlement")));

                System.out.println(result);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
