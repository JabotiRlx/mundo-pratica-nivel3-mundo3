/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PessoaFisicaDAO {
    private ConectorBD conector;
    private SequenceManager sequenceManager;

    public PessoaFisicaDAO() {
        this.conector = new ConectorBD();
        this.sequenceManager = new SequenceManager();
    }

    public PessoaFisica getPessoa(int id) {
    
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
   
        try {
            connection = conector.getConnection();
            String sql = "SELECT * FROM Pessoa right join PessoaFisica on idPessoa = Pessoa_idPessoa where idPessoa = ?";
            statement = conector.getPrepared( sql, connection );
            statement.setInt(1, id);
            
            resultSet = conector.getSelect( statement );
            
            while(resultSet.next())
            {
                
                return new PessoaFisica( 
                        resultSet.getInt("idPessoa"), 
                        resultSet.getString("nome"), 
                        resultSet.getString("logradouro") , 
                        resultSet.getString("cidade") , 
                        resultSet.getString("estado") ,  
                        resultSet.getString("telefone") ,  
                        resultSet.getString("email") , 
                        resultSet.getString("CPF")  
                );
            }
 
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratamento de exceções
        } finally {
            conector.closeAll(connection, statement, resultSet);
        }
        return null;

    }

    public ArrayList<PessoaFisica> getPessoas() {
   
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<PessoaFisica> pessoas = new ArrayList<>();
   
        try {
            connection = conector.getConnection();
            String sql = "SELECT * FROM Pessoa right join PessoaFisica on idPessoa = Pessoa_idPessoa";
            statement = conector.getPrepared( sql, connection );
            resultSet = conector.getSelect( statement );
            
            while(resultSet.next())
            {
                pessoas.add( new PessoaFisica( 
                        resultSet.getInt("idPessoa"), 
                        resultSet.getString("nome"), 
                        resultSet.getString("logradouro") , 
                        resultSet.getString("cidade") , 
                        resultSet.getString("estado") ,  
                        resultSet.getString("telefone") ,  
                        resultSet.getString("email") , 
                        resultSet.getString("CPF")  
                ));
            }
            
            return pessoas;
 
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratamento de exceções
        } finally {
            conector.closeAll(connection, statement, resultSet);
        }
        return null;
    }

    public void incluir(PessoaFisica pessoa) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            int nextId = sequenceManager.getValue("sequencia_pessoa");
            pessoa.setId(nextId); // Define o ID da pessoa física
            
            connection = conector.getConnection();
            
            String sql = """
                          INSERT INTO [dbo].[Pessoa]
                          (idPessoa
                          ,nome
                          ,[logradouro]
                          ,[cidade]
                          ,[estado]
                          ,[telefone]
                          ,[email])
                          VALUES
                          ( ?
                          , ?
                          , ?
                          , ?
                          , ? 
                          , ?
                          , ? );
                         
                         INSERT INTO [dbo].[PessoaFisica]
                          ([Pessoa_idPessoa]
                          ,[CPF])
                          VALUES
                          ( ?
                          , ? );                         
                         """;
            
            statement = conector.getPrepared( sql, connection );
            
            statement.setInt(1, pessoa.getId() );
            statement.setString(2, pessoa.getNome());
            statement.setString(3, pessoa.getLogradouro());
            statement.setString(4, pessoa.getCidade());
            statement.setString(5, pessoa.getEstado());
            statement.setString(6, pessoa.getTelefone());
            statement.setString(7, pessoa.getEmail());
            statement.setInt(8, pessoa.getId());
            statement.setString(9, pessoa.getCpf());
           
            statement.executeUpdate();

        } catch (SQLException e) {
         
            e.printStackTrace();
            // Tratamento de exceções
        } finally {
            conector.closeConnection(connection);
            conector.closeStatement(statement);
        }
    }

    public void alterar( int id , PessoaFisica pessoa) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
                 
            connection = conector.getConnection();
            
            String sql = """
                          UPDATE [dbo].[Pessoa]
                          SET 
                           nome = ?
                          ,[logradouro] = ?
                          ,[cidade] = ?
                          ,[estado] = ?
                          ,[telefone] = ?
                          ,[email]  = ? 
                         where idPessoa = ?
                                                  
                         UPDATE [dbo].[PessoaFisica]
                          SET  [CPF] = ? 
                           where Pessoa_idPessoa = ?                        
                         """;
            
            statement = conector.getPrepared( sql, connection );
            
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getLogradouro());
            statement.setString(3, pessoa.getCidade());
            statement.setString(4, pessoa.getEstado());
            statement.setString(5, pessoa.getTelefone());
            statement.setString(6, pessoa.getEmail());
            statement.setInt(7, id );
            statement.setString(8, pessoa.getCpf());
            statement.setInt(9, id );
           
            statement.executeUpdate();

        } catch (SQLException e) {
         
            e.printStackTrace();
            // Tratamento de exceções
        } finally {
            conector.closeConnection(connection);
            conector.closeStatement(statement);
        }
    }

    public void excluir(int id) {
        // Lógica para remoção de uma pessoa física do banco em ambas as tabelas
        Connection connection = null;
        PreparedStatement statement = null;

        try {
                 
            connection = conector.getConnection();
            
            String sql = """
                          DELETE FROM [Pessoa] WHERE idPessoa = ?
                          
                          DELETE FROM [PessoaFisica] WHERE pessoa_idPessoa = ?                        
                         """;
            
            statement = conector.getPrepared( sql, connection );
            
            statement.setInt(1, id );
            statement.setInt(2, id );
   
           
            statement.executeUpdate();

        } catch (SQLException e) {
         
            e.printStackTrace();
            // Tratamento de exceções
        } finally {
            conector.closeConnection(connection);
            conector.closeStatement(statement);
        }
    }
}
