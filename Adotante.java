package ongAnimaistxt;

import java.util.ArrayList;

public class Adotante {
	private int id;
    private String nome;
    private String endereco;
    private String contato;
    private ArrayList<Animal> animaisAdotados;

    public Adotante(int id, String nome, String endereco, String contato) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
        this.animaisAdotados = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarAnimalAdotado(Animal animal) {
        animaisAdotados.add(animal);
    }

    @Override
    public String toString() {
        return "\nID: " + id + "\nNome: " + nome + "\nEndereço: " + endereco + "\nContato: " + contato + 
               "\n\n-----------------------------------------------";
    }

	public String getEndereco() {
		
		return endereco;
	}

	public String getContato() {
	    return contato;
	}

	public void setId(int id) {
		this.id = id;
		
	}
}
