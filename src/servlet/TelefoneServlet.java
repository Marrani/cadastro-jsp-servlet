package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.BeanTelefones;
import dao.DaoTelefones;
import dao.DaoUsuario;

/**
 * Servlet implementation class TelefoneSevlet
 */
@WebServlet("/TelefoneServlet")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DaoUsuario daoUsuario = new DaoUsuario();
	
	DaoTelefones daoTelefones = new DaoTelefones();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TelefoneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
		
		String user = request.getParameter("user");
		String acao = request.getParameter("acao");
		
		try {
			
			if(acao.equalsIgnoreCase("delete")) {
				
				daoTelefones.deletar(user);

			} else if(acao.equalsIgnoreCase("listar")){
			
			BeanCursoJsp usuario = daoUsuario.consultar(user);
			
			request.getSession().setAttribute("userEscolhido", usuario);
			
			}
			
			request.setAttribute("telefones", daoTelefones.listar(beanCursoJsp.getId()));
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("telefones.jsp");
			//request.setAttribute("telefones", daoUsuario.listar());
			dispatcher.forward(request, response);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
		
		String numero = request.getParameter("numero");
		String tipo = request.getParameter("tipo");
		
		BeanTelefones beanTelefones = new BeanTelefones();
		
		beanTelefones.setNumero(numero);
		beanTelefones.setTipo(tipo);
		beanTelefones.setUsuario(beanCursoJsp.getId());
		
		
		daoTelefones.salvar(beanTelefones);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("telefones.jsp");
		try {
			request.setAttribute("telefones", daoTelefones.listar(beanCursoJsp.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatcher.forward(request, response);
		
		
		
	
		
	}

}
