package br.fusion.punir.verificacao;

import br.fusion.punir.modelos.RegistroDePunicao;
import br.fusion.punir.verificacao.servicos.ExpulsarJogadorDoServidor;
import br.fusion.punir.verificacao.servicos.NotificarSilenciamento;
import br.fusion.punir.verificacao.servicos.verificacoes.VerificarBanimentoJogador;
import br.fusion.punir.verificacao.servicos.verificacoes.VerificarSilenciamentoJogador;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class VerificarJogador {


    public static void verificarJogador(ProxiedPlayer p) {

        VerificarBanimentoJogador verificarBanimentoJogador = new VerificarBanimentoJogador();
        RegistroDePunicao registroDePunicao = verificarBanimentoJogador.verificar(p);
        if (registroDePunicao != null) {
            ExpulsarJogadorDoServidor.expulsar(p, registroDePunicao);
            return;
        }

        VerificarSilenciamentoJogador verificarSilenciamentoJogador = new VerificarSilenciamentoJogador();
        registroDePunicao = verificarSilenciamentoJogador.verificar(p);
        if (registroDePunicao != null) {
            NotificarSilenciamento.notificar(p, registroDePunicao);
        }
    }
}
