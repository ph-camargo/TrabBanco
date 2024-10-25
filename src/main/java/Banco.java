import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {
    private Connection conexao;
    private boolean estaConectado;
    private String mensagemErro;



    // Construtor com todos os parâmetros
    public Banco(String servidor, String porta, String usuario, String senha) {

        try {
            String url = "jdbc:mysql://" + localhost + ":" + 3306 + "/agenda_contatos";
            this.conexao = DriverManager.getConnection(url, usuario, senha);
            this.estaConectado = true;
        } catch (SQLException e) {
            this.mensagemErro = e.getMessage();
            this.estaConectado = false;
        }
    }

    // Construtor com usuário e senha
    public Banco(String usuario, String senha) {
        this("localhost", "3306", usuario, senha);
    }

    public Connection obterConexao() {
        return this.conexao;
    }

    public boolean conectado() {
        return this.estaConectado;
    }

    public String obterMensagemErro() {
        return this.mensagemErro;
    }

    public void encerraConexao() {
        try {
            if (this.conexao != null && !this.conexao.isClosed()) {
                this.conexao.close();
            }
            this.estaConectado = false;
        } catch (SQLException e) {
            this.mensagemErro = e.getMessage();
        }
    }
}
