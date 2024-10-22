package ongAnimaistxt;

public class Cachorro extends Animal {
	private String porte;

    public Cachorro(int id, String nome, String dataNascimento, String sexo, String porte, boolean disponivelParaAdocao) {
        super(id, nome, dataNascimento, "Cachorro", sexo, disponivelParaAdocao);
        this.porte = porte;
    }

    public String getPorte() {
        return porte;
    }

    @Override
    public String toString() {
        return super.toString() + "\nPorte: " + porte + "\n\n-----------------------------------------------";
    }
}
