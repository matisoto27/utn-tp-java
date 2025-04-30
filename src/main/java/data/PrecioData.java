package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import entities.Anunciante;
import entities.Precio;
import entities.Propiedad;

public class PrecioData {

	public LinkedList<Precio> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Precio> precios = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM precios pre INNER JOIN propiedades prop "
					+ "ON pre.nro_propiedad = prop.nro_propiedad AND pre.id_anunciante = prop.id_anunciante "
					+ "INNER JOIN anunciantes a ON prop.id_anunciante = a.id_anunciante");
			if (rs != null) {
				while (rs.next()) {
					Precio p = new Precio();
					p.setPropiedad(new Propiedad());
					p.getPropiedad().setNroPropiedad(rs.getInt("nro_propiedad"));
					p.getPropiedad().setAnunciante(new Anunciante());
					p.getPropiedad().getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
					p.getPropiedad().getAnunciante().setNombre(rs.getString("nombre"));
					p.getPropiedad().getAnunciante().setEmail(rs.getString("email"));
					p.getPropiedad().getAnunciante().setTelefono(rs.getString("telefono"));
					p.getPropiedad().getAnunciante().setUsuario(rs.getString("usuario"));
					p.getPropiedad().getAnunciante().setContrasena(rs.getString("contrasena"));
					p.getPropiedad().setDireccion(rs.getString("direccion"));
					p.getPropiedad().setPiso(rs.getInt("piso"));
					p.getPropiedad().setDepto(rs.getString("depto"));

					p.setFechaDesde(rs.getObject("fecha_desde", LocalDate.class));
					p.setValor(rs.getDouble("valor"));

					precios.add(p);
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
		return precios;
	}

	public Precio getUltimoByPropiedad(Propiedad prop) {
		Precio p = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM precios pre "
					+ "INNER JOIN propiedades prop " + "ON pre.id_anunciante = prop.id_anunciante "
					+ "AND pre.id_anunciante = prop.id_anunciante " + "INNER JOIN anunciantes anun "
					+ "ON prop.id_anunciante = anun.id_anunciante "
					+ "WHERE pre.nro_propiedad = ? AND pre.id_anunciante = ? AND fecha_desde = (SELECT max(fecha_desde) FROM precios "
					+ "WHERE nro_propiedad = ? AND id_anunciante = ?)");
			stmt.setInt(1, prop.getNroPropiedad());
			stmt.setInt(2, prop.getAnunciante().getIdAnunciante());
			stmt.setInt(3, prop.getNroPropiedad());
			stmt.setInt(4, prop.getAnunciante().getIdAnunciante());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				p = new Precio();

				p.setPropiedad(new Propiedad());
				p.getPropiedad().setNroPropiedad(rs.getInt("nro_propiedad"));

				p.getPropiedad().setAnunciante(new Anunciante());
				p.getPropiedad().getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
				p.getPropiedad().getAnunciante().setNombre(rs.getString("nombre"));
				p.getPropiedad().getAnunciante().setEmail(rs.getString("email"));
				p.getPropiedad().getAnunciante().setTelefono(rs.getString("telefono"));
				p.getPropiedad().getAnunciante().setUsuario(rs.getString("usuario"));
				p.getPropiedad().getAnunciante().setContrasena(rs.getString("contrasena"));

				p.getPropiedad().setDireccion(rs.getString("direccion"));
				p.getPropiedad().setPiso(rs.getInt("piso"));
				p.getPropiedad().setDepto(rs.getString("depto"));

				p.setFechaDesde(rs.getObject("fecha_desde", LocalDate.class));
				p.setValor(rs.getDouble("valor"));
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
		return p;
	}

	public void add(Precio p) {
		String dateFormat = "yyyy-MM-dd";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
		LocalDate fechaActual = LocalDate.now();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO precios (id_anunciante, nro_propiedad, fecha_desde, valor) VALUES (?, ?, ?, ?)");
			stmt.setInt(1, p.getPropiedad().getAnunciante().getIdAnunciante());
			stmt.setInt(2, p.getPropiedad().getNroPropiedad());
			stmt.setObject(3, fechaActual.format(dtf));
			stmt.setDouble(4, p.getValor());
			stmt.executeUpdate();
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
	}
	
	public void update(Precio pre) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("UPDATE precios SET valor = ? WHERE id_anunciante = ? AND nro_propiedad = ? AND fecha_desde = ?");
			stmt.setDouble(1, pre.getValor());
			stmt.setInt(2, pre.getPropiedad().getAnunciante().getIdAnunciante());
			stmt.setInt(3, pre.getPropiedad().getNroPropiedad());
			stmt.setObject(4, pre.getFechaDesde());
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
	
	public void deleteByPropiedad(Precio pre) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM precios WHERE id_anunciante = ? AND nro_propiedad = ?");
			stmt.setInt(1, pre.getPropiedad().getAnunciante().getIdAnunciante());
			stmt.setInt(2, pre.getPropiedad().getNroPropiedad());
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
	
}