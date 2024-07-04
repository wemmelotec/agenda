package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {

	/** Módulo de conexão **/
	// Parâmetros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";

	// Método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	// teste de conexão
	// public void testeConexao() {
	// try {
	// Connection con = conectar();
	// System.out.println(con);
	// con.close();
	// } catch (Exception e) {
	// System.out.println(e);
	// }
	// }

	// Método de inserção
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome, fone, email) values (?, ?, ?)";
		try {
			// abrir a conexão com o banco
			Connection con = conectar();
			// preparar a query para ser executada
			PreparedStatement pst = con.prepareStatement(create);
			// substituir os parâmetros (?) pelo conteúdo das variáveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// executar a query
			pst.executeUpdate();
			// encerrar a conexão com o banco
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// método com retorno de um vetor dinâmico
	public ArrayList<JavaBeans> listarContatos() {
		// criando um objeto do tipo arraylist para armazenar os objetos vindos do banco
		ArrayList<JavaBeans> contatos = new ArrayList<>();

		String read = "select * from contatos";//order by nome
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				// variáveis de apoio que recebem os dados do banco
				String id = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getNString(3);
				String email = rs.getNString(4);
				// populando o arraylista, a lista dinâmica que foi referenciada com JavaBeans
				contatos.add(new JavaBeans(id, nome, fone, email));
			}
			con.close();
			return contatos;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	//crud update
	//selecionar o contato
	public void selecionarContato(JavaBeans contato) {
		//JavaBeans contato = new JavaBeans();
		String selecionar = "select * from contatos where id=?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(selecionar);
			pst.setString(1, contato.getId());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				contato.setId(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getNString(3));
				contato.setEmail(rs.getNString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
