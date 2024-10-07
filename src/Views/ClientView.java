package Views;

import Controllers.ClientController;
import Repositories.AtracoesRepository;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClientView {

    private ClientController clientController;


    public ClientView() throws FileNotFoundException {
        this.clientController = new ClientController();
    }


    public void menuCliente() throws FileNotFoundException {

        // Import Scanner - Chamar função porque é esperado informação do teclado
        Scanner input = new Scanner(System.in);
        // Declarar variavel
        int opcaoCliente;

        do { // Apresenta MENU CLIENTE
            System.out.println("\n|||||||||||||||||||||||||||||||||||||||||");
            System.out.println("\n \uD83D\uDF90 MENU CLIENTE \uD83D\uDF90 \n");
            System.out.println("[1] CONSULTAR ATRAÇÕES DISPONÍVEIS");
            System.out.println("[2] CONSULTAR ATRAÇÕES FAVORITAS");
            System.out.println("[3] SAIR");
            System.out.println(" ");
            System.out.println("> ESCOLHE INSERINDO A OPÇÃO [NUMÉRICA]: ");
            opcaoCliente = input.nextInt(); // Ler e guardar input do user (numero inteiro).

            switch (opcaoCliente) {
                case 1:
                    System.out.println("\n|||||||||||||||||||||||||||||||||||||||||");
                    System.out.println("\n [1. CONSULTAR ATRAÇÕES DISPONÍVEIS]");
                    System.out.println(" ");
                    clientController.imprimirAtracoes();
                    System.out.println(" ");
                    break;
                case 2:
                    System.out.println("\n|||||||||||||||||||||||||||||||||||||||||");
                    System.out.println("\n [2. CONSULTAR ATRAÇÕES FAVORITAS]");
                    System.out.println(" ");
                    clientController.imprimirAtracoesMaisProcuradas();
                    System.out.println(" ");
                    break;
                case 3:
                    System.out.println(" ");
                    System.out.println("\n [3. SAIR]");
                    System.out.println(" ");
                    System.out.println("\n|||||||||||||||||||||||||||||||||||||||||");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }while(opcaoCliente!=3); // Enquanto o input do user é diferente de 3 (MENU INICIAL)// TERMINA A FUNCAO
    }

    public void displayAtracoesTable() {
        AtracoesRepository atracoesRepository = clientController.getAtracoesRepository();
        imprimirTabelaAtracoes(atracoesRepository);
    }

    private void imprimirTabelaAtracoes(AtracoesRepository atracoesRepository) {
    }
}

