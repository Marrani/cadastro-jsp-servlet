package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

/**
 * Servlet implementation class PesquisaServlet
 */
@WebServlet("/pesquisaServlet")
public class PesquisaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DaoUsuario daoUsuario = new DaoUsuario();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PesquisaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("descricao");
		
		if(nome != null && !nome.isEmpty()) {
			
			try {
				List<BeanCursoJsp>  listaPesquisa = daoUsuario.listar(nome);
				request.setAttribute("usuarios", listaPesquisa);
				RequestDispatcher view = request.getRequestDispatcher("cadastrousuario.jsp");
				view.forward(request, response);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				request.setAttribute("usuarios", daoUsuario.listar());
				request.setAttribute("msg", "usuario não existe");
				RequestDispatcher view = request.getRequestDispatcher("cadastrousuario.jsp");
				view.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}
		
	}

}
