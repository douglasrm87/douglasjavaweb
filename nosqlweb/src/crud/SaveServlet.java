package crud;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cassandra.ExemploCassandra;

@WebServlet("/SaveServlet")
public class SaveServlet extends HttpServlet {
	private static final String NOSQL = "NOSQL";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// recebe do HTML os ados lidos.
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("nomeFunc");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String country = request.getParameter("country");

		// utilizar este objeto para servir de transporte
		Emp e = new Emp();
		e.setId(id);
		e.setName(name);
		e.setPassword(password);
		e.setEmail(email);
		e.setCountry(country);

		int status = EmpDao.gravarCassandra(e, NOSQL);
		if (status == 0) {
			out.print("<p>Dados gravados com sucesso.</p>");
			request.getRequestDispatcher("index.html").include(request, response);
		} else {
			out.println("<p>Problemas na gravação dos dados no Cassandra.</p>");
		}

		out.close();
	}

}
