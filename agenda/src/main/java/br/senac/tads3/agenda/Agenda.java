/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads3.agenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando.tsuda
 */
public class Agenda extends ConexaoBD {
  
  private static Scanner entrada = new Scanner(System.in);
  
  public void listar() throws SQLException, ClassNotFoundException{
      // 1) Abrir conexao
    PreparedStatement stmt = null;
    Connection conn = null;  
    
    try{
    conn = obterConexao();  
    String sql = "SELECT * FROM TB_CONTATO";
        System.out.println(stmt.executeQuery(sql)); 
          
	} catch (SQLException ex) {
           conn.close(); 
	  System.out.println("Erro ao fechar conn.");
	}
      }

  
  
  
public void alterar(){
      
    System.out.print("Digite o ID do contato: ");
    String id = entrada.nextLine();
      
    System.out.print("Digite o nome completo do contato: ");
    String nome = entrada.nextLine();
    
    System.out.print("Digite a data de nascimento no formato dd/mm/aaaa: ");
    String strDataNasc = entrada.nextLine();
    
    System.out.print("Digite o e-mail");
    String email = entrada.nextLine();
    
    System.out.print("Digite o telefone no formato 99 99999-9999");
    String telefone = entrada.nextLine();
    
    // 1) Abrir conexao
    PreparedStatement stmt = null;
    Connection conn = null;
    
    String sql = "UPDATE TB_CONTATO SET NM_CONTATO =?, DT_NASCIMENTO = ?, "
   + "VL_TELEFONE =?, VL_EMAIL = ?, DT_CADASTRO =? WHERE ID_CONTATO =? ";
    
    try {
      conn = obterConexao();
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, nome);
      
      DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
      Date dataNasc = null;
      try {
		dataNasc = formatador.parse(strDataNasc);
			  } catch (ParseException ex) {
		System.out.println("Data de nascimento inválida.");
		return;
      }
      stmt.setDate(2, new java.sql.Date(dataNasc.getTime()));
      stmt.setString(3, telefone);
      stmt.setString(4, email);
      stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
      stmt.setString(6, id);
      
      // 2) Executar SQL
      stmt.executeUpdate();
      System.out.println("Contato alterado com sucesso");
 
    } catch (SQLException e) {
      System.out.println("Não foi possível executar.");
    } catch (ClassNotFoundException e) {
      System.out.println("Não foi possível executar.");
    } finally {
      if (stmt != null) {
	try {
	 stmt.close();
	} catch (SQLException ex) {
	 System.out.println("Erro ao fechar stmt.");
	}
		  }
		  if (conn != null) {
	try {
	 conn.close();
	} catch (SQLException ex) {
	 System.out.println("Erro ao fechar conn.");
	}
		  }
	}
      
  
  }

  public void incluir() {
    
    System.out.print("Digite o nome completo do contato: ");
    String nome = entrada.nextLine();
    
    System.out.print("Digite a data de nascimento no formato dd/mm/aaaa: ");
    String strDataNasc = entrada.nextLine();
    
    System.out.print("Digite o e-mail");
    String email = entrada.nextLine();
    
    System.out.print("Digite o telefone no formato 99 99999-9999");
    String telefone = entrada.nextLine();
    
    // 1) Abrir conexao
    PreparedStatement stmt = null;
    Connection conn = null;
    
    String sql = "INSERT INTO TB_CONTATO (NM_CONTATO, DT_NASCIMENTO, "
	    + "VL_TELEFONE, VL_EMAIL, DT_CADASTRO) "
	    + "VALUES (?, ?, ?, ?, ?)";
    
    try {
      conn = obterConexao();
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, nome);
      
      DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
      Date dataNasc = null;
      try {
	dataNasc = formatador.parse(strDataNasc);
      } catch (ParseException ex) {
	System.out.println("Data de nascimento inválida.");
	return;
      }
      stmt.setDate(2, new java.sql.Date(dataNasc.getTime()));
      stmt.setString(3, telefone);
      stmt.setString(4, email);
      stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
      
      // 2) Executar SQL
      stmt.executeUpdate();
      System.out.println("Contato cadastrado com sucesso");
 
    } catch (SQLException e) {
      System.out.println("Não foi possível executar.");
    } catch (ClassNotFoundException e) {
      System.out.println("Não foi possível executar.");
    } finally {
      if (stmt != null) {
	try {
	  stmt.close();
	} catch (SQLException ex) {
	  System.out.println("Erro ao fechar stmt.");
	}
      }
      if (conn != null) {
	try {
	  conn.close();
	} catch (SQLException ex) {
	  System.out.println("Erro ao fechar conn.");
	}
      }
    }
    
    
    
    // 3) Fechar conexao
    
  }

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    Agenda instancia = new Agenda();

    do {
      System.out.println("***** DIGITE UMA OPÇÃO *****");
      System.out.println("(1) Listar contatos");
      System.out.println("(2) Incluir novo contato");
      System.out.println("(3) Alterar um contato");
      System.out.println("(9) Sair");
      System.out.print("Opção: ");
      
      String strOpcao = entrada.nextLine();
      int opcao = Integer.parseInt(strOpcao);
      switch (opcao) {
	case 1:
            instancia.listar();
	  break;
	case 2:
	  instancia.incluir();
	  break;
        case 3:
	  instancia.alterar();
	  break;
	case 9:
	  System.exit(0);
	  break;
	default:
	  System.out.println("OPÇÃO INVÁLIDA");
      }
    } while (true);
    
  }

}
