package servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.BeanProdutos;
import dao.DaoProdutos;

@WebServlet("/ProdutosServlet")
public class ProdutosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoProdutos daoProdutos = new DaoProdutos();

	public ProdutosServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao") != null ? request.getParameter("acao"): "listartodos";
		String prod = request.getParameter("prod");
		try {
			if (acao.equalsIgnoreCase("listartodos")) {

				request.setAttribute("produtos", daoProdutos.listar());

			} else if (acao.equalsIgnoreCase("editar")) {
				BeanProdutos beanProdutos = daoProdutos.consultar(prod);

				request.setAttribute("prod", beanProdutos);

			} else if (acao.equalsIgnoreCase("delete")) {
				daoProdutos.deletar(prod);
				request.setAttribute("msg", "Exclusão efetuada!");
				request.setAttribute("produtos", daoProdutos.listar());
				
			}
			
			RequestDispatcher view = request.getRequestDispatcher("cadastroprodutos.jsp");
			request.setAttribute("categorias", daoProdutos.listarCategoria());
			view.forward(request, response);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String acao = request.getParameter("acao");
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			RequestDispatcher view = request.getRequestDispatcher("cadastroprodutos.jsp");

			try {
				request.setAttribute("produtos", daoProdutos.listar());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			view.forward(request, response);

		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			String categoria = request.getParameter("categoria_id");
			

			if (nome.isEmpty() || quantidade.isEmpty() || valor.isEmpty()) {
				request.setAttribute("msg", "Favor digitar todos os dados");

			} else {
				BeanProdutos produtos = new BeanProdutos();

				produtos.setId(!id.isEmpty() ? Long.parseLong(id) : null);
				produtos.setNome(nome);
				produtos.setQuantidade(Double.parseDouble(quantidade));
				String valorFormat = valor.replaceAll("\\.", "");
				valorFormat = valorFormat.replaceAll("\\,",".");
				produtos.setValor(Double.parseDouble(valorFormat));
				produtos.setCategoria_id(Long.parseLong(categoria));

				try {

					if (id == null || id.isEmpty()) {
						daoProdutos.salvar(produtos);

					} else if (id != null || !id.isEmpty()) {
						daoProdutos.atualizar(produtos);
						request.setAttribute("msg", "Cadastro efetuado com sucesso!");

					}

				} catch (Exception e) {

					e.printStackTrace();
				}

			}

		}

		RequestDispatcher view = request.getRequestDispatcher("cadastroprodutos.jsp");

		try {
			request.setAttribute("produtos", daoProdutos.listar());
			request.setAttribute("categorias", daoProdutos.listarCategoria());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.forward(request, response);

	}
}
