import Views.ClientView;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        ClientView clientView = new ClientView();

        clientView.menuCliente();
    }
}