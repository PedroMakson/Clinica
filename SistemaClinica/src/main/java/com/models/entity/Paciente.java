package com.models.entity;

import java.util.Date;

public class Paciente extends Usuario {

    private double peso;
    private double altura;
    private String tipoSanguineo;

    public Paciente(String cpf, String nome, Date dataNascimento, String sexo, double peso, double altura,
            String tipoSanguineo,
            String telefone, String email, String senha, String cep, String rua, int numeroDaCasa, String bairro,
            String cidade, String uf) {
        super(cpf, nome, dataNascimento, sexo, telefone, email, senha, cep, rua, numeroDaCasa, bairro, cidade, uf);
        this.peso = peso;
        this.altura = altura;
        this.tipoSanguineo = tipoSanguineo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

}