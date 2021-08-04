package br.fusion.punir.servicos;

import br.fusion.punir.Main;
import br.fusion.punir.bd.BD;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;


public class RegistrarPunicao {


    public static void registrar(Main plugin, String nomeStaff, String nomeJogadorPunido, String motivo){
        ProxiedPlayer p = plugin.getProxy().getPlayer(nomeStaff);
        UUID idJogadorPunido = p.getUniqueId();

        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                int idPunicao = BD.getIDPunicaoPeloNome(motivo);
            }
        });

        // RESGATAR ID DA PUNIÇÃO COM BASE NO MOTIVO
        // SELECT * FROM punicao WHERE nome_punicao = ?

        // Verificar se o jogador ja tem esse tipo de ocorrencia registrada
        // SLA O QUE JOIN EM AMBAS

        // Registrar ou atualizar registro de punição do jogador





    }
}
