package ongAnimaistxt;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class ONG {
	int idAnimal = 1;
	int idAdotante = 1;
	private BancoDeDados bancoDeDados;
	
//	Grupo: 
//	Miguel Maranhão de Vasconcelos
//	Luiz Carlos Montenegro de Sousa
//	Kathelen Vitória Brito Sales
//	Daniel Tavares de Almeida
	

    public ONG() {
        bancoDeDados = new BancoDeDados();
        atualizarIds();
    }

    private void atualizarIds() {
        ArrayList<Animal> animaisCadastrados = bancoDeDados.listarTodosAnimais();
        for (Animal animal : animaisCadastrados) {
            if (animal.getId() >= idAnimal) {
                idAnimal = animal.getId() + 1;
            }
        }

        ArrayList<Adotante> adotantesCadastrados = bancoDeDados.listarAdotantes();
        for (Adotante adotante : adotantesCadastrados) {
            if (adotante.getId() >= idAdotante) {
                idAdotante = adotante.getId() + 1;
            }
        }
    }
    
    private int calcularIdade(String dataNascimento) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNasc = LocalDate.parse(dataNascimento, formatter);
            LocalDate hoje = LocalDate.now();
            Period periodo = Period.between(dataNasc, hoje);
            return periodo.getYears();
        } catch (DateTimeParseException e) {
            
            return -1; 
        }
    }

    public void cadastrarAnimal() {
   
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDigite o nome do animal: ");
        String nome = scanner.nextLine();

        System.out.println("\nDigite a data de nascimento (dd/mm/aaaa): ");
        String dataNascimento = scanner.nextLine();

        System.out.println("\nDigite a espécie (Cachorro/Gato): ");
        String especie = scanner.nextLine().toLowerCase();

        System.out.println("\nDigite o sexo (Masculino/Feminino): ");
        String sexo = scanner.nextLine().toLowerCase();
        if (nome.equals("")) {
			System.out.println("\nImpossível cadastrar! Nome do animal não informado.");
			return;
		} else if (dataNascimento.equals("")) {
			System.out.println(
					"\nImpossível cadastrar! Data de Nascimento (aproximada) do animal não informada.");
			return;
		} else if (!(especie.equals("cachorro") || especie.equals("gato"))) {
			System.out.println(
					"\nImpossível cadastrar! Espécie do animal não informada ou não coincide com as espécies que trabalhamos (Cachorro ou Gato).");
			return;
		}
		else if (!(sexo.equals("masculino") || sexo.equals("feminino"))) {
			System.out.println("\nImpossível cadastrar! Sexo do animal não informado ou não coincide com os sexos que trabalhamos (Masculino ou Feminino).");
			return;
			
		}
        boolean disponivelParaAdocao = true;

        if (especie.equals("cachorro")) {
            System.out.println("\nDigite o porte (Pequeno/Médio/Grande): ");
            String porte = scanner.nextLine().toLowerCase();
            if (!(porte.equals("pequeno") || porte.equals("medio") || porte.equals("grande") || porte.equals("médio"))) {
            	System.out.println("\nImpossível cadastrar! Porte do animal não informado ou não coincide com o porte de animais que trabalhamos (Pequeno, Médio ou Grande).");
            	return;		
            }
            Cachorro cachorro = new Cachorro(idAnimal, nome, dataNascimento, sexo, porte, disponivelParaAdocao);
            System.out.println("\nAnimal Adicionado com Sucesso!");
            System.out.println("\nLembrem - se:\n Você também pode fazer a diferença! Seja um defensor dos animais, denuncie casos de abandono e maus-tratos e, se possível, adote um animal de estimação de forma responsável.");
					
            bancoDeDados.adicionarAnimal(cachorro);
            idAnimal++;
           
            
        } else if (especie.equals("gato")) {
            System.out.println("\nDigite a Cor / Padrão dos pelos: ");
            String corPelo = scanner.nextLine();
            Gato gato = new Gato(idAnimal, nome, dataNascimento, sexo, corPelo, disponivelParaAdocao);
            System.out.println("\nAnimal Adicionado com Sucesso!");
            System.out.println("\nLembrem - se:\n Você também pode fazer a diferença! Seja um defensor dos animais, denuncie casos de abandono e maus-tratos e, se possível, adote um animal de estimação de forma responsável.");
            bancoDeDados.adicionarAnimal(gato);
            idAnimal++;
            
        }
    }

    public void cadastrarAdotante() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDigite o nome completo do adotante: ");
        String nome = scanner.nextLine();

        System.out.println("\nDigite o endereço do adotante: ");
        String endereco = scanner.nextLine();

        System.out.println("\nDigite o contato do adotante: ");
        String contato = scanner.nextLine();
        if (nome.equals("")) {
			System.out.println("\nImpossível cadastrar! Nome do Adotante não informado.");
			return;
        }
        else if (endereco.equals("")) {
        	System.out.println("\nImpossível cadastrar! Endereço do Adotante não informado.");
        	return;
        }
        else if (contato.equals("")) {
        	System.out.println("\nImpossível cadastrar! Contato do Adotante não informado.");
        	return;
        }
        
        Adotante adotante = new Adotante(idAdotante, nome, endereco, contato);
        System.out.println("\nAdotante Cadastrado com Sucesso!");
        bancoDeDados.adicionarAdotante(adotante);
        idAdotante++;
    }

    public void listarAnimaisDisponiveis() {
        ArrayList<Animal> animais = bancoDeDados.listarAnimaisDisponiveis();
        if (animais.isEmpty()) {
            System.out.println("\nNenhum Animal disponível para adoção!");
            System.out.println("\nCaso tenha encontrado algum animal perdido, cadastre-o em nossa ONG! Será um enorme prazer garantir um lar para esses bichinhos tão queridos.");
            return;
        } else {
            for (Animal animal : animais) {
                System.out.println(animal);
            }
        }
    }

    public void listarAdotantes() {
        ArrayList<Adotante> adotantes = bancoDeDados.listarAdotantes();
        if (adotantes.isEmpty()) {
            System.out.println("\nNenhum adotante cadastrado.");
            return;
        } else {
            for (Adotante adotante : adotantes) {
                System.out.println(adotante);
            }
        }
    }

    public void adotarAnimal() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Animal> preAdocao = bancoDeDados.listarAnimaisDisponiveis();
        if (preAdocao.isEmpty()) {
            System.out.println("\nNenhum animal disponível para adoção!");
            return;
        }

        
        ArrayList<Adotante> adotantesDisponiveis = bancoDeDados.listarAdotantes();
        if (adotantesDisponiveis.isEmpty()) {
            System.out.println("\nNenhum adotante disponível. Cadastre um adotante primeiro.");
            return;
        }

        System.out.println("\nAdotantes disponíveis:");
        for (Adotante adotante : adotantesDisponiveis) {
            System.out.println(adotante);  
        }

        System.out.println("\nDigite o ID do adotante: ");
        int idAdotante = scanner.nextInt();
        scanner.nextLine();  
        
        
        Adotante adotanteEscolhido = null;
        for (Adotante adotante : adotantesDisponiveis) {
            if (adotante.getId() == idAdotante) {
                adotanteEscolhido = adotante;
                break;
            }
        }

        if (adotanteEscolhido == null) {
            System.out.println("\nAdotante Inválido! ID não encontrado.");
            return;
        }

        
        System.out.println("\nDigite a espécie do animal (Cachorro/Gato) ou deixe em branco para não filtrar: ");
        String especieFiltro = scanner.nextLine().toLowerCase();

        System.out.println("\nDigite o sexo do animal (Masculino/Feminino) ou deixe em branco para não filtrar: ");
        String sexoFiltro = scanner.nextLine().toLowerCase();

        System.out.println("\nDigite a idade máxima do animal (em anos) ou deixe em branco para não filtrar: ");
        String idadeMaximaStr = scanner.nextLine();
        Integer idadeMaxima = null;
        if (!idadeMaximaStr.trim().isEmpty()) {
            try {
                idadeMaxima = Integer.parseInt(idadeMaximaStr);
            } catch (NumberFormatException e) {
                System.out.println("Idade máxima inválida. Filtro de idade será ignorado.");
            }
        }

        
        ArrayList<Animal> animaisFiltrados = new ArrayList<>();
        for (Animal animal : preAdocao) {
            boolean matches = true;

            if (!especieFiltro.isEmpty() && !animal.getEspecie().toLowerCase().equals(especieFiltro)) {
                matches = false;
            }
            if (!sexoFiltro.isEmpty() && !animal.getSexo().toLowerCase().equals(sexoFiltro)) {
                matches = false;
            }
            if (idadeMaxima != null) {
                int idade = calcularIdade(animal.getDataNascimento());
                if (idade == -1 || idade > idadeMaxima) {
                    matches = false;
                }
            }

            if (matches) {
                animaisFiltrados.add(animal);
            }
        }

        if (animaisFiltrados.isEmpty()) {
            System.out.println("\nNenhum animal corresponde aos filtros fornecidos.");
            return;
        }

        System.out.println("\nAnimais disponíveis que correspondem aos filtros:");
        for (Animal animal : animaisFiltrados) {
            System.out.println(animal);
        }

        System.out.println("\nDigite o ID do animal que deseja adotar ou Digite 0 para desistir de adotar e retornar ao menu de opções: ");
        int idAnimalEscolhido = scanner.nextInt();
        if (idAnimalEscolhido == 0) {
            System.out.println("\nRetornando ao menu de opções!");
            return;
        }

        if (bancoDeDados.verificarDisponibilidade(idAnimalEscolhido)) {
            bancoDeDados.marcarAnimalComoAdotado(idAnimalEscolhido, adotanteEscolhido);
            System.out.println("\nAnimal adotado com sucesso por " + adotanteEscolhido.getNome() + "!");
        } else {
            System.out.println("\nO animal selecionado não está disponível para adoção.");
        }
    }

    public static void main(String[] args) {
        ONG ong = new ONG();
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
        	System.out.println("-----------------------------------------------");
        	System.out.println("\nOlá, seja Bem Vindo(a) ao sistema da nossa ONG:\n");
        	System.out.println("Segue as nossas opções:\n");
            System.out.println("1 = Cadastrar novo Animal");
            System.out.println("2 = Cadastrar Adotante");
            System.out.println("3 = Listar Animais Disponíveis");
            System.out.println("4 = Listar Adotantes");
            System.out.println("5 = Adotar Animal");
            System.out.println("6 = Emitir Relatório de Adoções");
            System.out.println("7 = Emitir Relatório de Cadastros");
            System.out.println("8 = Sair\n");
            System.out.print("\nDigite dentre as nossas opções, a sua escolha: \n ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    ong.cadastrarAnimal();
                    break;
                case 2:
                    ong.cadastrarAdotante();
                    break;
                case 3:
                    ong.listarAnimaisDisponiveis();
                    break;
                case 4:
                    ong.listarAdotantes();
                    break;
                case 5:
                    ong.adotarAnimal();
                    break;
                case 6:
                	BancoDeDados.emitirRelatorioAdocoes();
                	break;
                case 7:
                	BancoDeDados.emitirRelatorioCadastros();
                	break;
                case 8:
                    sair = true;
                    System.out.println("\nOk! Saindo do Sistema...");
                    break;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
            }
        }
    }
}
