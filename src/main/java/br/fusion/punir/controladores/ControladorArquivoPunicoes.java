package br.fusion.punir.controladores;

import br.fusion.punir.Main;
import br.fusion.punir.bd.BD;
import br.fusion.punir.modelos.Punicao;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorArquivoPunicoes {


    public static void iniciarArquivo(Main plugin) {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        File file = new File(plugin.getDataFolder(), "punicoes.yml");


        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream("punicoes.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            salvarPunicoes(plugin, BD.getPunicoes());
        } catch (IOException e) {
            System.out.println("Nao foi possivel salvar as punicoes");
            e.printStackTrace();
        }
    }


    public static void salvarPunicoes(Main plugin, List<Punicao> punicoes) throws IOException {
        Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "punicoes.yml"));
        for (Punicao punicao : punicoes) {
            String path = "Punições." + "ID." + punicao.getID();
            if (configuration.contains(path)) {
                System.out.println("Punicao ja salva: " + punicao.getID());
                continue;
            }
            configuration.set(path + ".Motivo", punicao.getNome());
            configuration.set(path + ".Permissão", punicao.getPermissao());
            configuration.set(path + ".Ações" + ".1" + ".Tipo", "BANIMENTO");
            configuration.set(path + ".Ações" + ".1" + ".Duração", "0");
        }
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(plugin.getDataFolder(), "punicoes.yml"));
    }

    public static List<String> motivosDisponiveisPorPermissao(Main plugin, int permissao) throws IOException {
        List<String> motivosDisponiveis = new ArrayList<>();

        Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "punicoes.yml"));
        Configuration sessaoIDS = configuration.getSection("Punições").getSection("ID");

        for(String punicao : sessaoIDS.getKeys()){

            Configuration sessaoPunicao = sessaoIDS.getSection(punicao);
            if(sessaoPunicao.getInt("Permissão") <= permissao){
                motivosDisponiveis.add(sessaoPunicao.getString("Motivo"));
            }
        }

        return motivosDisponiveis;
    }

    public static int getPunicaoID(Main plugin, String motivo) throws IOException {
        Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "punicoes.yml"));
        Configuration sessaoIDS = configuration.getSection("Punições").getSection("ID");

        for(String punicao : sessaoIDS.getKeys()){
            Configuration sessaoPunicao = sessaoIDS.getSection(punicao);
            if(sessaoPunicao.getString("Motivo").equals(motivo)){
                return Integer.parseInt(punicao);
            }
        }
        return 0;
    }
}
