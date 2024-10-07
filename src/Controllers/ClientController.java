package Controllers;

import Model.Atracao;
import Model.Venda;
import Repositories.AtracoesRepository;
import Repositories.VendasRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class ClientController {
    private AtracoesRepository atracoesRepository;

    // MÉTODO CONSTRUTOR
    public ClientController() throws FileNotFoundException {
        this.atracoesRepository = new AtracoesRepository();
    }

    // MÉTODO GETTER
    public AtracoesRepository getAtracoesRepository() {
        return atracoesRepository;
    }


    /**
     * Função para imprimir uma tabela com as atrações,
     */
    public void imprimirAtracoes() {
        // Início da Tabela(Cabeçalho):
        System.out.println("\n-> TABELA DE ATRAÇÕES:");
        System.out.println("___________________________________________________________________");
        System.out.println("| ID | Atração | Preço Adulto | Preço Criança | Duração(HH:MM:SS) |");
        System.out.println("___________________________________________________________________");
        for (Atracao atracao : atracoesRepository.getAtracoesArray()) {
            imprimeAtracao(atracao);
        }
        System.out.println("___________________________________________________________________");
        // Fim da Tabela:
    }

    /**
     * Função para imprimir uma atração e converter duração de segundos para horas, minutis e segundos
     * @param atracao
     */
    private void imprimeAtracao(Atracao atracao) {
        int segundos = atracao.getSegundos();
        int horas = segundos / 3600;
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;

        // System.out.println("|" + atracao.getId() + " | " + atracao.getNome() + " | €" + String.format("%.2f", atracao.getPrecoAdulto()) + " | €" + String.format("%.2f", atracao.getPrecoCrianca()) + " | " + String.format("%02d", horas) + ":" + String.format("%02d", minutos) + ":" + String.format("%02d", segundosRestantes) + " |");
        // Marcadores de Formato (%d: inteiro), (%s: string), (%f: ponto flutuante)
        System.out.printf("| %d | %s | €%.2f | €%.2f | %02d:%02d:%02d |\n",
                atracao.getId(),
                atracao.getNome(),
                atracao.getPrecoAdulto(),
                atracao.getPrecoCrianca(),
                horas,
                minutos,
                segundosRestantes);
    }


    /**
     * Função para imprimir a atração mais procurada por adultos e a atração mais procurada por crianças,
     * (Mais procurada é a que vendeu mais bilhetes no total para o respetivo segmento de cliente)
     */
    public void imprimirAtracoesMaisProcuradas() throws FileNotFoundException {
        // Chamar 2 repositórios "Vendas" e "Atracoes"
        ArrayList<Venda> vendasArray = new VendasRepository().getVendasArray();
        ArrayList<Atracao> atracoesArray = new AtracoesRepository().getAtracoesArray();

        // Contar e guardar o numero de vendas por cada atração por tipo de cliente (adulto):
        int[] contadorVendasAdultos = new int[getAtracoesRepository().getAtracoesArray().size()];

        // Contar e guardar o numero de vendas por cada atração por tipo de cliente (crinça):
        int[] contadorVendasCrianca = new int[getAtracoesRepository().getAtracoesArray().size()];

        //Iterar o Array de vendas, para cada venda ele guarda o IdAtração e o tipo de cliente...
        for (int i = 0; i < vendasArray.size(); i++) {
            Venda venda = vendasArray.get(i);
            int idAtracao = venda.getIdAtracao();
            String clienteTipo = venda.getClienteTipo();

            //...tipo de cliente ele incrementa o contador correspondente (contadorVendasAdultos ou contadorVendasCrianca)
            if (clienteTipo.equalsIgnoreCase("adulto")) {
                contadorVendasAdultos[idAtracao-1]++;
            } else if (clienteTipo.equalsIgnoreCase("crianca")) {
                contadorVendasCrianca[idAtracao-1]++;
            }
        }


        // Validar a atração com mais vendas por tipo de cliente
        int maxVendasAdultos = 0; //Vai guardar o total de vendas adultos (atração mais procurada)
        int maxVendasCriancas = 0; //Vai guardar o total de vendas crianças (atração mais procurada)
        Atracao adultoAtracao = null;
        Atracao criancaAtracao = null;

        for (int i = 0; i < contadorVendasAdultos.length; i++) {
            if (contadorVendasAdultos[i] > maxVendasAdultos) {
                maxVendasAdultos = contadorVendasAdultos[i];
                adultoAtracao = atracoesArray.get(i);
            }
        }


        for (int i = 0; i < contadorVendasCrianca.length; i++) {
            if (contadorVendasCrianca[i] > maxVendasCriancas) {
                maxVendasCriancas = contadorVendasCrianca[i];
                criancaAtracao = atracoesArray.get(i);
            }
        }

        // Imprimir resultado na consola (Atração mais procurada + total de vendas):
        System.out.println("\n-> ATRAÇÃO MAIS PROCURADA:");
        System.out.println("___________________________________________________________________");
        System.out.println("ADULTOS: " + adultoAtracao.getNome() + " | TOTAL VENDAS: " + maxVendasAdultos);
        System.out.println("___________________________________________________________________");
        System.out.println("CRIANÇAS: " + criancaAtracao.getNome() + " | TOTAL VENDAS: " + maxVendasCriancas);
        System.out.println("___________________________________________________________________");
    }
}