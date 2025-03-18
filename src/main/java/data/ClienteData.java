package data;

import java.sql.*;

import entities.Cliente;

public class ClienteData {
	
	public Cliente getByDni(Cliente cli) {
		Cliente c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM clientes WHERE dni = ?");
			stmt.setString(1, cli.getDni());
			rs = stmt.executeQuery();
			if(rs != null && rs.next()) {
				c = new Cliente();
				c.setDni(rs.getString("dni"));
				c.setNombre(rs.getString("nombre"));
				c.setApellido(rs.getString("apellido"));
				c.setFechaNac(rs.getDate("fecha_nac"));
				c.setEmail(rs.getString("email"));
				c.setTelefono(rs.getString("telefono"));
				c.setContrasena(rs.getString("contrasena"));
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
		return c;
	}
	
	public Cliente getByDniContrasena(Cliente cli) {
		Cliente c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM clientes WHERE dni = ? AND contrasena = ?");
			stmt.setString(1, cli.getDni());
			stmt.setString(2, cli.getContrasena());
			rs = stmt.executeQuery();
			if(rs != null && rs.next()) {
				c = new Cliente();
				c.setDni(rs.getString("dni"));
				c.setNombre(rs.getString("nombre"));
				c.setApellido(rs.getString("apellido"));
				c.setFechaNac(rs.getDate("fecha_nac"));
				c.setEmail(rs.getString("email"));
				c.setTelefono(rs.getString("telefono"));
				c.setContrasena(rs.getString("contrasena"));
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
		return c;
	}
	
	public void add(Cliente cli) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO clientes (dni, nombre, apellido, fecha_nac, email, telefono, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, cli.getDni());
			stmt.setString(2, cli.getNombre());
			stmt.setString(3, cli.getApellido());
			stmt.setDate(4, cli.getFechaNac());
			stmt.setString(5, cli.getEmail());
			stmt.setString(6, cli.getTelefono());
			stmt.setString(7, cli.getContrasena());	
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