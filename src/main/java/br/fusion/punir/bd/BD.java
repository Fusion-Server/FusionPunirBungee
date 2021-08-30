package br.fusion.punir.bd;

import br.fusion.punir.modelos.Punicao;
import br.fusion.punir.modelos.RegistroDePunicao;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BD {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://158.69.23.151:3306/punicoes");
        ds.setUsername("u7_TWJkLCUfq2");
        ds.setPassword("tluToIbv@On5c4uG.wmaUUQn");
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(30);

    }

    public static Connection getConexao() throws SQLException {
        return ds.getConnection();
    }

    public static List<Punicao> getPunicoes() {
        List<Punicao> punicoes = new ArrayList<>();
        try {
            Connection conexao = getConexao();
            PreparedStatement statement = conexao.prepareStatement("SELECT * FROM punicoes");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<RegistroDePunicao> getBanimentosJogador(String idJogador, String servidor) {
        List<RegistroDePunicao> banimentos = new ArrayList<>();
        try {
            Connection conexao = getConexao();
            PreparedStatement statement = conexao.prepareStatement("SELECT j.nome, p.id_punicao, b.data_fim, c.codigo_punicao, c.nome_punicao FROM jogadores j " +
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
            while (rs.next()) {
                int id = rs.getInt("c.codigo_punicao");
                String nomePunicao = rs.getString("c.nome_punicao");
                String nomeJogador = rs.getString("j.nome");
                RegistroDePunicao registroDePunicao = new RegistroDePunicao(id, nomePunicao, UUID.fromString(idJogador), nomeJogador, servidor);
                registroDePunicao.setDataFim(rs.getTimestamp("b.data_fim"));
                registroDePunicao.setIdUnicoPunicao(rs.getInt("p.id_punicao"));
                banimentos.add(registroDePunicao);
            }
            statement.close();
            rs.close();
            conexao.close();
            return banimentos;
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<RegistroDePunicao> getSilenciamentoJogador(String idJogador, String servidor) {
        List<RegistroDePunicao> silenciamentos = new ArrayList<>();
        try {
            Connection conexao = getConexao();
            PreparedStatement statement = conexao.prepareStatement("SELECT j.nome, p.id_punicao, s.data_fim, c.codigo_punicao, c.nome_punicao FROM jogadores j " +
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
            while (rs.next()) {
                int id = rs.getInt("c.codigo_punicao");
                String nomePunicao = rs.getString("c.nome_punicao");
                String nomeJogador = rs.getString("j.nome");
                RegistroDePunicao registroDePunicao = new RegistroDePunicao(id, nomePunicao, UUID.fromString(idJogador), nomeJogador, servidor);
                registroDePunicao.setDataFim(rs.getTimestamp("s.data_fim"));
                registroDePunicao.setIdUnicoPunicao(rs.getInt("p.id_punicao"));
                silenciamentos.add(registroDePunicao);
            }
            statement.close();
            rs.close();
            conexao.close();
            return silenciamentos;
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public static int getOcorrenciasPunicaoJogador(int idPunicao, UUID idJogador) {
        try {
            Connection conexao = getConexao();
            PreparedStatement statement = conexao.prepareStatement("SELECT p.ocorrencias FROM jogadores j " +
                    "JOIN jogadores_punicoes p " +
                    "ON j.id_usuario = p.id_usuario " +
                    "WHERE j.uuid = ? AND p.codigo_punicao = ?");
            statement.setString(1, idJogador.toString());
            statement.setInt(2, idPunicao);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("p.ocorrencias");
            }


            statement.close();
            rs.close();
            conexao.close();
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void adicionarBanimento(RegistroDePunicao registro, int ocorrencias) {
        try {
            Connection conexao = getConexao();
            int idUnico = getIDUnicoPunicao(conexao, registro);
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO banimentos VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, idUnico);
            statement.setString(2, registro.getAplicador());
            statement.setString(3, registro.getSupervisorResponsavel());
            statement.setDate(4, new Date(registro.getData().getTime()));
            statement.setDate(5, new Date(registro.getDataFim().getTime()));
            statement.setString(6, registro.getProvas());
            statement.setInt(7, ocorrencias);
            statement.executeUpdate();
            statement.close();
            adicionarOcorrencia(conexao, idUnico);
            statement.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void adicionarSilenciamento(RegistroDePunicao registro, int ocorrencias) {
        try {
            Connection conexao = getConexao();
            int idUnico = getIDUnicoPunicao(conexao, registro);
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO silenciamentos VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, idUnico);
            statement.setString(2, registro.getAplicador());
            statement.setString(3, registro.getSupervisorResponsavel());
            statement.setDate(4, new Date(registro.getData().getTime()));
            statement.setDate(5, new Date(registro.getDataFim().getTime()));
            statement.setString(6, registro.getProvas());
            statement.setInt(7, ocorrencias);
            statement.executeUpdate();
            statement.close();
            adicionarOcorrencia(conexao, idUnico);
            statement.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void adicionarOcorrencia(Connection conexao, int idUnico){
        try{
            PreparedStatement statement = conexao.prepareStatement("UPDATE jogadores_punicoes SET ocorrencias = ocorrencias + 1 WHERE id_punicao = ?");
            statement.setInt(1, idUnico);
            statement.executeUpdate();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private static int getIDUnicoPunicao(Connection conexao, RegistroDePunicao registro) {
        try {
            int id = getIDJogador(conexao, registro.getIdJogador(), registro.getNomeJogador());
            PreparedStatement statement = conexao.prepareStatement("SELECT id_punicao FROM jogadores_punicoes "+
                    "WHERE id_usuario = ? AND codigo_punicao = ? AND servidor = ?");
            statement.setInt(1, id);
            statement.setInt(2, registro.getIDPunicao());
            statement.setString(3, registro.getServidor());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_punicao");
            }
            rs.close();
            statement.close();
            statement = conexao.prepareStatement("INSERT INTO jogadores_punicoes VALUES (?, default, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.setString(2, registro.getServidor());
            statement.setInt(3, 0);
            statement.setInt(4, registro.getIDPunicao());
            System.out.println("Id da punicao " + registro.getIDPunicao());
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
            statement.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;

    }
    private static int getIDJogador(Connection conexao, UUID idJogador, String nomeJogador){
        try{
            PreparedStatement statement = conexao.prepareStatement("SELECT id_usuario FROM jogadores "+
                    "WHERE uuid = ?");
            statement.setString(1, idJogador.toString());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Retornando o id if1 " + id);
                return rs.getInt("id_usuario");
            }
            rs.close();
            statement.close();
            statement = conexao.prepareStatement("INSERT INTO jogadores VALUES (default, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, nomeJogador);
            statement.setString(2, idJogador.toString());
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                System.out.println("Retornando o id if2" + id);
                return id;
            }
            statement.close();
            rs.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

}
