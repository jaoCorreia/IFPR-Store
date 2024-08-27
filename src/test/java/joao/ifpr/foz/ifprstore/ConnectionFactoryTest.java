package joao.ifpr.foz.ifprstore;

import joao.ifpr.foz.ifprstore.connection.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionFactoryTest {

    @Test

    public void deveRealizarAConexaoSemExcecao(){

        Connection connection = ConnectionFactory.getConnection();

    }


    public static void closeConnection(){
            ConnectionFactory.closeConnection();

    }
}
