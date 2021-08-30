package br.fusion.punir.servicos;

import br.fusion.punir.Main;
import br.fusion.punir.bd.BD;
import br.fusion.punir.controladores.ControladorArquivoPunicoes;
import br.fusion.punir.controladores.ControladorSistemaDePunicao;
import br.fusion.punir.modelos.RegistroDePunicao;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;


public class RegistrarPunicao {


    public void registrar(Main plugin, String servidor, String nomeStaff, String nomeJogadorPunido, UUID idJogadorPunido,String motivo, int permissao, String provas){
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            try {
                int idPunicao = ControladorArquivoPunicoes.getPunicaoID(motivo);
                if(permissao >= 3){
                    RegistroDePunicao registro = new RegistroDePunicao(idPunicao, motivo, idJogadorPunido, nomeJogadorPunido, servidor);
                    registro.setAplicador(nomeStaff);
                    registro.setSupervisorResponsavel(nomeStaff);
                    registro.setData(new Date());
                    registro.setProvas(provas);
                    new ControladorSistemaDePunicao().executarPunicao(registro);
                    return;
                }
//                    Enviar pro discord
//                    RegistroDePunicao registroDePunicao = new RegistroDePunicao(idPunicao, motivo, idJogadorPunido, nomeJogadorPunido);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // RESGATAR ID DA PUNIÇÃO COM BASE NO MOTIVO
        // SELECT * FROM punicao WHERE nome_punicao = ?

        // Verificar se o jogador ja tem esse tipo de ocorrencia registrada
        // SLA O QUE JOIN EM AMBAS

        // Registrar ou atualizar registro de punição do jogador





    }
}
