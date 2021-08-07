package br.fusion.punir.eventos;

import br.fusion.punir.Main;

import br.fusion.punir.verificacao.VerificarJogador;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JogadorEntrarEmServidor implements Listener {
    private Main plugin;

    public JogadorEntrarEmServidor(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void aoJogadorEntrar(ServerConnectedEvent e){
        VerificarJogador.verificarJogador(e.getPlayer(), e.getServer());
    }
}
