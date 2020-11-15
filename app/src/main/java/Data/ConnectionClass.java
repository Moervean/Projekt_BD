package Data;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    String classs = "com.my";

    String url = "jdbc:mysql://192.168.0.7:3306/dietary";
    String un = "vox";
    String password = "789";
    ConnectionSource connectionSource;
    public ConnectionSource buildConnect(){
        try {
            connectionSource = new JdbcConnectionSource(url,un,password);
            return connectionSource;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
