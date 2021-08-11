package br.fusion.punir.servicos;

import br.fusion.punir.Main;
import br.fusion.punir.bd.BD;
import br.fusion.punir.controladores.ControladorArquivoPunicoes;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.IOException;
import java.util.UUID;


public class RegistrarPunicao {


    public void registrar(Main plugin, String servidor, String nomeStaff, String nomeJogadorPunido, String motivo){
        ProxiedPlayer p = plugin.getProxy().getPlayer(nomeStaff);
        UUID idJogadorPunido = p.getUniqueId();

        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    int idPunicao = ControladorArquivoPunicoes.getPunicaoID(plugin, motivo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // RESGATAR ID DA PUNIÇÃO COM BASE NO MOTIVO
        // SELECT * FROM punicao WHERE nome_punicao = ?

        // Verificar se o jogador ja tem esse tipo de ocorrencia registrada
        // SLA O QUE JOIN EM AMBAS

        // Registrar ou atualizar registro de punição do jogador





    }
}
