package br.fusion.punir.bd;

import br.fusion.punir.modelos.Punicao;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BD {
//    private static final String DRIVER = "com.mysql.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://localhost:3306/punicoes";
//    private static final String USUARIO = "root";
//    private static final String SENHA = "123456a";
//    private static Connection conexao;


    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/punicoes");
        ds.setUsername("root");
        ds.setPassword("123456a");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);

    }

    public static Connection getConexao() throws SQLException {
        return ds.getConnection();
    }

//    private static void conectar() {
//        try{
//            Class.forName(DRIVER);
//            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    public static List<String> motivosDisponiveisPorPermissao(int permissao){
//        List<String> motivosDisponiveis = new ArrayList<>();
//        try{
//            PreparedStatement statement = getConexao().prepareStatement("SELECT * FROM punicoes WHERE permissao_necessaria <= ?");
//            statement.setInt(1, permissao);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()){
//                motivosDisponiveis.add(rs.getString("nome_punicao"));
//            }
//            return motivosDisponiveis;
//        }catch (SQLException e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static List<Punicao> getPunicoes(){
        List<Punicao> punicoes = new ArrayList<>();
        try{
            PreparedStatement statement = getConexao().prepareStatement("SELECT * FROM punicoes");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int idPunicao = rs.getInt("codigo_punicao");
                String nomePunicao = rs.getString("nome_punicao");
                int permissao = rs.getInt("permissao_necessaria");
                Punicao punicao = new Punicao(idPunicao, nomePunicao, permissao);
                punicoes.add(punicao);
            }
            return punicoes;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

//    public static int getIDPunicaoPeloNome(String motivo){
//        try{
//            PreparedStatement statement = getConexao().prepareStatement("SELECT * FROM punicoes WHERE nome_punicao = ?");
//            statement.setString(1, motivo);
//            ResultSet rs = statement.executeQuery();
//            if(rs.next()){
//             return rs.getInt("codigo_punicao");
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//
//        return 0;
//    }

}
