package br.fusion.punir.verificacao.servicos;

import br.fusion.punir.controladores.ControladorArquivoPunicoes;
import br.fusion.punir.modelos.RegistroDePunicao;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NotificarSilenciamento {



    public static void notificar(ProxiedPlayer p, RegistroDePunicao registroDePunicao, Server servidor){
        notificarJogador(p, registroDePunicao);
        notificarServidor(p, servidor);

    }


    private static void notificarJogador(ProxiedPlayer p, RegistroDePunicao registroDePunicao){
        Title aviso = ProxyServer.getInstance().createTitle();
        aviso.title(new TextComponent(ChatColor.RED + "Você está silenciado!"));
        aviso.subTitle(new TextComponent(ChatColor.GRAY + "Veja detalhes no chat!"));
        aviso.send(p);
        TextComponent mensagem = new TextComponent(ChatColor.RED + "Você foi silenciado até " + registroDePunicao.getDataFim());
        mensagem.addExtra("\n" + ChatColor.RED + "Motivo: " + registroDePunicao.getNomePunicao());
        mensagem.addExtra("\n" + ChatColor.RED + "ID: #"+ registroDePunicao.getIdUnicoPunicao());
        p.sendMessage(mensagem);

    }

    private static void notificarServidor(ProxiedPlayer p, Server servidor){
        try{
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);
            out.writeUTF(p.getName());

            servidor.sendData("fp:silenciamento", bytes.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
        }


    }


}
