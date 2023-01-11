import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Estoque {
    Connection connection;
    Scanner scanner = new Scanner(System.in);

    public Estoque() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mercado", "root", "");
    }

    // ======================= ADICIONAR PRODUTO

    public void addProduto() {

        String nomeProduto;
        int codigo;
        double valor;
        int qtd;

        System.out.println("Informe o nome do produto:");
        nomeProduto = scanner.nextLine();
        System.out.println("Informe o codigo do produto:");
        codigo = scanner.nextInt();
        System.out.println("Informe o valor do produto:");
        valor = Double.parseDouble(scanner.next());
        System.out.println("Informe a quantidade do produto:");
        qtd = scanner.nextInt();

        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO produto(nome, cod, valor, qtd) VALUES (?,?,?,?)");
            preparedStatement.setString(1, nomeProduto);
            preparedStatement.setInt(2, codigo);
            preparedStatement.setDouble(3, valor);
            preparedStatement.setInt(4, qtd);
 
            preparedStatement.execute();
            System.out.println("Produto cadastrado");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Produto não cadastrado");
        }

    }

    // ================================ BUSCAR PRODUTO

    public void buscarProduto() {

        Scanner scanner = new Scanner(System.in);

        int codigo;

        System.out.println("Informe o código do produto:");
        codigo = scanner.nextInt();

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM produto where cod = ?");
            preparedStatement.setInt(1, codigo);
            ResultSet result = preparedStatement.executeQuery();

            int index = 0;
            while (result.next()) {
                index++;
                System.out.print("ID: ");
                System.out.println(result.getInt("id"));
                System.out.print("Código: ");
                System.out.println(result.getInt("cod"));
                System.out.print("Nome: ");
                System.out.println(result.getString("nome"));
                System.out.print("Valor: ");
                System.out.println(result.getString("valor"));
                System.out.print("Quantidade: ");
                System.out.println(result.getString("qtd"));

            }
            if (index == 0) {
                System.out.println("Produto não encontrado");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Não foi possível encontrar o produto");
        }
    }

    // ================================ ATUALIZAR PRODUTO

    public void atualizarProduto() {

        int codigo;

        System.out.println("Informe o código do produto:");
        codigo = scanner.nextInt();
        System.out.println("Informe o novo valor do produto");
        double novoValor = Double.parseDouble(scanner.next());
        System.out.println("Informe a quantidade do produto: ");
        int nvQtd = scanner.nextInt();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE produto SET  valor = ?, qtd = ? where cod = ? ");
            preparedStatement.setDouble(1, novoValor);
            preparedStatement.setInt(2, nvQtd);
            preparedStatement.setInt(3, codigo);
            boolean result = preparedStatement.execute();
            if (!result) {
                System.out.println("Produto atualizado!");
            } else {
                System.out.println("Falha em atualizar produto!");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Erro! Não foi possível atualizar o produto!");

        }
    }

    // ================================ REMOVER PRODUTO
    public void removerProduto() {

        Scanner scanner = new Scanner(System.in);

        int codigo;

        System.out.println("Informe o código do produto:");
        codigo = scanner.nextInt();

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM produto where cod = ?");
            preparedStatement.setInt(1, codigo);
            ResultSet result = preparedStatement.executeQuery();

            int index = 0;

            if (index == 0) {
                System.out.println("Produto não encontrado");
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Não foi possível encontrar o produto");
        }
    }

}
