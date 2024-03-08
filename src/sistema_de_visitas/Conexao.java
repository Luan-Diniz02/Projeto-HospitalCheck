package sistema_de_visitas;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.sql.ResultSet;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class Conexao {
     
    public void cadastrarAcompanhante(String tabela, String cpf, String nome, String sexo, int idade, String categoria, String paciente, Date data, String horario,String entrada_liberada, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO " + tabela + " (cpf, nome, sexo, idade, categoria,acompanhando_visitando, data_visita, horario, entrada_liberada) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement psInsert = connection.prepareStatement(insertQuery);
        java.sql.Date dataSQL = new java.sql.Date(data.getTime());
        
        psInsert.setString(1, cpf);
        psInsert.setString(2, nome);
        psInsert.setString(3, sexo);
        psInsert.setInt(4, idade);
        psInsert.setString(5, categoria);
        psInsert.setString(6, paciente);
        psInsert.setDate(7, dataSQL);
        psInsert.setString(8, horario);
        psInsert.setString(9, entrada_liberada);
        
        psInsert.executeUpdate();
    }
    
        public void atualizarAcompanhante(String tabela, String nomeAcompanhante, String cpfConsulta, Connection connection) throws SQLException {
        String updateQuery = "UPDATE " + tabela + " SET acompanhante = ? WHERE CPF = ?";
        PreparedStatement psUpdate = connection.prepareStatement(updateQuery);
        
        psUpdate.setString(1, nomeAcompanhante);
        psUpdate.setString(2, cpfConsulta);
        
        psUpdate.executeUpdate();
    }
            
        
     public ResultSet buscarCPF(String tabela, long id, Connection connection) throws SQLException {
        String selectByIdQuery = "SELECT * FROM " + tabela + " WHERE CPF = ?";
        PreparedStatement psSelectById = connection.prepareStatement(selectByIdQuery);
        psSelectById.setLong(1, id);
        return psSelectById.executeQuery();
    }
    
        public boolean verificarAcompanhante(String tabela, String cpf_paciente, Connection connection) throws SQLException {
        String selectByIdQuery = "SELECT * FROM " + tabela + " WHERE CPF = ? AND acompanhante IS NOT NULL";
        PreparedStatement psSelectById = connection.prepareStatement(selectByIdQuery);
        psSelectById.setString(1, cpf_paciente);
        ResultSet resultSet = psSelectById.executeQuery();
        return resultSet.next();
    }
        
public int consultarVisitas(String tabela, String nome_paciente, String categoria, Connection connection, Date data) throws SQLException {
    int numeroVisitas = 0;

    try {
        String selectQuery = "SELECT COUNT(*) AS numero_visitas FROM " + tabela + " WHERE acompanhando_visitando = ? AND categoria = ? AND data_visita = ?";
        PreparedStatement psSelect = connection.prepareStatement(selectQuery);
        psSelect.setString(1, nome_paciente);
        psSelect.setString(2, categoria);
        psSelect.setDate(3, new java.sql.Date(data.getTime()));

        ResultSet resultado = psSelect.executeQuery();
        if (resultado.next()) {
            numeroVisitas = resultado.getInt("numero_visitas");
        }

        psSelect.close();
    } catch (SQLException e) {
        throw new SQLException("Erro ao consultar o número de visitas agendadas: " + e.getMessage());
    }

    return numeroVisitas;
}

        
    public void liberarEntrada(String tabela, String liberarEntrada, String nomeVisitante, String nomePaciente, Connection connection) throws SQLException {
    String updateQuery = "UPDATE " + tabela + " SET entrada_liberada = ? WHERE nome = ? AND acompanhando_visitando = ?";
    PreparedStatement psUpdate = connection.prepareStatement(updateQuery);
    
    psUpdate.setString(1, liberarEntrada);
    psUpdate.setString(2, nomeVisitante);
    psUpdate.setString(3, nomePaciente);
    
    psUpdate.executeUpdate();
}   
    
    public void imprimirTodosDados(String tabela, Connection connection, DefaultTableModel model) throws SQLException {
    String selectColumnsQuery = "SELECT nome, categoria,acompanhando_visitando, data_visita, horario FROM " + tabela;
    PreparedStatement psSelectColumns = connection.prepareStatement(selectColumnsQuery);
    ResultSet resultSet = psSelectColumns.executeQuery();

    ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();

    // Limpa a tabela antes de adicionar novos dados
    model.setRowCount(0);

    while (resultSet.next()) {
        Object[] rowData = new Object[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            rowData[i - 1] = resultSet.getObject(i);
        }
        model.addRow(rowData);
    }
}
  public void imprimirDadosPorNome(String tabela, Connection connection, DefaultTableModel model, String nomePaciente) throws SQLException {
    String selectColumnsQuery = "SELECT nome, categoria, data_visita, horario FROM " + tabela + " WHERE acompanhando_visitando = ?";
    PreparedStatement psSelectColumns = connection.prepareStatement(selectColumnsQuery);
    psSelectColumns.setString(1, nomePaciente);
    ResultSet resultSet = psSelectColumns.executeQuery();

    ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();

    // Limpa a tabela antes de adicionar novos dados
    model.setRowCount(0);

    while (resultSet.next()) {
        Object[] rowData = new Object[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            rowData[i - 1] = resultSet.getObject(i);
        }
        model.addRow(rowData);
    }

    // Atualiza a visualização da tabela após adicionar os dados
    SwingUtilities.invokeLater(() -> model.fireTableDataChanged());
}

public boolean verificarTrocaAcompanhante(String tabela, String nome_paciente, Connection connection, Date dataComparacao) throws SQLException {
    boolean trocaRealizada = false;

    try {
        String selectQuery = "SELECT COUNT(*) AS count FROM " + tabela + " WHERE acompanhando_visitando = ? AND DATE(data_visita) = ?";
        PreparedStatement psSelect = connection.prepareStatement(selectQuery);
        psSelect.setString(1, nome_paciente);
        psSelect.setDate(2, new java.sql.Date(dataComparacao.getTime()));

        ResultSet resultado = psSelect.executeQuery();
        if (resultado.next()) {
            int count = resultado.getInt("count");
            trocaRealizada = count > 0;
        }

        psSelect.close();
    } catch (SQLException e) {
        throw new SQLException("Erro ao verificar se a troca de acompanhante foi realizada na data especificada para o paciente: " + e.getMessage());
    }

    return trocaRealizada;
}


    public boolean autenticar(String login, String senha,String tabela, Connection connection) throws SQLException {
        boolean autenticado = false;

        try {
            String selectQuery = "SELECT COUNT(*) AS count FROM " + tabela +" WHERE login = ? AND senha = ?";
            PreparedStatement psSelect = connection.prepareStatement(selectQuery);
            psSelect.setString(1, login);
            psSelect.setString(2, senha);

            ResultSet resultado = psSelect.executeQuery();
            if (resultado.next()) {
                int count = resultado.getInt("count");
                autenticado = count > 0;
            }

            psSelect.close();
        } catch (SQLException e) {
            throw new SQLException("Erro ao autenticar usuario: " + e.getMessage());
        }

        return autenticado;
    }
}
    
