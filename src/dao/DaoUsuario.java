package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {
	
	 private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}
	
	
	public void salvar(BeanCursoJsp usuario){
		
		try {
			
			String sql = "insert into usuario(login, senha, nome, cep, rua, bairro, cidade, uf, ibge, fotobase64, contenttype, fotobase64miniatura, ativo, sexo, perfil) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getCep());
			insert.setString(5, usuario.getRua());
			insert.setString(6, usuario.getBairro());
			insert.setString(7, usuario.getCidade());
			insert.setString(8, usuario.getUf());
			insert.setString(9, usuario.getIbge());
			insert.setString(10, usuario.getFotoBase64());
			insert.setString(11, usuario.getContentType());
			insert.setString(12, usuario.getFotoBase64Miniatura());
			insert.setBoolean(13, usuario.isAtivo());
			insert.setString(14, usuario.getSexo());
			insert.setString(15, usuario.getPerfil());
			insert.execute();
			connection.commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				connection.rollback();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
	public List<BeanCursoJsp> listar(String nome) throws SQLException{
		String sql = "select * from usuario where login <> 'admin' and  LOWER	(nome) like LOWER ('%"+nome+"%')";
		return consultarUsuarios(sql);
	}
	
	public List<BeanCursoJsp> listar() throws Exception {
		
		String sql = "select * from usuario where login <> 'admin'";
		return consultarUsuarios(sql);
		
		
	}


private List<BeanCursoJsp> consultarUsuarios(String sql) throws SQLException {
	List<BeanCursoJsp> listar = new ArrayList<BeanCursoJsp>();
	PreparedStatement statement = connection.prepareStatement(sql);
	 ResultSet resultset = statement.executeQuery();
	 
	 while(resultset.next()) {
		 
		 BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
		 
		 beanCursoJsp.setId(resultset.getLong("id"));
		 beanCursoJsp.setLogin(resultset.getString("login"));
		 beanCursoJsp.setSenha(resultset.getString("senha"));
		 beanCursoJsp.setNome(resultset.getString("nome"));
		 beanCursoJsp.setCep(resultset.getString("cep"));
		 beanCursoJsp.setRua(resultset.getString("rua"));
		 beanCursoJsp.setBairro(resultset.getString("bairro"));
		 beanCursoJsp.setCidade(resultset.getString("cidade"));
		 beanCursoJsp.setUf(resultset.getString("uf"));
		 beanCursoJsp.setIbge(resultset.getString("ibge"));
//			 beanCursoJsp.setFotoBase64(resultset.getString("fotobase64"));
		 beanCursoJsp.setFotoBase64Miniatura(resultset.getString("fotobase64miniatura"));
		 beanCursoJsp.setContentType(resultset.getString("contenttype"));
		 beanCursoJsp.setSexo(resultset.getString("sexo"));
		 beanCursoJsp.setPerfil(resultset.getString("perfil"));
		 listar.add(beanCursoJsp);
	 }
	 return listar;
}
	
	public void deletar(String id){
		try {
			
		String sql = "delete from usuario where id = '" + id + "'";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
	}


	public BeanCursoJsp consultar(String id)  throws Exception{
		String sql ="select * from usuario where id='" + id +"' and login <> 'admin'" ;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
		    beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setUf(resultSet.getString("uf"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			beanCursoJsp.setFotoBase64(resultSet.getString("fotoBase64"));
			beanCursoJsp.setContentType(resultSet.getString("contentType"));
			beanCursoJsp.setAtivo(resultSet.getBoolean("ativo"));
			beanCursoJsp.setSexo(resultSet.getString("sexo"));
			beanCursoJsp.setPerfil(resultSet.getString("perfil"));
			
			
			return beanCursoJsp;
		}
		return null;
	}
	
	public boolean validarLogin(String login)  throws Exception{
		String sql ="select count(1) as qtd from usuario where login='" + login +"'";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}
		return false;
	}
	
	
	
	public boolean validarLoginUpdate(String login, String id)  throws Exception{
		String sql ="select count(1) as qtd from usuario where login='"+ login +"' and id <> " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			return resultSet.getInt("qtd") <= 0;
		}
		return false;
	}
	

	public void atualizar(BeanCursoJsp usuario) {
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append(" update usuario set login = ? , senha = ?, nome = ?, cep = ?, rua = ?, bairro = ?, cidade = ?, uf = ?, ibge = ? , ativo = ?, sexo = ?, perfil = ?");
			
			if(usuario.isAtualizarImagem()) {
				
				sql.append(", fotobase64 = ?, contenttype = ? , fotobase64miniatura = ?");
			}
					sql.append(" where id = " + usuario.getId());
			
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getCep());
			statement.setString(5, usuario.getRua());
			statement.setString(6, usuario.getBairro());
			statement.setString(7, usuario.getCidade());
			statement.setString(8, usuario.getUf());
			statement.setString(9, usuario.getIbge());
			statement.setBoolean(10, usuario.isAtivo());
			statement.setString(11, usuario.getSexo());
			statement.setString(12, usuario.getPerfil());
			
			if(usuario.isAtualizarImagem()) {
				
				statement.setString(13, usuario.getFotoBase64());
				statement.setString(14, usuario.getContentType());
				statement.setString(15, usuario.getFotoBase64Miniatura());
				
			}
			
			
			
			statement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
		
		
		
		
	}

}
