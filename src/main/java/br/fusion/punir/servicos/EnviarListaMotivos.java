package br.fusion.punir.servicos;

import br.fusion.punir.Main;
import br.fusion.punir.bd.BD;
import br.fusion.punir.controladores.ControladorArquivoPunicoes;
import net.md_5.bungee.api.connection.Server;

import java.io.*;
import java.util.List;

public class EnviarListaMotivos {


    public void enviar(String nomeStaff, int permissao, Server servidor, Main plugin){
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try{
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(bytes);
                    out.writeUTF(nomeStaff);
                    List<String> motivos;
                    motivos = ControladorArquivoPunicoes.motivosDisponiveisPorPermissao(permissao);
                    for(String motivo : motivos){
                        out.writeUTF(motivo);
                    }

                    System.out.println("Enviando dados via fp:motivos!");
                    servidor.sendData("fp:motivos", bytes.toByteArray());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
