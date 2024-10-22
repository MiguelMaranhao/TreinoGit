package ongAnimaistxt;

public abstract class Animal {
	private int id;
    private String nome;
    private String dataNascimento;
    private String especie;
    private String sexo;
    private boolean disponivelParaAdocao;
    private String dataCadastro;

    public Animal(int id, String nome, String dataNascimento, String especie, String sexo, boolean disponivelParaAdocao) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.especie = especie;
        this.sexo = sexo;
        this.disponivelParaAdocao = disponivelParaAdocao;
    }
    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDataNascimento() { return dataNascimento; }
    public String getEspecie() { return especie; }
    public String getSexo() { return sexo; }
    public boolean isDisponivelParaAdocao() { return disponivelParaAdocao; }

    public void setDisponivelParaAdocao(boolean disponivelParaAdocao) {
        this.disponivelParaAdocao = disponivelParaAdocao;
    }

    @Override
    public String toString() {
        return "\nID: " + id + "\nNome: " + nome + "\nData de Nascimento: " + dataNascimento + 
               "\nEspécie: " + especie + "\nSexo: " + sexo + 
               "\nDisponível para Adoção: " + (disponivelParaAdocao ? "Sim" : "Não");
    }
}