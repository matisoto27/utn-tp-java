package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import entities.Alquiler;
import entities.Anunciante;
import entities.Cliente;
import entities.Propiedad;

public class AlquilerData {

	public Alquiler getById(Alquiler alq) {
		Alquiler a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					  "SELECT * "
					+ "FROM alquileres alq"
					+ "INNER JOIN clientes cli "
					+ "		ON alq.dni_cliente = cli.dni "
					+ "INNER JOIN propiedades prop "
					+ "		ON alq.id_anunciante = prop.id_anunciante "
					+ "		AND alq.nro_propiedad = prop.nro_propiedad "
					+ "INNER JOIN anunciantes anun "
					+ "		ON prop.id_anunciante = anun.id_anunciante "
					+ "INNER JOIN precios pre "
					+ "		ON prop.id_anunciante = pre.id_anunciante "
					+ "		AND prop.nro_propiedad = pre.nro_propiedad "
					+ "		AND pre.fecha_desde = ("
					+ "			SELECT MAX(fecha_desde) "
					+ "			FROM precios "
					+ "			WHERE id_anunciante = prop.id_anunciante "
					+ "			AND nro_propiedad = prop.nro_propiedad "
					+ "			AND fecha_desde <= alq.fecha_solicitado"
					+ "		) "
					+ "WHERE alq.id_alquiler = ?");
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
				a.setPrecio(rs.getDouble("valor"));
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
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					  "SELECT * "
					+ "FROM alquileres alq"
					+ "INNER JOIN clientes cli "
					+ "		ON alq.dni_cliente = cli.dni "
					+ "INNER JOIN propiedades prop "
					+ "		ON alq.id_anunciante = prop.id_anunciante "
					+ "		AND alq.nro_propiedad = prop.nro_propiedad "
					+ "INNER JOIN anunciantes anun "
					+ "		ON prop.id_anunciante = anun.id_anunciante "
					+ "INNER JOIN precios pre "
					+ "		ON prop.id_anunciante = pre.id_anunciante "
					+ "		AND prop.nro_propiedad = pre.nro_propiedad "
					+ "		AND pre.fecha_desde = ("
					+ "			SELECT MAX(fecha_desde) "
					+ "			FROM precios "
					+ "			WHERE id_anunciante = prop.id_anunciante "
					+ "			AND nro_propiedad = prop.nro_propiedad "
					+ "			AND fecha_desde <= alq.fecha_solicitado"
					+ "		)"
					+ "WHERE alq.id_anunciante = ? "
					+ "AND alq.nro_propiedad = ?");
			stmt.setInt(1, alq.getPropiedad().getAnunciante().getIdAnunciante());
			stmt.setInt(2, alq.getPropiedad().getNroPropiedad());
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
				a.setPrecio(rs.getDouble("valor"));
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
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE alquileres SET estado = ?, fecha_inicio_contrato = ?, fecha_fin_contrato = ? WHERE id_alquiler = ?");
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
	
	public LinkedList<Alquiler> getAlquileresPendientesEnCursoByAnunciante(Anunciante anun) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Alquiler> alquileres = new LinkedList<>();
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					  "SELECT * "
					+ "FROM alquileres alq "
					+ "INNER JOIN clientes cli "
					+ "		ON alq.dni_cliente = cli.dni "
					+ "INNER JOIN propiedades prop "
					+ "		ON alq.id_anunciante = prop.id_anunciante "
					+ "		AND alq.nro_propiedad = prop.nro_propiedad "
					+ "INNER JOIN anunciantes anun "
					+ "		ON prop.id_anunciante = anun.id_anunciante "
					+ "LEFT JOIN precios pre "
					+ "		ON prop.id_anunciante = pre.id_anunciante "
					+ "		AND prop.nro_propiedad = pre.nro_propiedad "
					+ "		AND pre.fecha_desde = ("
					+ "			SELECT MAX(fecha_desde) "
					+ "			FROM precios "
					+ "			WHERE id_anunciante = prop.id_anunciante "
					+ "			AND nro_propiedad = prop.nro_propiedad "
					+ "			AND fecha_desde <= alq.fecha_inicio_contrato"
					+ "			GROUP BY id_anunciante, nro_propiedad"
					+ "		) "
					+ "WHERE alq.id_anunciante = ? "
					+ "		AND ("
					+ "			alq.estado = 'Pendiente' "
					+ "			OR alq.estado = 'En curso'"
					+ "		)");
			stmt.setInt(1, anun.getIdAnunciante());
			rs = stmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Alquiler a = new Alquiler();

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
					a.setPrecio(rs.getDouble("valor"));
					a.setFechaRenuncia(rs.getObject("fecha_renuncia", LocalDate.class));
					a.setPuntuacion(rs.getInt("puntuacion"));
					a.setComentario(rs.getString("comentario"));
					
					alquileres.add(a);
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

}