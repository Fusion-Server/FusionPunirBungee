package br.fusion.punir.eventos;

import br.fusion.punir.Main;
import br.fusion.punir.servicos.EnviarListaMotivos;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;

public class MensagemMotivos implements Listener {


    private Main plugin;

    public MensagemMotivos(Main plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void aoReceberMensagem(PluginMessageEvent e) {
        if (!e.getTag().equals("fp:motivos")) {
            return;
        }
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));

            String nomeStaff = in.readUTF();
            int permissao = in.readInt();

            ProxiedPlayer p = plugin.getProxy().getPlayer(nomeStaff);
            new EnviarListaMotivos().enviar(nomeStaff, permissao, p.getServer(), plugin);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



}
