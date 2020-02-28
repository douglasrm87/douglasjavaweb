package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//https://www.alura.com.br/artigos/recebendo-dados-de-um-formulario-html-com-servlets
// http://localhost:8181/nosqlweb/nosql
@WebServlet("/nosql")
public class ExemploServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExemploServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int retorno = receberDadosHTML(request, response);
		switch (retorno) {
		case 0:
			request.getRequestDispatcher("WEB-INF/login.html").forward(request, response);
			break;
		case 1:
			request.getRequestDispatcher("WEB-INF/incluirlivro.html").forward(request, response);
			break;
		case 2:
			request.getRequestDispatcher("WEB-INF/logininvalido.html").forward(request, response);
			break;
		case 3:

			break;
		default:
			break;
		}
	}

	// https://www.devmedia.com.br/usando-html-forms-com-servlets/28533
	private int receberDadosHTML(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("name");
		String pass = request.getParameter("password");
		System.out.println("Nome: " + id);
		System.out.println("Password: " + pass);
		response.setContentType("text/html");
		RequestDispatcher rd = null;
		if ("teste".equals(id) && "teste".equals(pass)) {
			return 1;
		} else if ((id != null) && (pass != null)) {
			return 2;
		}
		return 0;

	}

}
