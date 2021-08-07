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
            PreparedStatement statement = conexao.prepareStatement("SELECT b.id_banimento, u.uuid, d.data_fim, b.aplicador, b.supervisor_responsavel, p.nome_punicao, b.provas, d.ocorrencia, p.codigo_punicao, b.servidor " +
                    "FROM usuarios_punidos u " +
                    "JOIN banimento b " +
                    "ON u.id_usuario = b.id_usuario " +
                    "JOIN banimento_detalhes d " +
                    "ON d.id_banimento = b.id_banimento " +
                    "JOIN punicoes p " +
                    "ON b.codigo_punicao = p.codigo_punicao " +
                    "WHERE u.uuid = ? AND b.servidor = ?");
            statement.setString(1, idJogador);
            statement.setString(2, servidor);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("p.codigo_punicao");
                String nomePunicao = rs.getString("p.nome_punicao");
                RegistroDePunicao registroDePunicao = new RegistroDePunicao(id, nomePunicao, UUID.fromString(idJogador), servidor);
                registroDePunicao.setDataFim(rs.getTimestamp("d.data_fim"));
                registroDePunicao.setIdUnicoPunicao(rs.getInt("b.id_banimento"));
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
            PreparedStatement statement = conexao.prepareStatement("SELECT u.uuid, s.id_silenciamento, d.data_fim, s.aplicador, s.supervisor_responsavel, p.nome_punicao, s.provas, d.ocorrencia, p.codigo_punicao, s.servidor " +
                    "FROM usuarios_punidos u " +
                    "JOIN silenciamento s " +
                    "ON u.id_usuario = s.id_usuario " +
                    "JOIN silenciamento_detalhes d " +
                    "ON d.id_silenciamento = s.id_silenciamento " +
                    "JOIN punicoes p " +
                    "ON s.codigo_punicao = p.codigo_punicao " +
                    "WHERE u.uuid = ? AND s.servidor = ?");
            statement.setString(1, idJogador);
            statement.setString(2, servidor);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("p.codigo_punicao");
                String nomePunicao = rs.getString("p.nome_punicao");
                RegistroDePunicao registroDePunicao = new RegistroDePunicao(id, nomePunicao, UUID.fromString(idJogador), servidor);
                registroDePunicao.setDataFim(rs.getTimestamp("d.data_fim"));
                registroDePunicao.setIdUnicoPunicao(rs.getInt("s.id_silenciamento"));
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
