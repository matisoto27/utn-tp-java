package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Anunciante;
import entities.Propiedad;

public class PropiedadData {

	public LinkedList<Propiedad> getPropiedadesDisponibles() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Propiedad> propiedades = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM propiedades p "
					+ "INNER JOIN anunciantes a ON p.id_anunciante = a.id_anunciante "
					+ "LEFT JOIN alquileres alq ON alq.id_anunciante = p.id_anunciante AND alq.nro_propiedad = p.nro_propiedad "
					+ "WHERE (alq.id_alquiler IS NULL) OR estado != 'Confirmado'");
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

	public LinkedList<Propiedad> getPropiedadesByAnunciante(Anunciante anun) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Propiedad> propiedades = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn()
					.prepareStatement("SELECT * FROM propiedades prop " + "INNER JOIN anunciantes anun "
							+ "ON prop.id_anunciante = anun.id_anunciante " + "WHERE prop.id_anunciante = ?");
			stmt.setInt(1, anun.getIdAnunciante());
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

	public Propiedad getByNroId(Propiedad prop) {
		Propiedad p = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn()
					.prepareStatement("SELECT * FROM propiedades p " + "INNER JOIN anunciantes a "
							+ "ON p.id_anunciante = a.id_anunciante "
							+ "WHERE nro_propiedad = ? AND id_anunciante = ?");
			stmt.setInt(1, prop.getNroPropiedad());
			stmt.setInt(2, prop.getAnunciante().getIdAnunciante());
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
			stmt.close();
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO propiedades (id_anunciante, nro_propiedad, direccion, piso, depto) VALUES (?, ?, ?, ?, ?)");
			stmt.setInt(1, p.getAnunciante().getIdAnunciante());
			stmt.setInt(2, nro_propiedad);
			stmt.setString(3, p.getDireccion());
			stmt.setInt(4, p.getPiso());
			stmt.setString(5, p.getDepto());
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

}