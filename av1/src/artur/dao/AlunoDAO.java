package artur.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import artur.bd.DB;
import artur.model.Aluno;



public class AlunoDAO implements IDAO<Aluno> {
	private static final int limit = 10;
	private DB db = new DB();
	
	@Override
	public void insert(Aluno t) {
		// TODO Auto-generated method stub
		try (Connection con = db.getConnection()) {
			String sql = "INSERT INTO alunos (nome, nascimento, sexo, endereco, telefone, email) VALUES (?, ?, ?, ?, ?, ?);";
			
			PreparedStatement cmd = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			cmd.setString(1, t.getNome());
			cmd.setDate(2, new java.sql.Date(t.getNascimento().getTime()));
			cmd.setString(3, t.getSexo().toString());
			cmd.setString(4, t.getEndereco());
			cmd.setString(5, t.getTelefone());
			cmd.setString(6, t.getEmail());
			cmd.execute();
			
			// pegar a chave gerada
			ResultSet key = cmd.getGeneratedKeys();
			if (key.next()) {
				t.setId(key.getInt(1));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		try (Connection con = db.getConnection()) {
			String sql = "DELETE FROM alunos WHERE id=?;";
			
			PreparedStatement cmd = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			cmd.setInt(1, id);
			cmd.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Aluno d) {
		// TODO Auto-generated method stub
		if (d.getId() == null) throw new RuntimeException("Aluno com id nulo como este n�o pode ser exclu�do.");
		else delete(d.getId());
	}

	@Override
	public List<Aluno> selectAll() {
		// TODO Auto-generated method stub
		try (Connection con = db.getConnection()) {
			String sql = "SELECT nome FROM alunos WHERE id = ? ORDER BY nome DESC";
			
			PreparedStatement cmd = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = cmd.executeQuery();		//consulta
			List<Aluno> lista = new ArrayList();
					
			if (rs.next()) {
				Aluno d = new Aluno();
				d.setNome(rs.getString("nome"));
				d.setNascimento(rs.getDate("nascimento"));
				d.setSexo(rs.getString("sexo"));
				d.setEndereco(rs.getString("endereco"));
				d.setTelefone(rs.getString("telefone"));
				d.setEmail(rs.getString("email"));
			}
					
			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Aluno> selectPage(Integer page) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		try (Connection con = db.getConnection()) {
			String sql = "SELECT nome FROM alunos WHERE id = ? ORDER BY nome DESC OFFSET ? LIMIT" + limit;
			
			PreparedStatement cmd = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			int offset = (page - 1) * limit;		//primeiro resultado na lista
			cmd.setInt(1, offset);
			
			ResultSet rs = cmd.executeQuery();		//consulta
			List<Aluno> lista = new ArrayList();
					
			if (rs.next()) {
				Aluno d = new Aluno();
				d.setNome(rs.getString("nome"));
				d.setNascimento(rs.getDate("nascimento"));
				d.setSexo(rs.getString("sexo"));
				d.setEndereco(rs.getString("endereco"));
				d.setTelefone(rs.getString("telefone"));
				d.setEmail(rs.getString("email"));
			}
					
			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Aluno select(Integer id) {
		// TODO Auto-generated method stub
		try (Connection con = db.getConnection()) {
			String sql = "SELECT nome FROM alunos WHERE id = ?";
			
			PreparedStatement cmd = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			cmd.setInt(1, id);
			
			ResultSet rs = cmd.executeQuery();		//consulta
			
			if (rs.next()) {
				Aluno d = new Aluno();
				d.setNome(rs.getString("nome"));
				d.setNascimento(rs.getDate("nascimento"));
				d.setSexo(rs.getString("sexo"));
				d.setEndereco(rs.getString("endereco"));
				d.setTelefone(rs.getString("telefone"));
				d.setEmail(rs.getString("email"));
				
				return d;
			}
			
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Aluno t) {
		// TODO Auto-generated method stub
		if (t.getId() == null) throw new RuntimeException("Aluno com id nulo como este n�o pode ser exclu�do.");
		try (Connection con = db.getConnection()) {
			String sql = "UPDATE alunos SET nome = ?, nascimento = ?, sexo = ?, endereco = ?, telefone = ?, email = ? WHERE id = ?;";
			
			PreparedStatement cmd = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			cmd.setString(1, t.getNome());
			cmd.setString(2, t.getNascimento().toString());
			cmd.setString(3, t.getSexo().toString());
			cmd.setString(4, t.getEndereco());
			cmd.setString(5, t.getTelefone());
			cmd.setString(6, t.getEmail());
			cmd.setInt(7, t.getId());
			cmd.execute();
			
			// atualizando os telefones
			sql = "DELETE FROM alunos WHERE id_disciplina = ?";
			cmd.getConnection().prepareStatement(sql);
			cmd.setInt(1, t.getId());
			cmd.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}