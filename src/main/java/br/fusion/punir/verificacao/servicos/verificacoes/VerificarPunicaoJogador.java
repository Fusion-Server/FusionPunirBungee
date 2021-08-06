package br.fusion.punir.verificacao.servicos.verificacoes;

import br.fusion.punir.modelos.RegistroDePunicao;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface VerificarPunicaoJogador {


    RegistroDePunicao verificar(ProxiedPlayer jogador);
}
