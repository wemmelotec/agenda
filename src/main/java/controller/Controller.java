package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		// Imprime no console o path que chegou no servlet
		// System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		}else if (action.equals("/delete")) {
			removerContato(request, response);
		} 
		else {
			response.sendRedirect("index.html");
		}
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		// teste de conexão
		// dao.testeConexao();
	}

	// listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// esta linha apenas redirecionava ao jsp sem listar nada inicialmente
		// response.sendRedirect("agenda.jsp");

		// criando um objeto que receberá os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();

		// teste de recebimento da lista
		// for (int i = 0; i < lista.size(); i++) {
		// System.out.println(lista.get(i).getId());
		// System.out.println(lista.get(i).getNome());
		// System.out.println(lista.get(i).getFone());
		// System.out.println(lista.get(i).getEmail());
		// }

		// encaminhar a lista para o documento jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	// salvar o contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebimento de formulário
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));
		// setar os parâmetros recebidos na javabeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar o método inserirContato na DAO passando o objeto contato e salvando
		// no banco
		dao.inserirContato(contato);
		// após salvar redirecionar para o agenda.jsp
		response.sendRedirect("main");
	}

	// editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebimento do id do contato que será editado
		String id = request.getParameter("id");
		// teste para saber se estou recebendo o id
		// System.out.println(id);
		// setar o id na variavel javabeans
		contato.setId(id);
		// executar o método selecionarContato
		dao.selecionarContato(contato);
		// teste de recebimento do contato
		// System.out.println(contato.getId());
		// System.out.println(contato.getNome());
		// System.out.println(contato.getFone());
		// System.out.println(contato.getEmail());
		// setar os valores que vinheram do banco no objeto javabeans
		request.setAttribute("id", contato.getId());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// encaminhar para o editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// teste para saber se estou recebendo os dados que foram alterados e enviados
		// pela jps
		// System.out.println(request.getParameter("id"));
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));
		// setar as variaveis jbeans
		contato.setId(request.getParameter("id"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// executar o alterar contato
		dao.alterarContato(contato);
		// redirecionar para o agenda.jps atualizando as alterações
		response.sendRedirect("main");

	}
	
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		// teste para saber se estou recebendo o parâmetro id 
		//System.out.println(id);
		// setar o id no javabeans
		contato.setId(id);
		dao.deletarContato(contato);
		response.sendRedirect("main");
	}

}
