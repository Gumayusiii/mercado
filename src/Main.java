import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
            boolean sair;
            while (sair == false) {
            System.out.println("_____________________________________");
            System.out.println("                MENU                 ");
            System.out.println("=====================================");
            System.out.println("(1) Buscar produto");
            System.out.println("(2) Visualizar dados dos funcionarios");
            System.out.println("(3) Cadastrar funcionário");
            System.out.println("(4) Cadastrar Produto");
            System.out.println("(5) Processar produtos (pagamento em espécie)");
            

            int opcao;
            try {
                opcao = scanner.nextInt();
                System.out.println("");

                switch (opcao) {
                    case 1:
                        produto.buscar();
                        break;
                    case 2:
                        funcionario.verDados();
                        break;
                    case 3:
                        funcionario.cadastrar();
                        break;
                    case 4:
                        produto.cadastrar();
                        break;
                    case 5:
                        paciente.buscarPorCPF();
                        break;
                    case 6:
                        sair = true;
                        break;
                    
                }

            } catch (Exception e) {
                System.out.println("Opção inválida");

            }

        } */

    }
}