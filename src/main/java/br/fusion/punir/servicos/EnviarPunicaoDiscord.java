package br.fusion.punir.servicos;

import br.fusion.punir.modelos.RegistroDePunicao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class EnviarPunicaoDiscord {


    public void enviarPunicao(RegistroDePunicao registro, int ocorrencias) {
        try {

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String data = formato.format(registro.getData());
            String dataFim = formato.format(registro.getDataFim());
            Socket cliente = new Socket("158.69.23.151", 7776);
            DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
            out.writeUTF(registro.getNomeJogador());
            out.writeUTF(data);
            out.writeUTF(dataFim);
            out.writeUTF(registro.getServidor());
            out.writeUTF(registro.getAplicador());
            out.writeUTF(registro.getProvas());
            out.writeInt(ocorrencias);

            out.close();
            cliente.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
