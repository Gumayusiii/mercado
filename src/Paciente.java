import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Paciente {
    Connection connection;

    public Paciente() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
    }
    public void cadastrar(){
        Scanner scanner = new Scanner(System.in);

        String cpf;
        String nome;
        String doenca;
       

        System.out.println("Informe o CPF:");
        cpf = scanner.next();
        System.out.println("Informe o nome:");
        nome = scanner.next();
        System.out.println("Informe a doença:");
        doenca = scanner.next();
      

        try{

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO paciente(nome, cpf, doenca) VALUES (?,?,?)");
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cpf);
            preparedStatement.setString(3, doenca);

            preparedStatement.execute();
            System.out.println("Paciente cadastrado");
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Paciente não cadastrado");
        }


       
    }

    public void buscarPorCPF() {

        Scanner scanner = new Scanner(System.in);

       
        String cpf;

      
        System.out.println("Informe o CPF:");
        cpf = scanner.next();

        try{

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM paciente where cpf = ?");
            preparedStatement.setString(1, cpf);
            ResultSet result = preparedStatement.executeQuery();

            int index = 0;
            while(result.next()){
                index++;
                System.out.print("ID: ");
                System.out.println(result.getInt("id"));
                System.out.print("Nome: ");
                System.out.println(result.getString("nome"));
                System.out.print("CPF: ");
                System.out.println(result.getString("cpf"));
                System.out.print("Doença: ");
                System.out.println(result.getString("doenca"));
                
        }
        if(index == 0){
            System.out.println("CPF não encontrado");
        }
           
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Não foi possível encontrar o paciente");
        }

       
    }
}
