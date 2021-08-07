package br.fusion.punir.verificacao;

import br.fusion.punir.modelos.RegistroDePunicao;
import br.fusion.punir.verificacao.servicos.ExpulsarJogadorDoServidor;
import br.fusion.punir.verificacao.servicos.NotificarSilenciamento;
import br.fusion.punir.verificacao.servicos.verificacoes.VerificarBanimentoJogador;
import br.fusion.punir.verificacao.servicos.verificacoes.VerificarSilenciamentoJogador;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

public class VerificarJogador {


    public static void verificarJogador(ProxiedPlayer p, Server servidor) {
        ProxyServer proxy = ProxyServer.getInstance();

        proxy.getScheduler().runAsync(proxy.getPluginManager().getPlugin("FusionPunirBungee"), new Runnable() {
            @Override
            public void run() {
                VerificarBanimentoJogador verificarBanimentoJogador = new VerificarBanimentoJogador();
                RegistroDePunicao registroDePunicao = verificarBanimentoJogador.verificar(p, servidor);
                if (registroDePunicao != null) {
                    ExpulsarJogadorDoServidor.expulsar(p, registroDePunicao);
                    return;
                }

                VerificarSilenciamentoJogador verificarSilenciamentoJogador = new VerificarSilenciamentoJogador();
                registroDePunicao = verificarSilenciamentoJogador.verificar(p, servidor);
                if (registroDePunicao != null) {
                    NotificarSilenciamento.notificar(p, registroDePunicao, servidor);
                }
            }
        });
    }
}
