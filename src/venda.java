import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Statement;


public class Venda {
    String nomeProduto;

    int totalVendas;
    String data;
    double valor;
    Connection connection;
    int qtd_produto;

    public Venda() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mercado", "root", "");
    }

    public void adicionarVenda() {
        Scanner scanner = new Scanner(System.in);

        List<ProdutoVenda> produtosVendas = new ArrayList<ProdutoVenda>();

        String pagamento = "";
        boolean adicionandoProduto = true;
        while (adicionandoProduto) {
           
            System.out.println("Informe o codigo do produto:");
            int codigo = scanner.nextInt();
            System.out.println("Informe a quantidade do produto:");
            int qtd = scanner.nextInt();

            produtosVendas.add(new ProdutoVenda(codigo, qtd));

            System.out.println("Deseja adicionar main um produto? (s/n)");
            String resposta = scanner.next();
            if (resposta.equalsIgnoreCase("n")) {
                adicionandoProduto = false;
                break;
            }
        }

        System.out.println("Qual a forma de pagamento(1 - Dinheiro / 2 - Cartão):");
        int resposta = scanner.nextInt();
        if (resposta == 1) {
            pagamento = "Dinheiro";
        } else {
            pagamento = "Cartão";
        }

        String cods = "";
        for (int i = 0; i < produtosVendas.size(); i++) {
            cods += produtosVendas.get(i).code;
            if(i < produtosVendas.size()-1)cods += ",";
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT SUM(valor) as total FROM produto WHERE cod in ("+cods+")");
            //System.out.println("INSERT VENDA 1");
            
            ///System.out.println("INSERT VENDA 2");
            ResultSet resultTotal = preparedStatement.executeQuery();
            //System.out.println("INSERT VENDA 3");
            resultTotal.next();
            //System.out.println("INSERT VENDA 4");
            double total = resultTotal.getDouble("total");
            //System.out.println("INSERT VENDA 5"); 
            boolean salvo = false;
            PreparedStatement salvarVenda = null;
            try{

            salvarVenda = connection.prepareStatement(
                    "INSERT INTO venda(total, pagamento) VALUES (?,?)", java.sql.Statement.RETURN_GENERATED_KEYS);
            salvarVenda.setDouble(1, total);
            salvarVenda.setString(2, pagamento);

            salvo = salvarVenda.execute();
            }catch(Exception e){
                System.out.println("INSERT VENDA");
                System.out.println(e.getMessage());
            }

            if (!salvo) {

                ResultSet idResult = salvarVenda.getGeneratedKeys();
            
                if (idResult.next()) {

                    int id = idResult.getInt(1);
                    for (int i = 0; i < produtosVendas.size(); i++) {
                        PreparedStatement relacionamento = connection
                                .prepareStatement("INSERT INTO venda_produto(cod_produto, id_venda) VALUES(?,?)");
                        relacionamento.setInt(1, produtosVendas.get(i).code);
                        relacionamento.setInt(2, id);
                        System.out.println("Id da venda: " + id);
                        boolean result = relacionamento.execute();
                        if (result != false) {
                            System.out.println("Não foi possível criar a venda");
                            return;
                        }
                    }

                }else{
                    System.out.println("Não foi possível criar a venda");
                }
            }else{
                System.out.println("Não foi possível criar a venda");
            }
            
            System.out.println("Venda criada com sucesso");

        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Não foi possível criar a venda");
        }

    }

    public void buscarVenda() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Informe o id da venda:");
        int id = scanner.nextInt();

        try{

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM venda WHERE id = ?");
            statement.setInt(1, id);
            PreparedStatement statementProdutosVenda = connection.prepareStatement("SELECT produto.id, produto.nome, produto.cod, produto.valor, produto.qtd FROM produto INNER JOIN venda_produto ON venda_produto.id_venda = ? WHERE produto.cod = venda_produto.cod_produto");
            statementProdutosVenda.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ResultSet resultSetProdutos = statementProdutosVenda.executeQuery();
            if(resultSet.next()){
                System.out.println("********************************************************");
                //encontrou venda
                System.out.println("ID: "+ resultSet.getInt("id"));
                System.out.println("Valor: "+ resultSet.getDouble("total"));
                System.out.println("Data: "+ resultSet.getDate("data").toLocalDate());
            }else{
                //não encontrou
                System.out.println("Venda não encontrada");
                return;
            }
            int index = 0;
            System.out.println("");
            System.out.println("Produtos nessa venda:");
            System.out.println("");
            while(resultSetProdutos.next()){
                index++;
                //produtos
                System.out.println("ID: "+ resultSetProdutos.getInt("id"));
                System.out.println("Código: "+ resultSetProdutos.getDouble("cod"));
                System.out.println("Nome: "+ resultSetProdutos.getString("nome"));
                System.out.println("Valor: "+ resultSetProdutos.getDouble("valor"));
               System.out.println("---------------------------------------------------");
               
            }
            System.out.println("********************************************************");
            if(index == 0){
                System.out.println("Não foi encontrado nenhum produto para essa venda");
            }
        }catch(Exception e){

        }

       

    }

    public void removerVenda() {
        Scanner scanner = new Scanner(System.in);
      


        System.out.println("Informe o id da venda:");
        int id = scanner.nextInt();

        try{

            PreparedStatement statement = connection.prepareStatement("DELETE FROM venda WHERE id = ?");
            statement.setInt(1, id);
          
            boolean resultSet = statement.execute();
           
            if(!resultSet){
                System.out.println("Venda deletada");
            }else{
                System.out.println("Não foi possível deletar a venda");
            }
        }catch(Exception e){

        }

       
    }
}