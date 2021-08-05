package br.fusion.punir.modelos;

public class Punicao {

    private int id;
    private String nome;
    private int permissao;

    public Punicao(int id, String nome, int permissao){
        this.id = id;
        this.nome = nome;
        this.permissao = permissao;
    }

    public Punicao(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public int getID() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPermissao() {
        return permissao;
    }

    public void setPermissao(int permissao) {
        this.permissao = permissao;
    }
}
