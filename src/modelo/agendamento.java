package modelo;

import java.time.LocalDateTime;

public class agendamento {

    private int id;
    private paciente paciente;
    private fonoaudiologo fonoaudiologo;
    private LocalDateTime dataHora;

    public agendamento() {
    }

    public agendamento(paciente paciente, fonoaudiologo fonoaudiologo, LocalDateTime dataHora) {
        this.paciente = paciente;
        this.fonoaudiologo = fonoaudiologo;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(paciente paciente) {
        this.paciente = paciente;
    }

    public fonoaudiologo getFonoaudiologo() {
        return fonoaudiologo;
    }

    public void setFonoaudiologo(fonoaudiologo fonoaudiologo) {
        this.fonoaudiologo = fonoaudiologo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}