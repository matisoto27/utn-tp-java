package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;

import entities.Anunciante;
import entities.Propiedad;

public class PropiedadData {
	
	public void add(Propiedad p) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int nro_propiedad = 1;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT COALESCE(MAX(nro_propiedad), 0) + 1 FROM propiedades WHERE id_anunciante = ?");
			stmt.setInt(1, p.getAnunciante().getIdAnunciante());
			rs = stmt.executeQuery();
			if (rs.next()) {
 				nro_propiedad = rs.getInt(1);
 			}
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO propiedades (id_anunciante, nro_propiedad, direccion, piso, depto) VALUES (?, ?, ?, ?, ?)");
			stmt.setInt(1, p.getAnunciante().getIdAnunciante());
			stmt.setInt(2, nro_propiedad);
			stmt.setString(3, p.getDireccion());
			if (p.getPiso() == null && p.getDepto() == null) {
				stmt.setNull(4, Types.INTEGER);
				stmt.setNull(5, Types.VARCHAR);
			} else {
				stmt.setInt(4, p.getPiso());
				stmt.setString(5, p.getDepto());
			}
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
	
	public LinkedList<Propiedad> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Propiedad> propiedades = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					  "SELECT * "
					+ "FROM propiedades prop "
					+ "INNER JOIN anunciantes anun "
					+ "		ON prop.id_anunciante = anun.id_anunciante "
					+ "LEFT JOIN precios pre "
					+ "		ON prop.id_anunciante = pre.id_anunciante "
					+ "		AND prop.nro_propiedad = pre.nro_propiedad "
					+ "		AND pre.fecha_desde = ("
					+ "			SELECT MAX(fecha_desde) "
					+ "			FROM precios "
					+ "			WHERE id_anunciante = prop.id_anunciante AND nro_propiedad = prop.nro_propiedad"
					+ "		) "
					+ "LEFT JOIN alquileres alq "
					+ "		ON prop.id_anunciante = alq.id_anunciante "
					+ "		AND prop.nro_propiedad = alq.nro_propiedad "
					+ "		AND fecha_solicitado = ("
					+ "			SELECT MAX(fecha_solicitado) "
					+ "			FROM alquileres "
					+ "			WHERE id_anunciante = prop.id_anunciante AND nro_propiedad = prop.nro_propiedad"
					+ "		) "
					+ "ORDER BY prop.id_anunciante, prop.nro_propiedad");
			if (rs != null) {
				while (rs.next()) {
					Propiedad p = new Propiedad();

					p.setNroPropiedad(rs.getInt("nro_propiedad"));

					p.setAnunciante(new Anunciante());
					p.getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
					p.getAnunciante().setNombre(rs.getString("nombre"));
					p.getAnunciante().setEmail(rs.getString("email"));
					p.getAnunciante().setTelefono(rs.getString("telefono"));
					p.getAnunciante().setUsuario(rs.getString("usuario"));
					p.getAnunciante().setContrasena(rs.getString("contrasena"));

					p.setDireccion(rs.getString("direccion"));
					p.setPiso(rs.getInt("piso"));
					p.setDepto(rs.getString("depto"));
					p.setPrecioActual(rs.getDouble("valor"));
					p.setEstado(rs.getString("estado"));

					propiedades.add(p);
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
		return propiedades;
	}
	
	public void update(Propiedad p) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE propiedades SET direccion = ?, piso = ?, depto = ? WHERE id_anunciante = ? AND nro_propiedad = ?");
			stmt.setString(1, p.getDireccion());
			if (p.getPiso() == null && p.getDepto() == null) {
				stmt.setNull(2, Types.INTEGER);
				stmt.setNull(3, Types.VARCHAR);
			} else {
				stmt.setInt(2, p.getPiso());
				stmt.setString(3, p.getDepto());
			}
			stmt.setInt(4, p.getAnunciante().getIdAnunciante());
			stmt.setInt(5, p.getNroPropiedad());
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

	public void delete(Propiedad p) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"DELETE FROM propiedades WHERE id_anunciante = ? AND nro_propiedad = ?");
			stmt.setInt(1, p.getAnunciante().getIdAnunciante());
			stmt.setInt(2, p.getNroPropiedad());
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
	
	public Propiedad getByIdAnunNroProp(Propiedad prop) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Propiedad p = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					  "SELECT * "
					+ "FROM propiedades prop "
					+ "INNER JOIN anunciantes anun "
					+ "		ON prop.id_anunciante = anun.id_anunciante "
					+ "LEFT JOIN precios pre "
					+ "		ON prop.id_anunciante = pre.id_anunciante "
					+ "		AND prop.nro_propiedad = pre.nro_propiedad "
					+ "		AND pre.fecha_desde = ("
					+ "			SELECT MAX(fecha_desde) "
					+ "			FROM precios "
					+ "			WHERE id_anunciante = prop.id_anunciante AND nro_propiedad = prop.nro_propiedad"
					+ "		) "
					+ "LEFT JOIN alquileres alq "
					+ "		ON prop.id_anunciante = alq.id_anunciante "
					+ "		AND prop.nro_propiedad = alq.nro_propiedad "
					+ "		AND fecha_solicitado = ("
					+ "			SELECT MAX(fecha_solicitado) "
					+ "			FROM alquileres "
					+ "			WHERE id_anunciante = prop.id_anunciante AND nro_propiedad = prop.nro_propiedad"
					+ "		) "
					+ "WHERE prop.id_anunciante = ? AND prop.nro_propiedad = ?");
			stmt.setInt(1, prop.getAnunciante().getIdAnunciante());
			stmt.setInt(2, prop.getNroPropiedad());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				p = new Propiedad();

				p.setNroPropiedad(rs.getInt("nro_propiedad"));

				p.setAnunciante(new Anunciante());
				p.getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
				p.getAnunciante().setNombre(rs.getString("nombre"));
				p.getAnunciante().setEmail(rs.getString("email"));
				p.getAnunciante().setTelefono(rs.getString("telefono"));
				p.getAnunciante().setUsuario(rs.getString("usuario"));
				p.getAnunciante().setContrasena(rs.getString("contrasena"));

				p.setDireccion(rs.getString("direccion"));
				p.setPiso(rs.getInt("piso"));
				p.setDepto(rs.getString("depto"));
				p.setPrecioActual(rs.getDouble("valor"));
				p.setEstado(rs.getString("estado"));
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
	
	public LinkedList<Propiedad> getPropiedadesDisponibles() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Propiedad> propiedades = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					  "SELECT * "
					+ "FROM propiedades prop "
					+ "INNER JOIN anunciantes anun "
					+ "		ON prop.id_anunciante = anun.id_anunciante "
					+ "LEFT JOIN precios pre "
					+ "		ON prop.id_anunciante = pre.id_anunciante "
					+ "		AND prop.nro_propiedad = pre.nro_propiedad "
					+ "		AND pre.fecha_desde = ("
					+ "			SELECT MAX(fecha_desde) "
					+ "			FROM precios "
					+ "			WHERE id_anunciante = prop.id_anunciante AND nro_propiedad = prop.nro_propiedad"
					+ "		) "
					+ "LEFT JOIN alquileres alq "
					+ "		ON prop.id_anunciante = alq.id_anunciante "
					+ "		AND prop.nro_propiedad = alq.nro_propiedad "
					+ "		AND fecha_solicitado = ("
					+ "			SELECT MAX(fecha_solicitado) "
					+ "			FROM alquileres "
					+ "			WHERE id_anunciante = prop.id_anunciante AND nro_propiedad = prop.nro_propiedad"
					+ "		) "
					+ "WHERE alq.id_alquiler IS NULL OR alq.estado != 'En curso' "
					+ "ORDER BY prop.id_anunciante, prop.nro_propiedad");
			if (rs != null) {
				while (rs.next()) {
					Propiedad p = new Propiedad();
					
					p.setNroPropiedad(rs.getInt("nro_propiedad"));

					p.setAnunciante(new Anunciante());
					p.getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
					p.getAnunciante().setNombre(rs.getString("nombre"));
					p.getAnunciante().setEmail(rs.getString("email"));
					p.getAnunciante().setTelefono(rs.getString("telefono"));
					p.getAnunciante().setUsuario(rs.getString("usuario"));
					p.getAnunciante().setContrasena(rs.getString("contrasena"));

					p.setDireccion(rs.getString("direccion"));
					p.setPiso(rs.getInt("piso"));
					p.setDepto(rs.getString("depto"));
					p.setPrecioActual(rs.getDouble("valor"));
					p.setEstado(rs.getString("estado"));

					propiedades.add(p);
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
		return propiedades;
	}

	public LinkedList<Propiedad> getPropiedadesByAnunciante(Anunciante a) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Propiedad> propiedades = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					  "SELECT * "
					+ "FROM propiedades prop "
					+ "INNER JOIN anunciantes anun "
					+ "		ON prop.id_anunciante = anun.id_anunciante "
					+ "LEFT JOIN precios pre "
					+ "		ON prop.id_anunciante = pre.id_anunciante "
					+ "		AND prop.nro_propiedad = pre.nro_propiedad "
					+ "		AND pre.fecha_desde = ("
					+ "			SELECT MAX(fecha_desde) "
					+ "			FROM precios "
					+ "			WHERE id_anunciante = prop.id_anunciante AND nro_propiedad = prop.nro_propiedad"
					+ "		) "
					+ "LEFT JOIN alquileres alq "
					+ "		ON prop.id_anunciante = alq.id_anunciante "
					+ "		AND prop.nro_propiedad = alq.nro_propiedad "
					+ "		AND fecha_solicitado = ("
					+ "			SELECT MAX(fecha_solicitado) "
					+ "			FROM alquileres "
					+ "			WHERE id_anunciante = prop.id_anunciante AND nro_propiedad = prop.nro_propiedad"
					+ "		) "
					+ "WHERE prop.id_anunciante = ? "
					+ "ORDER BY prop.id_anunciante, prop.nro_propiedad");
			stmt.setInt(1, a.getIdAnunciante());
			rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Propiedad p = new Propiedad();

					p.setNroPropiedad(rs.getInt("nro_propiedad"));

					p.setAnunciante(new Anunciante());
					p.getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
					p.getAnunciante().setNombre(rs.getString("nombre"));
					p.getAnunciante().setEmail(rs.getString("email"));
					p.getAnunciante().setTelefono(rs.getString("telefono"));
					p.getAnunciante().setUsuario(rs.getString("usuario"));
					p.getAnunciante().setContrasena(rs.getString("contrasena"));

					p.setDireccion(rs.getString("direccion"));
					p.setPiso(rs.getInt("piso"));
					p.setDepto(rs.getString("depto"));
					p.setPrecioActual(rs.getDouble("valor"));
					p.setEstado(rs.getString("estado"));

					propiedades.add(p);
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
		return propiedades;
	}

}