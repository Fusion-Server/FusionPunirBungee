package br.fusion.punir.eventos;

import br.fusion.punir.Main;
import br.fusion.punir.bd.BD;
import br.fusion.punir.controladores.ControladorArquivoPunicoes;
import br.fusion.punir.modelos.RegistroDePunicao;
import br.fusion.punir.servicos.RegistrarPunicao;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VerificarBanimentoJogador implements Listener {
    private Main plugin;

    public VerificarBanimentoJogador(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void aoJogadorEntrar(ServerConnectedEvent e){
        Date agora = new Date();
        ProxiedPlayer p = e.getPlayer();
        UUID idJogador = p.getUniqueId();

        List<RegistroDePunicao> registros = BD.getBanimentosJogador(idJogador.toString(), e.getServer().getInfo().getName());
        if (registros == null || registros.size() == 0) {
            return;
        }
        registros.forEach(registro -> {
            if (registro.getDataFim().after(agora)) {
//                notificarJogadorBanido(p.getName(), registro.getIDPunicao(), registro.getNomePunicao(), registro.getDataFim(), e.getServer());
                desconectarJogador(p, registro);
                return;
            }
        });
    }


    private void notificarJogadorBanido(String jogador, int id, String motivo, Date dataFim, Server servidor) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);
            out.writeUTF(jogador);
            out.writeInt(id);
            out.writeUTF(motivo);
            out.writeUTF(dataFim.toString());

            servidor.sendData("fp:checarban", bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void desconectarJogador(ProxiedPlayer p, RegistroDePunicao registro){
        TextComponent textComponent = new TextComponent();
        SimpleDateFormat dataFormatador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaFormatador = new SimpleDateFormat("HH:mm:ss");
        String dataFormatada = dataFormatador.format(registro.getDataFim());
        String horaFormatada = horaFormatador.format(registro.getDataFim());
        textComponent.addExtra(ChatColor.RED + "Você foi banido deste servidor até " + dataFormatada + " às " + horaFormatada);
        textComponent.addExtra(ChatColor.RED + "\nMotivo: " + registro.getNomePunicao());
        textComponent.addExtra(ChatColor.RED + "\nID: #" + registro.getIDPunicao());
        textComponent.addExtra(ChatColor.RED + "\nCompartilhar o ID desta punição poderá afetar seu pedido de revisão.");
        p.disconnect(textComponent);
    }
}
