package br.fusion.punir.eventos;

import br.fusion.punir.Main;
import br.fusion.punir.servicos.EnviarListaMotivos;
import br.fusion.punir.servicos.RegistrarPunicao;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class NovaPunicao implements Listener {

    private Main plugin;

    public NovaPunicao(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void aoReceberMensagem(PluginMessageEvent e) {
        if (!e.getTag().equals("fp:registrarpunicao")) {
            return;
        }
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));

            String servidor = in.readUTF();
            String nomeStaff = in.readUTF();
            String jogadorPunido = in.readUTF();
            UUID idJogadorPunido = UUID.fromString(in.readUTF());
            String motivo = in.readUTF();
            String provas = in.readUTF();
            int permissao = in.readInt();

            new RegistrarPunicao().registrar(plugin, servidor, nomeStaff, jogadorPunido, idJogadorPunido, motivo, permissao, provas);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
