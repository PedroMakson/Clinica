package com.models.entity;

import java.util.Date;

public class Funcionario extends Usuario {

    private String cargo;
    private double salario;
    private Date dataContratacao;
    private boolean status;

    public Funcionario(String cpf, String nome, Date dataNascimento, String sexo, String cargo, double salario,
            Date dataContratacao, Boolean status, String telefone, String email, String senha, String cep, String rua,
            int numeroDaCasa, String bairro, String cidade, String uf) {
                super(cpf, nome, dataNascimento, sexo, telefone, email, senha, cep, rua, numeroDaCasa, bairro, cidade, uf);
                this.cargo = cargo;
                this.salario = salario;
                this.dataContratacao = dataContratacao;
                this.status = status;    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}