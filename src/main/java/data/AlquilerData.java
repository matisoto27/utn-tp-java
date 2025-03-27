package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import entities.Alquiler;
import entities.Anunciante;
import entities.Cliente;
import entities.Propiedad;

public class AlquilerData {

	public LinkedList<Alquiler> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Alquiler> alquileres = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT * FROM alquileres a " + "INNER JOIN clientes c ON a.dni_cliente = c.dni "
					+ "INNER JOIN propiedades p ON a.id_anunciante = p.id_anunciante AND a.nro_propiedad = p.nro_propiedad "
					+ "INNER JOIN anunciantes an ON p.id_anunciante = an.id_anunciante");
			if (rs != null) {
				while (rs.next()) {
					Alquiler alq = new Alquiler();

					alq.setIdAlquiler(rs.getInt("id_alquiler"));

					alq.setCliente(new Cliente());
					alq.getCliente().setDni(rs.getString("dni"));
					alq.getCliente().setNombre(rs.getString("nombre"));
					alq.getCliente().setApellido(rs.getString("apellido"));
					alq.getCliente().setFechaNac(rs.getObject("fecha_nac", LocalDate.class));
					alq.getCliente().setEmail(rs.getString("email"));
					alq.getCliente().setTelefono(rs.getString("telefono"));
					alq.getCliente().setContrasena(rs.getString("contrasena"));

					alq.setPropiedad(new Propiedad());
					alq.getPropiedad().setNroPropiedad(rs.getInt("nro_propiedad"));
					alq.getPropiedad().setAnunciante(new Anunciante());
					alq.getPropiedad().getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
					alq.getPropiedad().getAnunciante().setNombre(rs.getString("nombre"));
					alq.getPropiedad().getAnunciante().setEmail(rs.getString("email"));
					alq.getPropiedad().getAnunciante().setTelefono(rs.getString("telefono"));
					alq.getPropiedad().getAnunciante().setUsuario(rs.getString("usuario"));
					alq.getPropiedad().getAnunciante().setContrasena(rs.getString("contrasena"));
					alq.getPropiedad().setDireccion(rs.getString("direccion"));
					alq.getPropiedad().setPiso(rs.getInt("piso"));
					alq.getPropiedad().setDepto(rs.getString("depto"));

					alq.setFechaSolicitado(rs.getObject("fecha_solicitado", LocalDate.class));
					alq.setEstado(rs.getString("estado"));
					alq.setFechaInicioContrato(rs.getObject("fecha_inicio_contrato", LocalDate.class));
					alq.setFechaFinContrato(rs.getObject("fecha_fin_contrato", LocalDate.class));
					alq.setFechaRenuncia(rs.getObject("fecha_renuncia", LocalDate.class));
					alq.setPuntuacion(rs.getInt("puntuacion"));
					alq.setComentario(rs.getString("comentario"));

					alquileres.add(alq);
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
		return alquileres;
	}

	public Alquiler getById(Alquiler alq) {
		Alquiler a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT * FROM alquileres a "
					+ "INNER JOIN clientes c ON a.dni_cliente = c.dni "
					+ "INNER JOIN propiedades p ON a.id_anunciante = p.id_anunciante AND a.nro_propiedad = p.nro_propiedad "
					+ "INNER JOIN anunciantes an ON p.id_anunciante = an.id_anunciante " + "WHERE id_alquiler = ?");
			stmt.setInt(1, alq.getIdAlquiler());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				a = new Alquiler();

				a.setIdAlquiler(rs.getInt("id_alquiler"));

				a.setCliente(new Cliente());
				a.getCliente().setDni(rs.getString("dni"));
				a.getCliente().setNombre(rs.getString("nombre"));
				a.getCliente().setApellido(rs.getString("apellido"));
				a.getCliente().setFechaNac(rs.getObject("fecha_nac", LocalDate.class));
				a.getCliente().setEmail(rs.getString("email"));
				a.getCliente().setTelefono(rs.getString("telefono"));
				a.getCliente().setContrasena(rs.getString("contrasena"));

				a.setPropiedad(new Propiedad());
				a.getPropiedad().setNroPropiedad(rs.getInt("nro_propiedad"));
				a.getPropiedad().setAnunciante(new Anunciante());
				a.getPropiedad().getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
				a.getPropiedad().getAnunciante().setNombre(rs.getString("nombre"));
				a.getPropiedad().getAnunciante().setEmail(rs.getString("email"));
				a.getPropiedad().getAnunciante().setTelefono(rs.getString("telefono"));
				a.getPropiedad().getAnunciante().setUsuario(rs.getString("usuario"));
				a.getPropiedad().getAnunciante().setContrasena(rs.getString("contrasena"));
				a.getPropiedad().setDireccion(rs.getString("direccion"));
				a.getPropiedad().setPiso(rs.getInt("piso"));
				a.getPropiedad().setDepto(rs.getString("depto"));

				a.setFechaSolicitado(rs.getObject("fecha_solicitado", LocalDate.class));
				a.setEstado(rs.getString("estado"));
				a.setFechaInicioContrato(rs.getObject("fecha_inicio_contrato", LocalDate.class));
				a.setFechaFinContrato(rs.getObject("fecha_fin_contrato", LocalDate.class));
				a.setFechaRenuncia(rs.getObject("fecha_renuncia", LocalDate.class));
				a.setPuntuacion(rs.getInt("puntuacion"));
				a.setComentario(rs.getString("comentario"));
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
		return a;
	}

	public Alquiler getUltimoByPropiedad(Alquiler alq) {
		Alquiler a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn()
					.prepareStatement("SELECT * FROM alquileres alq INNER JOIN clientes cli "
							+ "ON alq.dni_cliente = cli.dni INNER JOIN propiedades prop "
							+ "ON alq.nro_propiedad = prop.nro_propiedad AND alq.id_anunciante = prop.id_anunciante "
							+ "INNER JOIN anunciantes anun ON prop.id_anunciante = anun.id_anunciante "
							+ "WHERE alq.nro_propiedad = ? AND alq.id_anunciante = ? "
							+ "AND alq.fecha_solicitado = (SELECT MAX(fecha_solicitado) FROM alquileres "
							+ "WHERE nro_propiedad = ? AND id_anunciante = ?)");
			stmt.setInt(1, alq.getPropiedad().getNroPropiedad());
			stmt.setInt(2, alq.getPropiedad().getAnunciante().getIdAnunciante());
			stmt.setInt(3, alq.getPropiedad().getNroPropiedad());
			stmt.setInt(4, alq.getPropiedad().getAnunciante().getIdAnunciante());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				a = new Alquiler();

				a.setIdAlquiler(rs.getInt("id_alquiler"));

				a.setCliente(new Cliente());
				a.getCliente().setDni(rs.getString("dni"));
				a.getCliente().setNombre(rs.getString("nombre"));
				a.getCliente().setApellido(rs.getString("apellido"));
				a.getCliente().setFechaNac(rs.getObject("fecha_nac", LocalDate.class));
				a.getCliente().setEmail(rs.getString("email"));
				a.getCliente().setTelefono(rs.getString("telefono"));
				a.getCliente().setContrasena(rs.getString("contrasena"));

				a.setPropiedad(new Propiedad());
				a.getPropiedad().setNroPropiedad(rs.getInt("nro_propiedad"));
				a.getPropiedad().setAnunciante(new Anunciante());
				a.getPropiedad().getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
				a.getPropiedad().getAnunciante().setNombre(rs.getString("nombre"));
				a.getPropiedad().getAnunciante().setEmail(rs.getString("email"));
				a.getPropiedad().getAnunciante().setTelefono(rs.getString("telefono"));
				a.getPropiedad().getAnunciante().setUsuario(rs.getString("usuario"));
				a.getPropiedad().getAnunciante().setContrasena(rs.getString("contrasena"));
				a.getPropiedad().setDireccion(rs.getString("direccion"));
				a.getPropiedad().setPiso(rs.getInt("piso"));
				a.getPropiedad().setDepto(rs.getString("depto"));

				a.setFechaSolicitado(rs.getObject("fecha_solicitado", LocalDate.class));
				a.setEstado(rs.getString("estado"));
				a.setFechaInicioContrato(rs.getObject("fecha_inicio_contrato", LocalDate.class));
				a.setFechaFinContrato(rs.getObject("fecha_fin_contrato", LocalDate.class));
				a.setFechaRenuncia(rs.getObject("fecha_renuncia", LocalDate.class));
				a.setPuntuacion(rs.getInt("puntuacion"));
				a.setComentario(rs.getString("comentario"));
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
		return a;
	}

	public void add(Alquiler alq) {
		String dateFormat = "yyyy-MM-dd";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
		LocalDate fechaActual = LocalDate.now();
		String estado = "Pendiente";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO alquileres (dni_cliente, id_anunciante, nro_propiedad, fecha_solicitado, estado) VALUES (?, ?, ?, ?, ?)");
			stmt.setString(1, alq.getCliente().getDni());
			stmt.setInt(2, alq.getPropiedad().getAnunciante().getIdAnunciante());
			stmt.setInt(3, alq.getPropiedad().getNroPropiedad());
			stmt.setObject(4, fechaActual.format(dtf));
			stmt.setString(5, estado);
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

	public void update(Alquiler alq) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn()
					.prepareStatement("UPDATE alquileres SET estado = ? WHERE id_alquiler = ?");
			stmt.setString(1, alq.getEstado());
			stmt.setInt(2, alq.getIdAlquiler());
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

	public void delete(Alquiler alq) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn()
					.prepareStatement("DELETE FROM alquileres WHERE id_alquiler = ?");
			stmt.setInt(1, alq.getIdAlquiler());
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