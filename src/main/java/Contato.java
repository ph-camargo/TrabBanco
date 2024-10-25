import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Contato {
    private Integer idContato;
    private String nome;
    private String eMail;
    private String telefone;
    private Connection conexao;

    // Construtor com conexão
    public Contato(Connection conexao) {
        this.conexao = conexao;
    }

    // Construtor vazio
    public Contato() {}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getEMail() {
        return this.eMail;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public void gravarContato() throws SQLException {
        String sql = "INSERT INTO Contato (nome, eMail, telefone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, eMail);
            stmt.setString(3, telefone);
            stmt.executeUpdate();
        }
    }

    public void atualizarContato() throws SQLException {
        String sql = "UPDATE Contato SET nome = ?, eMail = ?, telefone = ? WHERE idContato = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, eMail);
            stmt.setString(3, telefone);
            stmt.setInt(4, idContato);
            stmt.executeUpdate();
        }
    }

    public void deletarContato(int idContato) throws SQLException {
        String sql = "DELETE FROM Contato WHERE idContato = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idContato);
            stmt.executeUpdate();
        }
    }

    public void deletarContato() throws SQLException {
        if (idContato != null) {
            deletarContato(this.idContato);
        }
    }

    public boolean obterContatoPeloId(int idContato) throws SQLException {
        String sql = "SELECT * FROM Contato WHERE idContato = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idContato);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idContato = rs.getInt("idContato");
                    this.nome = rs.getString("nome");
                    this.eMail = rs.getString("eMail");
                    this.telefone = rs.getString("telefone");
                    return true;
                }
            }
        }
        return false;
    }

    public ResultSet obterContatos() throws SQLException {
        String sql = "SELECT * FROM Contato";
        Statement stmt = conexao.createStatement();
        return stmt.executeQuery(sql);
    }

    public List<Contato> obterListaContato() throws SQLException {
        List<Contato> lista = new ArrayList<>();
        String sql = "SELECT * FROM Contato";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Contato contato = new Contato();
                contato.idContato = rs.getInt("idContato");
                contato.nome = rs.getString("nome");
                contato.eMail = rs.getString("eMail");
                contato.telefone = rs.getString("telefone");
                lista.add(contato);
            }
        }
        return lista;
    }

    public List<Contato> pesquisarContato(String termo) throws SQLException {
        List<Contato> lista = new ArrayList<>();
        String sql = "SELECT * FROM Contato WHERE nome LIKE ? OR eMail LIKE ? OR telefone LIKE ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, "%" + termo + "%");
            stmt.setString(3, "%" + termo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contato contato = new Contato();
                    contato.idContato = rs.getInt("idContato");
                    contato.nome = rs.getString("nome");
                    contato.eMail = rs.getString("eMail");
                    contato.telefone = rs.getString("telefone");
                    lista.add(contato);
                }
            }
        }
        return lista;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "idContato=" + idContato +
                ", nome='" + nome + '\'' +
                ", eMail='" + eMail + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
