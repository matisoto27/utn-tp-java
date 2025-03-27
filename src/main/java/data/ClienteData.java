package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;

import entities.Cliente;

public class ClienteData {

	public LinkedList<Cliente> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Cliente> clientes = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM clientes");
			if (rs != null) {
				while (rs.next()) {
					Cliente cli = new Cliente();
					cli.setDni(rs.getString("dni"));
					cli.setNombre(rs.getString("nombre"));
					cli.setApellido(rs.getString("apellido"));
					cli.setFechaNac(rs.getObject("fecha_nac", LocalDate.class));
					cli.setEmail(rs.getString("email"));
					cli.setTelefono(rs.getString("telefono"));
					cli.setContrasena(rs.getString("contrasena"));
					clientes.add(cli);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clientes;
	}

	public Cliente getByQuery(String query, Object... params) {
		Cliente c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				c = new Cliente();
				c.setDni(rs.getString("dni"));
				c.setNombre(rs.getString("nombre"));
				c.setApellido(rs.getString("apellido"));
				c.setFechaNac(rs.getObject("fecha_nac", LocalDate.class));
				c.setEmail(rs.getString("email"));
				c.setTelefono(rs.getString("telefono"));
				c.setContrasena(rs.getString("contrasena"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
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
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO clientes (dni, nombre, apellido, fecha_nac, email, telefono, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, cli.getDni());
			stmt.setString(2, cli.getNombre());
			stmt.setString(3, cli.getApellido());
			stmt.setObject(4, cli.getFechaNac());
			stmt.setString(5, cli.getEmail());
			stmt.setString(6, cli.getTelefono());
			stmt.setString(7, cli.getContrasena());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(Cliente cli) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE clientes SET nombre = ?, apellido = ?, fecha_nac = ?, email = ?, telefono = ?, contrasena = ? WHERE dni = ?");
			stmt.setString(1, cli.getNombre());
			stmt.setString(2, cli.getApellido());
			stmt.setObject(3, cli.getFechaNac());
			stmt.setString(4, cli.getEmail());
			stmt.setString(5, cli.getTelefono());
			stmt.setString(6, cli.getContrasena());
			stmt.setString(7, cli.getDni());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}