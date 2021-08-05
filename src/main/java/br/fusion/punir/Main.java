package br.fusion.punir;

import br.fusion.punir.bd.BD;
import br.fusion.punir.controladores.ControladorArquivoPunicoes;
import br.fusion.punir.eventos.MensagemMotivos;
import br.fusion.punir.eventos.NovaPunicao;
import br.fusion.punir.eventos.VerificarBanimentoJogador;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;


public class Main extends Plugin {

    public File configFile;
    public YamlConfiguration arquivoPunicoes;

    @Override
    public void onEnable() {
        getProxy().registerChannel("fp:checarban");
//        getProxy().registerChannel("fp:motivos");
        getProxy().getPluginManager().registerListener(this, new VerificarBanimentoJogador(this));
//        getProxy().getPluginManager().registerListener(this, new MensagemMotivos(this));
//        getProxy().getPluginManager().registerListener(this, new NovaPunicao(this));
        System.out.println("Registrados listeners");
        ControladorArquivoPunicoes.iniciarArquivo(this);
        System.out.println("Arquivos iniciados");
    }

    @Override
    public void onDisable() {

    }
}
