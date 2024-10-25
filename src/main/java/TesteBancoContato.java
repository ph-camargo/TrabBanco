import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TesteBancoContato {
    public static void main(String[] args) {
        try {
            // Conectando ao banco
            Banco banco = new Banco("localhost", "3306", "usuario", "senha");
            if (banco.conectado()) {
                System.out.println("Conectado ao banco com sucesso!");

                Connection conexao = banco.obterConexao();

                // Criando um novo contato
                Contato contato = new Contato(conexao);
                contato.setNome("João Silva");
                contato.setEMail("joao.silva@email.com");
                contato.setTelefone("9999-9999");
                contato.gravarContato();

                // Listando todos os contatos
                List<Contato> contatos = contato.obterListaContato();
                for (Contato c : contatos) {
                    System.out.println(c);
                }

                // Atualizando um contato
                contato.setNome("João Souza");
                contato.atualizarContato();

                // Deletando um contato
                contato.deletarContato();

                banco.encerraConexao();
            } else {
                System.out.println("Erro de conexão: " + banco.obterMensagemErro());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
