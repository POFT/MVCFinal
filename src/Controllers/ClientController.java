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
     * Função para imprimir uma atração e converter duração de segundos para horas, minutos e segundos
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
     * Função para imprimir a atração mais procurada por adultos,
     * (Mais procurada é a que vendeu mais bilhetes no total para o respetivo segmento de cliente)
     */
    public Atracao atracaoMaisProcuradaAdultos() throws FileNotFoundException {
        // Chamar 2 repositórios "Vendas" e "Atracoes"
        ArrayList<Venda> vendasArray = new VendasRepository().getVendasArray();
        ArrayList<Atracao> atracoesArray = new AtracoesRepository().getAtracoesArray();

        // Contar e guardar o numero de vendas por cada atração por tipo de cliente (adulto):
        int[] contadorVendasAdultos = new int[getAtracoesRepository().getAtracoesArray().size()];


        //Iterar o Array de vendas, para cada venda ele guarda o IdAtração e o tipo de cliente...
        for (int i = 0; i < vendasArray.size(); i++) {
            Venda venda = vendasArray.get(i);
            int idAtracao = venda.getIdAtracao();
            String clienteTipo = venda.getClienteTipo();

            //...tipo de cliente ele incrementa o contador correspondente (contadorVendasAdultos ou contadorVendasCrianca)
            if (clienteTipo.equalsIgnoreCase("adulto")) {
                contadorVendasAdultos[idAtracao-1]++;
            }
        }

        // Validar a atração com mais vendas por tipo de cliente
        int maxVendasAdultos = 0; //Vai guardar o total de vendas adultos (atração mais procurada)
        Atracao adultoAtracao = null;

        for (int i = 0; i < contadorVendasAdultos.length; i++) {
            if (contadorVendasAdultos[i] > maxVendasAdultos) {
                maxVendasAdultos = contadorVendasAdultos[i];
                adultoAtracao = atracoesArray.get(i);
            }
        }
        return adultoAtracao;
    }



    /**
     * Função para imprimir a atração mais procurada por crianças,
     * (Mais procurada é a que vendeu mais bilhetes no total para o respetivo segmento de cliente)
     */
    public Atracao atracaoMaisProcuradaCriancas() throws FileNotFoundException {

        ArrayList<Venda> vendasArray = new VendasRepository().getVendasArray();
        ArrayList<Atracao> atracoesArray = new AtracoesRepository().getAtracoesArray();
        int[] contadorVendasCriancas = new int[getAtracoesRepository().getAtracoesArray().size()];

        for (int i = 0; i < vendasArray.size(); i++) {
            Venda venda = vendasArray.get(i);
            int idAtracao = venda.getIdAtracao();
            String clienteTipo = venda.getClienteTipo();

            if (clienteTipo.equalsIgnoreCase("adulto")) {
                contadorVendasCriancas[idAtracao-1]++;
            }
        }

        int maxVendasCriancas = 0;
        Atracao criancaAtracao = null;

        for (int i = 0; i < contadorVendasCriancas.length; i++) {
            if (contadorVendasCriancas[i] > maxVendasCriancas) {
                maxVendasCriancas = contadorVendasCriancas[i];
                criancaAtracao = atracoesArray.get(i);
            }
        }
        return criancaAtracao;
    }

//
//    public Atracao maisProcurada() {
//        int maisVendas = 0; //Vai guardar o total de vendas crianças (atração mais procurada)
//        Atracao maisProcurada = null;
//
//        for (int i = 0; i < 10; i++) {
//            if (contadorVendas[i] > maisVendas) {
//                maisVendas = contadorVendas[i];
//                maisProcurada = atracoesArray.get(i);
//
//            }
//
//        }
//    }


    public Atracao atracaoMaisProcurada() throws FileNotFoundException {
        ArrayList<Venda> vendasArray = new VendasRepository().getVendasArray();
        ArrayList<Atracao> atracoesArray = new AtracoesRepository().getAtracoesArray();

        int[] contadorVendas = new int[getAtracoesRepository().getAtracoesArray().size()];

        // Iterar o Array de vendas, para cada venda ele guarda o IdAtração
        for (int i = 0; i < vendasArray.size(); i++) {
            Venda venda = vendasArray.get(i);
            int idAtracao = venda.getIdAtracao();
            contadorVendas[idAtracao-1]++;
        }

        // Validar a atração com mais vendas
        int maxVendas = 0;
        Atracao atracaoMaisProcurada = null;

        for (int i = 0; i < contadorVendas.length; i++) {
            if (contadorVendas[i] > maxVendas) {
                maxVendas = contadorVendas[i];
                atracaoMaisProcurada = atracoesArray.get(i);
            }
        }
        return atracaoMaisProcurada;
    }
}
