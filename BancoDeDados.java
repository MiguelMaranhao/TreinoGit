package ongAnimaistxt;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BancoDeDados {
    private static final String ANIMAIS_FILE = "data/animais.txt";
    private static final String ADOTANTES_FILE = "data/adotantes.txt";
    private static final String RELATORIO_FILE = "data/relatorio_adocoes.txt";
    private static final String RELATORIOC_FILE = "data/relatorio_cadastros.txt";
    private int ultimoIdAdotante = 0; 

    public BancoDeDados() {
        criarDiretorios();
        carregarUltimoIdAdotante(); 
    }

    private void criarDiretorios() {
        File dir = new File("data");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("Diretório 'data' criado com sucesso.");
            } else {
                System.out.println("Erro ao criar o diretório 'data'.");
            }
        } else {
            System.out.println("Diretório 'data' já existe.");
        }
    }

    
    private void carregarUltimoIdAdotante() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ADOTANTES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                int id = Integer.parseInt(partes[0]);
                if (id > ultimoIdAdotante) {
                    ultimoIdAdotante = id; 
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de adotantes não encontrado. O ID começará em 1.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar o último ID dos adotantes: " + e.getMessage());
        }
    }
    public void salvarRelatorio(int idAnimal, String nomeAnimal, String especieAnimal, int idAdotante, String nomeAdotante) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RELATORIO_FILE, true))) {
            String dataAdocao = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            String linha = String.format("%s - %s %s (ID: %d) foi adotado por %s (ID: %d).",
                    dataAdocao, especieAnimal, nomeAnimal, idAnimal, nomeAdotante, idAdotante);

            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("\nErro ao salvar o relatório: " + e.getMessage());
        }
    }
    public static void emitirRelatorioAdocoes() {
        File relatorioFile = new File(RELATORIO_FILE);
        if (!relatorioFile.exists()) {
            System.out.println("\nNenhum relatório foi encontrado.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(RELATORIO_FILE))) {
            String linha;
            System.out.println("\nRelatório de Adoções:\n");
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("\nErro ao emitir o relatório: " + e.getMessage());
        }
    }
    public static void emitirRelatorioCadastros() {
        File relatorioFile = new File(RELATORIOC_FILE);
        if (!relatorioFile.exists()) {
            System.out.println("\nNenhum relatório de cadastro foi encontrado.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(RELATORIOC_FILE))) {
            String linha;
            System.out.println("\nRelatório de Cadastros:\n");
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            System.out.println("\nErro ao emitir o relatório de cadastros: " + e.getMessage());
        }
    }
    public void salvarRelatorioCadastro(Animal animal) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RELATORIOC_FILE, true))) {
            String dataCadastro = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            String linha = String.format("%s - %s %s (ID: %d) foi cadastrado(a).",
                    dataCadastro, animal.getEspecie(), animal.getNome(), animal.getId());
                    

            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("\nErro ao salvar o relatório de cadastro: " + e.getMessage());
        }
    }

    
    private int gerarProximoIdAdotante() {
        return ++ultimoIdAdotante;
    }

    public void adicionarAnimal(Animal animal) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ANIMAIS_FILE, true))) {
            String linha = animal.getId() + "," + animal.getNome() + "," + animal.getDataNascimento() + "," +
                           animal.getEspecie() + "," + animal.getSexo() + "," +
                           (animal.isDisponivelParaAdocao() ? 1 : 0);

            if (animal instanceof Cachorro) {
                linha += "," + ((Cachorro) animal).getPorte() + ",null";
            } else if (animal instanceof Gato) {
                linha += ",null," + ((Gato) animal).getCorPelo();
            }

            writer.write(linha);
            writer.newLine();

            
            salvarRelatorioCadastro(animal);

        } catch (IOException e) {
            System.out.println("\nErro ao adicionar animal: " + e.getMessage());
        }
    }
    
    public void adicionarAdotante(Adotante adotante) {
        adotante.setId(gerarProximoIdAdotante()); 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADOTANTES_FILE, true))) {
            String linha = adotante.getId() + "," + adotante.getNome() + "," +
                           adotante.getEndereco() + "," + adotante.getContato();
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("\nErro ao adicionar adotante: " + e.getMessage());
        }
    }

    public ArrayList<Animal> listarAnimaisDisponiveis() {
        ArrayList<Animal> animais = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ANIMAIS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");

                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String dataNascimento = partes[2];
                String especie = partes[3];
                String sexo = partes[4];
                boolean disponivel = Integer.parseInt(partes[5]) == 1;

                if (disponivel) {
                    if (especie.equalsIgnoreCase("Cachorro")) {
                        String porte = partes[6];
                        animais.add(new Cachorro(id, nome, dataNascimento, sexo, porte, disponivel));
                    } else if (especie.equalsIgnoreCase("Gato")) {
                        String corPelo = partes[7];
                        animais.add(new Gato(id, nome, dataNascimento, sexo, corPelo, disponivel));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\nErro ao listar animais disponíveis: " + e.getMessage() + (" É necessário cadastrar um animal!"));
        }

        return animais;
    }

    public ArrayList<Adotante> listarAdotantes() {
        ArrayList<Adotante> adotantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ADOTANTES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String endereco = partes[2];
                String contato = partes[3];
                adotantes.add(new Adotante(id, nome, endereco, contato));
            }
        } catch (IOException e) {
            System.out.println("\nErro ao listar adotantes: " + e.getMessage() + (" É necessário cadastrar um adotante!"));
        }
        return adotantes;
    }
    public ArrayList<Animal> listarTodosAnimais() {
    	ArrayList<Animal> animais = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ANIMAIS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");

                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String dataNascimento = partes[2];
                String especie = partes[3];
                String sexo = partes[4];
                if (especie.equalsIgnoreCase("Cachorro")) {
                    String porte = partes[6];
                    animais.add(new Cachorro(id, nome, dataNascimento, sexo, porte, false));
                } else if (especie.equalsIgnoreCase("Gato")) {
                    String corPelo = partes[7];
                    animais.add(new Gato(id, nome, dataNascimento, sexo, corPelo, false));
                }
            }
        }
    catch (IOException e) {
        System.out.println("\nErro ao listar todos animais: " + e.getMessage() + (" É necessário cadastrar um animal!"));
    }

    return animais;
}

    public void marcarAnimalComoAdotado(int idAnimal, Adotante adotante) {
        File inputFile = new File(ANIMAIS_FILE);
        File tempFile = new File("temp.txt");

        boolean animalAdotado = false;
        String nomeAnimal = "";
        String especieAnimal = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                int id = Integer.parseInt(partes[0]);

                if (id == idAnimal) {
                    
                    partes[5] = "0"; 

                    
                    nomeAnimal = partes[1];
                    especieAnimal = partes[3];
                    animalAdotado = true;
                }

                
                writer.write(String.join(",", partes));
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("\nErro ao renomear o arquivo temporário.");
            }
        } else {
            System.out.println("\nErro ao deletar o arquivo original.");
        }

        
        if (animalAdotado) {
            salvarRelatorio(idAnimal, nomeAnimal, especieAnimal, adotante.getId(), adotante.getNome());
            System.out.println(nomeAnimal + " foi adotado(a) por " + adotante.getNome() + ".");
        } else {
            System.out.println("Animal com ID " + idAnimal + " não encontrado.");
        }
    }

    public boolean verificarDisponibilidade(int idAnimal) {
        ArrayList<Animal> animais = listarAnimaisDisponiveis();
        for (Animal animal : animais) {
            if (animal.getId() == idAnimal) {
                return animal.isDisponivelParaAdocao();
            }
        }
        return false; 
    }
}
   
