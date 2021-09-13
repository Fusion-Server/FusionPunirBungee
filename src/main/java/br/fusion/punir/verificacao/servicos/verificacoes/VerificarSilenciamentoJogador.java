package br.fusion.punir.verificacao.servicos.verificacoes;

import br.fusion.punir.bd.BD;
import br.fusion.punir.modelos.RegistroDePunicao;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VerificarSilenciamentoJogador implements VerificarPunicaoJogador {

    @Override
    public RegistroDePunicao verificar(ProxiedPlayer jogador, Server servidor) {
        Date agora = new Date();
        UUID idJogador = jogador.getUniqueId();

        List<RegistroDePunicao> registros = new BD().getSilenciamentoJogador(idJogador.toString(), servidor.getInfo().getName());
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
