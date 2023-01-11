import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Estoque produto = new Estoque();
        Venda venda = new Venda();
        boolean sair = false;
        while (sair == false) {
            System.out.println("_____________________________________");
            System.out.println("                MENU                 ");
            System.out.println("=====================================");
            System.out.println("(0) Sair");
            System.out.println("(1) Buscar produto");
            System.out.println("(2) Adicionar Produto");
            System.out.println("(3) Atualizar produto");
            System.out.println("(4) Remover produto");
            System.out.println("(5) Adicionar venda");
            System.out.println("(6) Buscar venda");
            System.out.println("(7) Remover venda");

            // System.out.println("(8) Relátorio de vendas");

            int opcao;

            try {
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 0:
                        sair = true;
                        break;
                    case 1:
                        produto.buscarProduto();
                        break;
                    case 2:
                        produto.addProduto();
                        break;
                    case 3:
                        produto.atualizarProduto();
                        break;
                    case 4:
                        produto.removerProduto();
                        break;
                    case 5:
                        venda.adicionarVenda();
                        break;
                    case 6:
                        venda.buscarVenda();
                        break;
                    case 7:
                        venda.removerVenda();
                        break;

                }

            } catch (Exception e) {
                System.out.println("Opção inválida");

            }
            

        }

    }
}