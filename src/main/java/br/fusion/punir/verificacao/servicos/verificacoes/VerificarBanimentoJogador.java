package br.fusion.punir.verificacao.servicos.verificacoes;

import br.fusion.punir.bd.BD;
import br.fusion.punir.modelos.RegistroDePunicao;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VerificarBanimentoJogador implements Listener, VerificarPunicaoJogador {


    @Override
    public RegistroDePunicao verificar(ProxiedPlayer jogador) {
        Date agora = new Date();
        UUID idJogador = jogador.getUniqueId();

        List<RegistroDePunicao> registros = BD.getBanimentosJogador(idJogador.toString(), jogador.getServer().getInfo().getName());
        if (registros == null || registros.size() == 0) {
            return null;
        }

        for (RegistroDePunicao registro : registros) {
            if (registro.getDataFim().after(agora)) {
                return registro;
            }
        }
        return null;
    }



}
