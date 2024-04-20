package com.models.entity;

public class Servico {

    private int codigo;
    private String nome;
    private double valor;
    private boolean status;
    
    public Servico(int codigo, String nome, double valor, boolean status) {
        this.codigo = codigo;
        this.nome = nome;
        this.valor = valor;
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}