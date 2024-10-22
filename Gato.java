package ongAnimaistxt;

public class Gato extends Animal {
	private String corPelo;

    public Gato(int id, String nome, String dataNascimento, String sexo, String corPelo, boolean disponivelParaAdocao) {
        super(id, nome, dataNascimento, "Gato", sexo, disponivelParaAdocao);
        this.corPelo = corPelo;
    }

    public String getCorPelo() {
        return corPelo;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCor do Pelo: " + corPelo + "\n\n-----------------------------------------------";
    }
}