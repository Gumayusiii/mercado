import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

import com.mysql.cj.conf.HostInfo;

public class Venda {
    Connection connection;

    public ConsultaVenda() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mercado", "root", "");
    }
    public void adicionarVenda(){
        Scanner scanner = new Scanner(System.in);
       
        
        int totalVendas;
        String data;
        double valor;
       


    /*   System.out.println("Informe o horário (00/00/0000-hh:mm):");
        data = scanner.next();
        System.out.println("Informe a matrícula:");
        matricula = scanner.nextInt();
        System.out.println("Informe o valor da consulta:");
        valor = Double.parseDouble(scanner.next());
       */


        try{

            System.out.println(horario);
            horario = horario.replace("-"," ");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            System.out.println(format.parse(horario));
            Date date = format.parse(horario);




            PreparedStatement buscarMedico = connection.prepareStatement("SELECT * FROM medico where matricula = ?");

            buscarMedico.setInt(1, matricula);
            ResultSet resultMedico = buscarMedico.executeQuery();

            PreparedStatement buscarPaciente = connection.prepareStatement("SELECT * FROM paciente where cpf = ?");
            buscarPaciente.setString(1, cpf);
            ResultSet resultPaciente = buscarPaciente.executeQuery();

            if(resultMedico.next() == false){
                System.out.println("Médico não encontrado");
            }else
            if(resultPaciente.next() == false){
                System.out.println("Paciente não encontrado");
            }else{

                String idPaciente = resultPaciente.getString("id");
                String idMedico = resultMedico.getString("id");

                PreparedStatement cadastroConsulta = connection.prepareStatement("INSERT INTO consulta(id_medico, id_paciente, horario, valor) VALUES (?,?,?,?)");

                cadastroConsulta.setInt(1, Integer.parseInt(idMedico));
                cadastroConsulta.setInt(2, Integer.parseInt(idPaciente));
                cadastroConsulta.setDate(3, new java.sql.Date(date.getTime()));
                cadastroConsulta.setDouble(4, valor);
            cadastroConsulta.execute();
            System.out.println("Consulta cadastrada");
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Consulta não cadastrada");
        }


    }

    public void buscarVenda(){
        Scanner scanner = new Scanner(System.in);
       

        String cpf;
        int matricula;
        String horario;
        String novoHorario;
        double valor;
       

        System.out.println("Informe o CPF:");
        cpf = scanner.next();
       
        System.out.println("Informe a matrícula:");
        matricula = scanner.nextInt();
        System.out.println("Informe o horário atual da consulta (00/00/0000-hh:mm):");
        horario = scanner.next();
        System.out.println("Informe o novo horário da consulta (00/00/0000-hh:mm):");
        novoHorario = scanner.next();


        try{

            System.out.println(horario);
            horario = horario.replace("-"," ");
            novoHorario = novoHorario.replace("-"," ");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            SimpleDateFormat formatBD = new SimpleDateFormat("yyyy-MM-dd HH:mm");
           


            PreparedStatement buscarMedico = connection.prepareStatement("SELECT * FROM medico where matricula = ?");
            buscarMedico.setInt(1, matricula);

            ResultSet resultMedico = buscarMedico.executeQuery();

            PreparedStatement buscarPaciente = connection.prepareStatement("SELECT * FROM paciente where cpf = ?");
            buscarPaciente.setString(2, cpf);

            ResultSet resultPaciente = buscarPaciente.executeQuery();

            if(resultMedico.next() == false){
                System.out.println("Médico não encontrado");
            }else
            if(resultPaciente.next() == false){
                System.out.println("Paciente não encontrado");
            }else{

                String idPaciente = resultPaciente.getString("id");
                String idMedico = resultMedico.getString("id");

                PreparedStatement cadastroConsulta = connection.prepareStatement("UPDATE consulta SET horario = ? WHERE id_paciente = ? AND id_medico = ? AND horario = ?");
                cadastroConsulta.setDate(1, new java.sql.Date(format.parse(horario).getTime()));
                cadastroConsulta.setInt(2, Integer.parseInt(idPaciente));
                cadastroConsulta.setInt(3, Integer.parseInt(idMedico));
                cadastroConsulta.setDate(4, new java.sql.Date(formatBD.parse(novoHorario).getTime()));
            cadastroConsulta.execute();
            System.out.println("Consulta Atualizada\n\n");
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Consulta não Atualizada\n\n");
        }


    }

    public void removerVenda(){
        Scanner scanner = new Scanner(System.in);
       

        String cpf;
        int matricula;
        String horario;
    
       

        System.out.println("Informe o CPF:");
        cpf = scanner.next();
       
        System.out.println("Informe a matrícula do médico:");
        matricula = scanner.nextInt();
        System.out.println("Informe o horário da consulta (00/00/0000-hh:mm):");
        horario = scanner.next();
       

        try{

            System.out.println(horario);
            horario = horario.replace("-"," ");
            
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            System.out.println(format.parse(horario));
            SimpleDateFormat formatBD = new SimpleDateFormat("yyyy-MM-dd HH:mm");
           


            PreparedStatement buscarMedico = connection.prepareStatement("SELECT * FROM medico where matricula = ?");
            buscarMedico.setInt(1, matricula);
            ResultSet resultMedico = buscarMedico.executeQuery();

            PreparedStatement buscarPaciente = connection.prepareStatement("SELECT * FROM paciente where cpf = ?");
            buscarPaciente.setString(1, cpf);

            ResultSet resultPaciente = buscarPaciente.executeQuery();

            if(resultMedico.next() == false){
                System.out.println("Médico não encontrado");
            }else
            if(resultPaciente.next() == false){
                System.out.println("Paciente não encontrado");
            }else{

                String idPaciente = resultPaciente.getString("id");
                String idMedico = resultMedico.getString("id");

                PreparedStatement cadastroConsulta = connection.prepareStatement("DELETE FROM consulta WHERE id_paciente = ? AND id_medico = ? AND horario = ?");
                cadastroConsulta.setInt(1, Integer.parseInt(idPaciente));
                cadastroConsulta.setInt(2, Integer.parseInt(idMedico));
                cadastroConsulta.setDate(3, new java.sql.Date(format.parse(horario).getTime()));

            cadastroConsulta.execute();
            System.out.println("Consulta Removida\n\n");
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Consulta não Removida\n\n");
        }


    }


    public void relatorioVenda() {

       

        try{

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT consulta.*, medico.nome as medico_nome, medico.matricula, medico.especialidade, medico.salario, paciente.nome as paciente_nome, paciente.cpf, paciente.doenca FROM consulta INNER JOIN medico on medico.id = consulta.id_medico INNER JOIN paciente on paciente.id = consulta.id_paciente");

            ResultSet result = preparedStatement.executeQuery();

            int index = 0;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            while(result.next()){
                index++;
                System.out.println("Consulta "+index+":");

                Double valor = result.getDouble("valor");
                NumberFormat numberFormat = NumberFormat.getInstance();
                String falorFormatado = numberFormat.format(valor);

                
                String dataFormatada = format.format(result.getDate("horario"));
             
                System.out.print("Nome do médico: ");
                System.out.println(result.getString("medico_nome")+" ("+result.getInt("matricula")+")");
                System.out.print("Nome paciente: ");
                System.out.println(result.getString("paciente_nome")+" ("+result.getString("cpf")+")");
                System.out.print("Horário: ");
                System.out.println(dataFormatada);
                System.out.print("Valor: ");
                System.out.println(falorFormatado);
                System.out.println("\n");
                
        }
        if(index == 0){
            System.out.println("CPF não encontrado");
        }
        System.out.println("\n");
           
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Não foi possível encontrar o paciente");
            System.out.println("\n");
        }

       
    }
}
