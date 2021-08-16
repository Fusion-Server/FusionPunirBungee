package br.fusion.punir.controladores;

import br.fusion.punir.bd.BD;
import br.fusion.punir.modelos.RegistroDePunicao;
import br.fusion.punir.verificacao.servicos.ExpulsarJogadorDoServidor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;
import java.util.*;

/**
 * Responsável por alterar os registros de punição
 * baseado nas ocorrências
 */
public class ControladorSistemaDePunicao {


    public void executarPunicao(RegistroDePunicao registro) throws IOException {

        int ocorrencias = BD.getOcorrenciasPunicaoJogador(registro.getIDPunicao(), registro.getIdJogador());
        Configuration punicoes = ControladorArquivoPunicoes.getPunicoes();
        Configuration sessaoAcoes = punicoes.getSection(String.valueOf(registro.getIDPunicao())).getSection("Ações");

        Configuration proximaAcao = getProximaAcao(sessaoAcoes, ocorrencias);
        Date dataFim = getDataFim(proximaAcao);
        registro.setDataFim(dataFim);
        if (proximaAcao.get("Tipo").equals("BANIMENTO")) {
            BD.adicionarBanimento(registro, ocorrencias + 1);
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(registro.getIdJogador());
            if (p.isConnected()) {
                ExpulsarJogadorDoServidor.expulsar(p, registro);
            }

        } else if (proximaAcao.get("Tipo").equals("SILENCIAMENTO")) {

        }


//        System.out.println("Contem? " + sessaoAcoes.contains(String.valueOf(ocorrencias + 1)));
//        String tipo = novaAcao.getString("Tipo");
//        System.out.println("Tipo da nova acao: " + tipo);

    }

    private Configuration getProximaAcao(Configuration sessaoAcoes, int ocorrenciasAtuais) {
        List<Integer> acoes = new ArrayList<>();
        for (String acao : sessaoAcoes.getKeys()) {
            acoes.add(Integer.parseInt(acao));
        }
        try {
            return sessaoAcoes.getSection(String.valueOf(acoes.get(ocorrenciasAtuais)));
        } catch (IndexOutOfBoundsException e) {
            return sessaoAcoes.getSection(String.valueOf(Collections.max(acoes)));
        }
//        Configuration novaAcao = sessaoAcoes.getSection(String.valueOf(ocorrenciasAtuais + 1));
//        switch (novaAcao.getString("Tipo")){
//            case "BANIMENTO":
//            case "SILENCIAMENTO":
//                return novaAcao;
//            default:
//                return sessaoAcoes.getSection(String.valueOf(ocorrenciasAtuais));
//        }
    }

    private Date getDataFim(Configuration acao) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());
        int duracao = Integer.parseInt(acao.getString("Duração"));
        if (duracao == 0) {
            calendario.add(Calendar.YEAR, 50);
        } else {
            calendario.add(Calendar.HOUR, duracao);
        }
        return calendario.getTime();
    }
}
