package com.models.entity;

import java.util.Date;

public class PrescricaoMedica {

    private int numeroPrescricao;
    private Paciente paciente;
    private Funcionario funcionario;
    private Date data;
    private String diagnostico;
    private String descricao;

    public PrescricaoMedica(int numeroPrescricao, Paciente paciente, Funcionario funcionario, Date data,
            String diagnostico, String descricao) {
        this.numeroPrescricao = numeroPrescricao;
        this.paciente = paciente;
        this.funcionario = funcionario;
        this.data = data;
        this.diagnostico = diagnostico;
        this.descricao = descricao;
    }

    public int getNumeroPrescricao() {
        return numeroPrescricao;
    }

    public void setNumeroPrescricao(int numeroPrescricao) {
        this.numeroPrescricao = numeroPrescricao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}