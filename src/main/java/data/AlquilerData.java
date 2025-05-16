package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
			rs = stmt.executeQuery(
					  "SELECT * "
					+ "FROM alquileres alq "
					+ "INNER JOIN clientes cli "
					+ "		ON alq.dni_cliente = cli.dni "
					+ "INNER JOIN propiedades prop "
					+ "		ON alq.id_anunciante = prop.id_anunciante "
					+ "     AND alq.nro_propiedad = prop.nro_propiedad "
					+ "INNER JOIN anunciantes anun "
					+ "		ON prop.id_anunciante = anun.id_anunciante"
			);
			if (rs != null) {
				while (rs.next()) {
					Alquiler a = new Alquiler();

					a.setIdAlquiler(rs.getInt("id_alquiler"));

					a.setCliente(new Cliente());
					a.getCliente().setDni(rs.getString("dni"));
					a.getCliente().setNombre(rs.getString("cli.nombre"));
					a.getCliente().setApellido(rs.getString("apellido"));
					a.getCliente().setFechaNac(rs.getObject("fecha_nac", LocalDate.class));
					a.getCliente().setEmail(rs.getString("email"));
					a.getCliente().setTelefono(rs.getString("telefono"));
					a.getCliente().setContrasena(rs.getString("contrasena"));

					a.setPropiedad(new Propiedad());
					a.getPropiedad().setNroPropiedad(rs.getInt("nro_propiedad"));
					a.getPropiedad().setAnunciante(new Anunciante());
					a.getPropiedad().getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
					a.getPropiedad().getAnunciante().setNombre(rs.getString("anun.nombre"));
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
	
	public Alquiler getById(Alquiler alq) {
		Alquiler a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
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
	
	public Alquiler getUltimoAlquilerByCliente(Alquiler alq) {
		Alquiler a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
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
					+ "			AND fecha_desde <= alq.fecha_solicitado"
					+ "		) "
					+ "WHERE alq.dni_cliente = ?");
			stmt.setString(1, alq.getCliente().getDni());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				a = new Alquiler();

				a.setIdAlquiler(rs.getInt("id_alquiler"));

				a.setCliente(new Cliente());
				a.getCliente().setDni(rs.getString("dni"));
				a.getCliente().setNombre(rs.getString("cli.nombre"));
				a.getCliente().setApellido(rs.getString("apellido"));
				a.getCliente().setFechaNac(rs.getObject("fecha_nac", LocalDate.class));
				a.getCliente().setEmail(rs.getString("cli.email"));
				a.getCliente().setTelefono(rs.getString("cli.telefono"));
				a.getCliente().setContrasena(rs.getString("contrasena"));

				a.setPropiedad(new Propiedad());
				a.getPropiedad().setNroPropiedad(rs.getInt("nro_propiedad"));
				a.getPropiedad().setAnunciante(new Anunciante());
				a.getPropiedad().getAnunciante().setIdAnunciante(rs.getInt("id_anunciante"));
				a.getPropiedad().getAnunciante().setNombre(rs.getString("anun.nombre"));
				a.getPropiedad().getAnunciante().setEmail(rs.getString("anun.email"));
				a.getPropiedad().getAnunciante().setTelefono(rs.getString("anun.telefono"));
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
	
	public Alquiler getUltimoAlquilerByPropiedad(Alquiler alq) {
		Alquiler a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
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
					+ "			AND fecha_desde <= alq.fecha_solicitado"
					+ "		) "
					+ "WHERE alq.id_anunciante = ? "
					+ "		AND alq.nro_propiedad = ?");
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
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			if (alq.getFechaSolicitado() == null && alq.getEstado() == null) {
				stmt = DbConnector.getInstancia().getConn().prepareStatement(
						"INSERT INTO alquileres (dni_cliente, id_anunciante, nro_propiedad, fecha_solicitado, estado) VALUES (?, ?, ?, ?, ?)");
			} else {
				stmt = DbConnector.getInstancia().getConn().prepareStatement(
						"INSERT INTO alquileres (dni_cliente, id_anunciante, nro_propiedad, fecha_solicitado, estado, fecha_inicio_contrato, fecha_fin_contrato, fecha_renuncia, puntuacion, comentario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			}
			stmt.setString(1, alq.getCliente().getDni());
			stmt.setInt(2, alq.getPropiedad().getAnunciante().getIdAnunciante());
			stmt.setInt(3, alq.getPropiedad().getNroPropiedad());
			if (alq.getFechaSolicitado() == null && alq.getEstado() == null) {
				stmt.setObject(4, LocalDate.now().format(dtf));
				stmt.setString(5, "Pendiente");
			} else {
				
				if (alq.getFechaSolicitado() != null) {
					stmt.setObject(4, alq.getFechaSolicitado().format(dtf));
				} else {
					stmt.setNull(4, Types.DATE);
				}
				
				if (alq.getEstado() != null) {
					stmt.setString(5, alq.getEstado());
				} else {
					stmt.setNull(5, Types.VARCHAR);
				}
				
				if (alq.getFechaInicioContrato() != null) {
					stmt.setObject(6, alq.getFechaInicioContrato().format(dtf));
				} else {
					stmt.setNull(6, Types.DATE);
				}
				
				if (alq.getFechaFinContrato() != null) {
					stmt.setObject(7, alq.getFechaFinContrato().format(dtf));
				} else {
					stmt.setNull(7, Types.DATE);
				}
				
				if (alq.getFechaRenuncia() != null) {
					stmt.setObject(8, alq.getFechaRenuncia().format(dtf));
				} else {
					stmt.setNull(8, Types.DATE);
				}
				
				if (alq.getPuntuacion() != null) {
					stmt.setInt(9, alq.getPuntuacion());
				} else {
					stmt.setNull(9, Types.INTEGER);
				}
				
				if (alq.getComentario() != null) {
					stmt.setString(10, alq.getComentario());
				} else {
					stmt.setNull(10, Types.VARCHAR);
				}
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

	public void update(Alquiler alq) {
		PreparedStatement stmt = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE alquileres SET estado = ?, fecha_inicio_contrato = ?, fecha_fin_contrato = ?, fecha_renuncia = ?, puntuacion = ?, comentario = ? WHERE id_alquiler = ?");
			stmt.setString(1, alq.getEstado());
			stmt.setObject(2, alq.getFechaInicioContrato());
			stmt.setObject(3, alq.getFechaFinContrato());
			stmt.setObject(4, alq.getFechaRenuncia());
			if (alq.getPuntuacion() == null && alq.getComentario() == null) {
				stmt.setNull(5, Types.INTEGER);
				stmt.setNull(6, Types.VARCHAR);
			} else if (alq.getComentario() == null) {
				stmt.setInt(5, alq.getPuntuacion());
				stmt.setNull(6, Types.VARCHAR);
			} else {
				stmt.setInt(5, alq.getPuntuacion());
				stmt.setString(6, alq.getComentario());
			}
			stmt.setInt(7, alq.getIdAlquiler());
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
			if (alq.getIdAlquiler() > 0) {
				stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM alquileres WHERE id_alquiler = ?");
				stmt.setInt(1, alq.getIdAlquiler());
			} else {
				stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM alquileres WHERE dni_cliente = ? AND id_anunciante = ? AND nro_propiedad = ? AND estado = 'Pendiente'");
				stmt.setString(1, alq.getCliente().getDni());
				stmt.setInt(2, alq.getPropiedad().getAnunciante().getIdAnunciante());
				stmt.setInt(3, alq.getPropiedad().getNroPropiedad());
			}
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
	
	public LinkedList<Alquiler> getAlquileresByAnuncianteYEstados(Anunciante anun, String... estados) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Alquiler> alquileres = new LinkedList<>();
		StringBuilder query = new StringBuilder(
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
		        + "			AND fecha_desde <= alq.fecha_inicio_contrato "
		        + "		) "
		        + "WHERE alq.id_anunciante = ? "
		        + "		AND alq.estado IN (");
		for (int i = 0; i < estados.length; i++) {
	        query.append("'").append(estados[i]).append("'");
	        if (i < estados.length - 1) {
	            query.append(", ");
	        }
	    }
		query.append(")");
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(query.toString());
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
	
	public LinkedList<Alquiler> getAlquileresByPropiedad(Propiedad prop) {
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
					+ "			AND fecha_desde <= alq.fecha_inicio_contrato "
					+ "		) "
					+ "WHERE alq.id_anunciante = ? AND alq.nro_propiedad = ?");
			stmt.setInt(1, prop.getAnunciante().getIdAnunciante());
			stmt.setInt(2, prop.getNroPropiedad());
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