import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.xdevapi.Result;
import com.mysql.cj.xdevapi.Statement;

/*
 * nome
 * codigo do produto
 * valor
 */
public class Caixa {
    Connection connection;

    public Caixa() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
    }
    public void cadastrar(){
        Scanner scanner = new Scanner(System.in);

        String especialidade;
        String nome;
        double salario;
        int matricula;

        System.out.println("Informe a especialidade:");
        especialidade = scanner.next();
        System.out.println("Informe o nome:");
        nome = scanner.next();
        System.out.println("Informe o salário:");
        salario = Double.parseDouble(scanner.next());
        System.out.println("Informe a matrícula do médico:");
        matricula = scanner.nextInt();

        try{
           
            PreparedStatement stmt = connection
            .prepareStatement("INSERT INTO medico(nome, matricula, especialidade, salario) VALUES (?, ?, ?, ?)");
           
            stmt.setString(1, nome);
            stmt.setInt(2, matricula);
            stmt.setString(3, especialidade);
            stmt.setDouble(4, salario);

            stmt.execute();
            System.out.println("Médico cadastrado");
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Médico não cadastrado");
        }



    }
    public void buscarPorMatricula() {

        Scanner scanner = new Scanner(System.in);

       
        int matricula;

      
        System.out.println("Informe a matrícula do médico:");
        matricula = scanner.nextInt();

        try{
            String query = "SELECT * FROM medico where matricula = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "matricula");

            ResultSet result = preparedStatement.executeQuery();

            int index = 0;
            while(result.next()){
                index++;
                System.out.print("ID: ");
                System.out.println(result.getInt("id"));
                System.out.print("Nome: ");
                System.out.println(result.getString("nome"));
                System.out.print("Especialidade: ");
                System.out.println(result.getString("especialidade"));
                System.out.print("Matrícula: ");
                System.out.println(result.getInt("matricula"));
                System.out.print("Salário: ");
                System.out.println(result.getDouble("salario"));
        }
        if(index == 0){
            System.out.println("Matrícula não encontrada");
        }
           
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Não foi possível encontrar o médico");
        }

       
    }
}
