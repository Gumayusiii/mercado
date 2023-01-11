import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Estoque {
    Connection connection;

    public Estoque() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
    }
    
    //adiciona produto
    public void addProduto(){
        Scanner scanner = new Scanner(System.in);

        String nomeProduto;
        int codigo;
        double valor;
        int qtd;

       

        System.out.println("Informe o nome do produto:");
        nomeProduto = scanner.nextLine();
        System.out.println("Informe o codigo do produto:");
        codigo = scanner.nextInt();
        System.out.println("Informe o valor do produto:");
        valor = scanner.nextDouble();
        System.out.println("Informe a quantidade do produto:");
        qtd = scanner.nextInt();

        try{

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO produto(nome, cod, valor, qtd) VALUES (?,?,?,?)");
            preparedStatement.setString(1, nomeProduto);
            preparedStatement.setInt(2, codigo);
            preparedStatement.setDouble(3, valor);
            preparedStatement.setInt(4, qtd);

            preparedStatement.execute();
            System.out.println("Produto cadastrado");
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Produto não cadastrado");
        }


       
    }

    public void buscarProduto() {

        Scanner scanner = new Scanner(System.in);

       
        Int codigo;

      
        System.out.println("Informe o código do produto:");
        codigo = scanner.next();

        try{

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM paciente where cod = ?");
            preparedStatement.setString(1, codigo);
            ResultSet result = preparedStatement.executeQuery();

            int index = 0;
            while(result.next()){
                index++;
                System.out.print("ID: ");
                System.out.println(result.getInt("id"));
                System.out.print("Código: ");
                System.out.println(result.getInt("cod"));
                System.out.print("Nome: ");
                System.out.println(result.getString("nome"));
                System.out.print("CPF: ");
                System.out.println(result.getString("valor"));
                System.out.print("Doença: ");
                System.out.println(result.getString("qtd"));
                
        }
        if(index == 0){
            System.out.println("Produto não encontrado");
        }
           
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Não foi possível encontrar o produto");
        }

       
    }
}
