package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import beans.BeanProdutos;
import beans.BeanTelefones;
import connection.SingleConnection;

public class DaoTelefones {

	private Connection connection;

	public DaoTelefones() {
		connection = SingleConnection.getConnection();

	}

	public void salvar(BeanTelefones telefone) {

		try {

			String sql = "insert into telefone(numero, tipo, usuario) values (?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);

			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public List<BeanTelefones> listar(Long user) throws Exception {

		List<BeanTelefones> listar = new ArrayList<BeanTelefones>();

		String sql = "select * from telefone where usuario = " + user;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {

			BeanTelefones telefone = new BeanTelefones();

			telefone.setId(resultset.getLong("id"));
			telefone.setNumero(resultset.getString("numero"));
			telefone.setTipo(resultset.getString("tipo"));
			telefone.setUsuario(resultset.getLong("usuario"));

			listar.add(telefone);
		}

		return listar;
	}

	public void deletar(String id) {

		try {
			String sql = "delete from telefone where id = '" + id + "'";
			PreparedStatement statement;
			statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

}
