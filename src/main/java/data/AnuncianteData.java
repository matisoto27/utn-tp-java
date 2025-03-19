package data;

import java.sql.*;
import java.util.LinkedList;

import entities.Anunciante;

public class AnuncianteData {
	
	public LinkedList<Anunciante> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Anunciante> anunciantes = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM anunciantes");
			if(rs != null) {
				while(rs.next()) {
					Anunciante anun = new Anunciante();
					anun.setIdAnunciante(rs.getInt("id_anunciante"));
					anun.setNombre(rs.getString("nombre"));
					anun.setEmail(rs.getString("email"));
					anun.setTelefono(rs.getString("telefono"));
					anun.setUsuario(rs.getString("usuario"));
					anun.setContrasena(rs.getString("contrasena"));
					anunciantes.add(anun);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return anunciantes;
	}
	
	public Anunciante getByNombre(Anunciante anun) {
		Anunciante a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM anunciantes WHERE nombre = ?");
			stmt.setString(1, anun.getNombre());
			rs = stmt.executeQuery();
			if(rs != null && rs.next()) {
				a = new Anunciante();
				a.setNombre(rs.getString("nombre"));
				a.setEmail(rs.getString("email"));
				a.setTelefono(rs.getString("telefono"));
				a.setUsuario(rs.getString("usuario"));
				a.setContrasena(rs.getString("contrasena"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}
	
	public Anunciante getByUsuario(Anunciante anun) {
		Anunciante a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM anunciantes WHERE usuario = ?");
			stmt.setString(1, anun.getUsuario());
			rs = stmt.executeQuery();
			if(rs != null && rs.next()) {
				a = new Anunciante();
				a.setNombre(rs.getString("nombre"));
				a.setEmail(rs.getString("email"));
				a.setTelefono(rs.getString("telefono"));
				a.setUsuario(rs.getString("usuario"));
				a.setContrasena(rs.getString("contrasena"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}
	
	public Anunciante getByUsuarioContrasena(Anunciante anun) {
		Anunciante a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM anunciantes WHERE usuario = ? AND contrasena = ?");
			stmt.setString(1, anun.getUsuario());
			stmt.setString(2, anun.getContrasena());
			rs = stmt.executeQuery();
			if(rs != null && rs.next()) {
				a = new Anunciante();
				a.setNombre(rs.getString("nombre"));
				a.setEmail(rs.getString("email"));
				a.setTelefono(rs.getString("telefono"));
				a.setUsuario(rs.getString("usuario"));
				a.setContrasena(rs.getString("contrasena"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}
	
	public void add(Anunciante anun) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO anunciantes (nombre, email, telefono, usuario, contrasena) VALUES (?, ?, ?, ?, ?)");
			stmt.setString(1, anun.getNombre());
			stmt.setString(2, anun.getEmail());
			stmt.setString(3, anun.getTelefono());
			stmt.setString(4, anun.getUsuario());	
			stmt.setString(5, anun.getContrasena());	
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}