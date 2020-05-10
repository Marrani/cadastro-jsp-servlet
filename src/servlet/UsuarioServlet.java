package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;
import sun.awt.image.ByteArrayImageSource;

@WebServlet("/UsuarioServlet")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public UsuarioServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listartodos";
		String user = request.getParameter("user");

		try {

			if (acao.equalsIgnoreCase("delete")) {
				daoUsuario.deletar(user);

				RequestDispatcher view = request.getRequestDispatcher("cadastrousuario.jsp");
				request.setAttribute("msg", "Exclusão efetuada!");

				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("editar")) {

				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);

				RequestDispatcher view = request.getRequestDispatcher("cadastrousuario.jsp");

				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("listartodos")) {

				RequestDispatcher view = request.getRequestDispatcher("cadastrousuario.jsp");

				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("download")) {

				BeanCursoJsp usuario = daoUsuario.consultar(user);

				if (usuario != null) {

					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + usuario.getContentType().split("\\/")[1]);

					/* Converte a base 64 da imagem do banco para byte[] */

					byte[] imagemFotoBytes = new Base64().decodeBase64(usuario.getFotoBase64());

					/* Converte a base 64 em um objeto de entrada para processar */

					InputStream is = new ByteArrayInputStream(imagemFotoBytes);

					/* Inicio da resposta para o navegador */

					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);

					}

					os.flush();
					os.close();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("cadastrousuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");
			String ibge = request.getParameter("ibge");
			String sexo = request.getParameter("sexo");
			String perfil = request.getParameter("perfil");

			BeanCursoJsp usuario = new BeanCursoJsp();

			usuario.setId((id != null && !id.isEmpty()) ? Long.parseLong(id) : null);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setUf(uf);
			usuario.setIbge(ibge);
			usuario.setSexo(sexo);
			usuario.setPerfil(perfil);
			if(request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on")) {
				usuario.setAtivo(true);
			} else {
				usuario.setAtivo(false);
			}

			try {
				/* Inicio File upload de imagens e pdf */
				if (ServletFileUpload.isMultipartContent(request)) {

					Part imagemFoto = request.getPart("foto");

					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

						String fotoBase64 = new Base64()
								.encodeBase64String(converteStremParabyte(imagemFoto.getInputStream()));

						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());

						/* Inicio miniatura imagem */

						/* Transforma emum bufferedImage */
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

						/* Pega o tipo da imagem */
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

						/* Cria imagem em miniatura */
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();

						/* Escrever imagem novamente */
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);

						String miniaturaBase64 = "data:image/png;base64,"
								+ DatatypeConverter.printBase64Binary(baos.toByteArray());

						usuario.setFotoBase64Miniatura(miniaturaBase64);
						/* Fim miniatura imagem */

					} else {
						usuario.setAtualizarImagem(false);
					}

				}
				/* Fim File upload de imagens e pdf */

				boolean podeInserir = true;
				if (id == null || id.isEmpty() && daoUsuario.validarLogin(login)) {

					daoUsuario.salvar(usuario);
					request.setAttribute("msg", "Cadastro efetuado com sucesso!");

				} else {
					if (!daoUsuario.validarLogin(login)) {
						request.setAttribute("msg", "Usuário já existente com o mesmo login!");
						podeInserir = false;

					}
				}

				if (id != null && !id.isEmpty()) {
					if (daoUsuario.validarLoginUpdate(login, id)) {
						daoUsuario.atualizar(usuario);
						request.setAttribute("msg", "Edição efetuada com sucesso!");

					} else {
						request.setAttribute("msg", "Login já existe para outro usuário!");

					}

				}

				if (!podeInserir) {
					request.setAttribute("user", usuario);

				}

				request.setAttribute("usuarios", daoUsuario.listar());

				RequestDispatcher view = request.getRequestDispatcher("cadastrousuario.jsp");

				view.forward(request, response);

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}

	private byte[] converteStremParabyte(InputStream imagem) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int reads = imagem.read();

		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();

		}

		return baos.toByteArray();

	}
}
