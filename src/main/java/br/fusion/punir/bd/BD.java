package br.fusion.punir.bd;

import br.fusion.punir.modelos.Punicao;
import br.fusion.punir.modelos.RegistroDePunicao;
import br.fusion.punir.servicos.RegistrarPunicao;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BD {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/punicoes");
        ds.setUsername("root");
        ds.setPassword("123456a");
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(30);

    }

    public static Connection getConexao() throws SQLException {
        return ds.getConnection();
    }

    public static List<Punicao> getPunicoes(){
        List<Punicao> punicoes = new ArrayList<>();
        try{
            Connection conexao = getConexao();
            PreparedStatement statement = conexao.prepareStatement("SELECT * FROM punicoes");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int idPunicao = rs.getInt("codigo_punicao");
                String nomePunicao = rs.getString("nome_punicao");
                int permissao = rs.getInt("permissao_necessaria");
                Punicao punicao = new Punicao(idPunicao, nomePunicao, permissao);
                punicoes.add(punicao);
            }
            statement.close();
            rs.close();
            conexao.close();
            return punicoes;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<RegistroDePunicao> getBanimentosJogador(String idJogador, String servidor){
        List<RegistroDePunicao> banimentos = new ArrayList<>();
        try{
            Connection conexao = getConexao();
            PreparedStatement statement = conexao.prepareStatement("SELECT p.id_punicao, p.data_fim, c.codigo_punicao, c.nome_punicao FROM jogadores j " +
                    "JOIN jogadores_punicoes p " +
                    "ON j.id_usuario = p.id_usuario " +
                    "JOIN punicoes c " +
                    "ON c.codigo_punicao = p.codigo_punicao " +
                    "JOIN banimentos b " +
                    "ON b.id_punicao = p.id_punicao " +
                    "WHERE j.uuid = ? AND p.servidor = ?");
            statement.setString(1, idJogador);
            statement.setString(2, servidor);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("c.codigo_punicao");
                String nomePunicao = rs.getString("c.nome_punicao");
                RegistroDePunicao registroDePunicao = new RegistroDePunicao(id, nomePunicao, UUID.fromString(idJogador), servidor);
                registroDePunicao.setDataFim(rs.getTimestamp("p.data_fim"));
                registroDePunicao.setIdUnicoPunicao(rs.getInt("p.id_punicao"));
                banimentos.add(registroDePunicao);
            }
            statement.close();
            rs.close();
            conexao.close();
            return banimentos;
        }catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<RegistroDePunicao> getSilenciamentoJogador(String idJogador, String servidor){
        List<RegistroDePunicao> silenciamentos = new ArrayList<>();
        try{
            Connection conexao = getConexao();
            PreparedStatement statement = conexao.prepareStatement("SELECT p.id_punicao, p.data_fim, c.codigo_punicao, c.nome_punicao FROM jogadores j " +
                    "JOIN jogadores_punicoes p " +
                    "ON j.id_usuario = p.id_usuario " +
                    "JOIN punicoes c " +
                    "ON c.codigo_punicao = p.codigo_punicao " +
                    "JOIN silenciamentos s " +
                    "ON s.id_punicao = p.id_punicao " +
                    "WHERE j.uuid = ? AND p.servidor = ?");
            statement.setString(1, idJogador);
            statement.setString(2, servidor);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("c.codigo_punicao");
                String nomePunicao = rs.getString("c.nome_punicao");
                RegistroDePunicao registroDePunicao = new RegistroDePunicao(id, nomePunicao, UUID.fromString(idJogador), servidor);
                registroDePunicao.setDataFim(rs.getTimestamp("p.data_fim"));
                registroDePunicao.setIdUnicoPunicao(rs.getInt("p.id_punicao"));
                silenciamentos.add(registroDePunicao);
            }
            statement.close();
            rs.close();
            conexao.close();
            return silenciamentos;
        }catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
