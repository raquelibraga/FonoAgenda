package modelo;

public class fonoaudiologo {

    private int id;
    private String nome;
    private String especialidade;

    public fonoaudiologo() {
    }

    public fonoaudiologo(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return nome;
    }
}