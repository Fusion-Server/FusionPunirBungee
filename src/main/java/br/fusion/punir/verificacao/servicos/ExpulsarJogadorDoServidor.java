package br.fusion.punir.verificacao.servicos;

import br.fusion.punir.modelos.RegistroDePunicao;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;

public class ExpulsarJogadorDoServidor {



    public static void expulsar(ProxiedPlayer p, RegistroDePunicao registro) {
        TextComponent textComponent = new TextComponent();
        SimpleDateFormat dataFormatador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaFormatador = new SimpleDateFormat("HH:mm:ss");
        String dataFormatada = dataFormatador.format(registro.getDataFim());
        String horaFormatada = horaFormatador.format(registro.getDataFim());
        textComponent.addExtra(ChatColor.RED + "" + ChatColor.BOLD + "FusionMC");
        textComponent.addExtra(ChatColor.RED + "\nVocê foi banido deste servidor até " + dataFormatada + " às " + horaFormatada);
        textComponent.addExtra(ChatColor.RED + "\nMotivo: " + registro.getNomePunicao());
        textComponent.addExtra(ChatColor.RED + "\nID: #" + registro.getIdUnicoPunicao());
        textComponent.addExtra(ChatColor.RED + "\nCompartilhar o ID desta punição poderá afetar seu pedido de revisão.");
        p.disconnect(textComponent);
    }
}
