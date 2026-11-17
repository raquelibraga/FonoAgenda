package dado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConexao() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/fonoagenda?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true";
        String usuario = "root";
        String senha = "";
        return DriverManager.getConnection(url, usuario, senha);
    }
}