package joao.ifpr.foz.ifprstore.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


public static Connection connection;
    public static Connection getConnection(){
        try{
            String url = "jdbc:mysql://localhost/ifpr_store";
            String pass = "johnkart01";
            String user = "root";

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,pass);
            return  connection;

        }catch(SQLException e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(){
//        try{
//            connection.close();
//        }catch (SQLException e ){
//            throw new RuntimeException("Nao foi possivel encerrar a conecao "+e.getMessage());
//        }
    }
}
