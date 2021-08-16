package br.fusion.punir.modelos;

import java.util.Date;
import java.util.UUID;

public class RegistroDePunicao {

    private Punicao punicao;
    private int idUnicoPunicao;
    private UUID idJogador;
    private String nomeJogador;
    private String aplicador;
    private String supervisorResponsavel;
    private String provas;
    private Date data;
    private Date dataFim;
    private String servidor;


    public RegistroDePunicao(int id, String nomePunicao, UUID idJogador, String nomeJogador, String servidor) {
        this.punicao = new Punicao(id, nomePunicao);
        this.idJogador = idJogador;
        this.servidor = servidor;
        this.nomeJogador = nomeJogador;
    }

    public int getIDPunicao(){
        return punicao.getID();
    }

    public String getNomePunicao(){
        return punicao.getNome();
    }

    public UUID getIdJogador() {
        return idJogador;
    }

    public String getAplicador() {
        return aplicador;
    }

    public void setAplicador(String aplicador) {
        this.aplicador = aplicador;
    }

    public String getSupervisorResponsavel() {
        return supervisorResponsavel;
    }

    public void setSupervisorResponsavel(String supervisorResponsavel) {
        this.supervisorResponsavel = supervisorResponsavel;
    }

    public String getProvas() {
        return provas;
    }

    public void setProvas(String provas) {
        this.provas = provas;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getServidor() {
        return servidor;
    }

    public int getIdUnicoPunicao() {
        return idUnicoPunicao;
    }

    public void setIdUnicoPunicao(int idUnicoPunicao) {
        this.idUnicoPunicao = idUnicoPunicao;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }
}
