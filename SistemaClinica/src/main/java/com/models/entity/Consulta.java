package com.models.entity;

import java.util.Date;
import java.time.LocalTime;

public class Consulta {

    private int codigoConsulta;
    private Funcionario funcionario;
    private Paciente paciente;
    private Servico servico;
    private Date data;
    private LocalTime horario;
    private String statusConsulta;
    private boolean pagamento;

    public Consulta(int codigoConsulta, Funcionario funcionario, Paciente paciente, Servico servico, Date data,
            LocalTime horario, String statusConsulta, boolean pagamento) {
        this.codigoConsulta = codigoConsulta;
        this.funcionario = funcionario;
        this.paciente = paciente;
        this.servico = servico;
        this.data = data;
        this.horario = horario;
        this.statusConsulta = statusConsulta;
        this.pagamento = pagamento;
    }

    public int getCodigoConsulta() {
        return codigoConsulta;
    }

    public void setCodigoConsulta(int codigoConsulta) {
        this.codigoConsulta = codigoConsulta;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(String statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public boolean isPagamento() {
        return pagamento;
    }

    public void setPagamento(boolean pagamento) {
        this.pagamento = pagamento;
    }

}