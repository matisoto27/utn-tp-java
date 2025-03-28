package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Administrador;

public class AdministradorData {

	public Administrador getByUsuarioContrasena(Administrador adm) {
		Administrador a = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn()
					.prepareStatement("SELECT * FROM administradores WHERE usuario = ? AND contrasena = ?");
			stmt.setString(1, adm.getUsuario());
			stmt.setString(2, adm.getContrasena());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				a = new Administrador();
				a.setUsuario(rs.getString("usuario"));
				a.setContrasena(rs.getString("contrasena"));
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

}