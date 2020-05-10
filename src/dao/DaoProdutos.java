package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCategoria;
import beans.BeanCursoJsp;
import beans.BeanProdutos;
import connection.SingleConnection;

public class DaoProdutos {
	
	private Connection connection;

	public DaoProdutos() {
		connection = SingleConnection.getConnection();
		
	}
	
	
	public void salvar(BeanProdutos produtos) {
		
		try {
			
			String sql = "insert into produtos(nome, quantidade, valor,categoria_id) values (?,?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			
			insert.setString(1, produtos.getNome());
			insert.setDouble(2, produtos.getQuantidade());
			insert.setDouble(3, produtos.getValor());
			insert.setLong(4, produtos.getCategoria_id());
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
	
	
	public List<BeanProdutos> listar() throws Exception {
		
		List<BeanProdutos> listar = new ArrayList<BeanProdutos>();
		
		String sql = "select * from produtos";
		 PreparedStatement statement = connection.prepareStatement(sql);
		 ResultSet resultset = statement.executeQuery();
		 
		 while(resultset.next()) {
			 
			 BeanProdutos produtos = new BeanProdutos();
			 
			 produtos.setId(resultset.getLong("id"));
			 produtos.setNome(resultset.getString("nome"));
			 produtos.setQuantidade(resultset.getDouble("quantidade"));
			 produtos.setValor(resultset.getDouble("valor"));
			 produtos.setCategoria_id(resultset.getLong("categoria_id"));
		
			 
			 listar.add(produtos);
		 }
		
		return listar;
	}
	
	
	public List<BeanCategoria> listarCategoria() throws SQLException {
		List<BeanCategoria> listarCategoria = new ArrayList<BeanCategoria>();
		String sql = "SELECT * FROM categoria";
		PreparedStatement statement = connection.prepareStatement(sql);
		 ResultSet resultset = statement.executeQuery();
		 
		 while(resultset.next()) {
			 BeanCategoria beanCategoria = new BeanCategoria();
			 
			 beanCategoria.setId(resultset.getLong("id"));
			 beanCategoria.setNome(resultset.getString("nome"));
			 
			 listarCategoria.add(beanCategoria);
		 }
		
		return listarCategoria;
		
	}
	
	
	public BeanProdutos consultar(String id)  throws Exception{
		String sql ="select * from produtos where id='" + id +"'";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			BeanProdutos beanProdutos = new BeanProdutos();
			beanProdutos.setId(resultSet.getLong("id"));
			beanProdutos.setNome(resultSet.getString("nome"));
			beanProdutos.setQuantidade(resultSet.getDouble("quantidade"));
			beanProdutos.setValor(resultSet.getDouble("valor"));
			beanProdutos.setCategoria_id(resultSet.getLong("categoria_id"));
			
			
			return beanProdutos;
		}
		return null;
	}
	
	
	public void atualizar(BeanProdutos produtos) {
		try {
			
			String sql = "update produtos set nome = ?, quantidade = ?, valor = ?, categoria_id = ? where id = " + produtos.getId();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produtos.getNome());
			statement.setDouble(2, produtos.getQuantidade());
			statement.setDouble(3, produtos.getValor());
			statement.setLong(4, produtos.getCategoria_id());
			statement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	public void deletar(String id) {
		
	
		try {
			String sql = "delete from produtos where id = '" + id + "'";
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
