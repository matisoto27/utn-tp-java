package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Anunciante;
import entities.Propiedad;

public class PropiedadData {
	
	public LinkedList<Propiedad> getPropiedadesDisponibles(){
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Propiedad> propiedades = new LinkedList<>();
		try {
			stmt= DbConnector.getInstancia().getConn().createStatement();
			String sql =
			"SELECT * FROM propiedades p " +
            "INNER JOIN anunciantes a ON p.id_anunciante = a.id_anunciante " +
            "WHERE NOT EXISTS (SELECT 1 FROM alquileres alq " +
            "WHERE alq.id_anunciante = p.id_anunciante " +
            "AND alq.nro_propiedad = p.nro_propiedad " +
            "AND CURRENT_DATE BETWEEN alq.fecha_inicio_contrato AND alq.fecha_fin_contrato)";
			rs = stmt.executeQuery(sql);
			if(rs != null) {
				while(rs.next()) {
					Anunciante anun = new Anunciante();
					Propiedad prop = new Propiedad();
					
					anun.setIdAnunciante(rs.getInt("id_anunciante"));
					anun.setNombre(rs.getString("nombre"));
					anun.setEmail(rs.getString("email"));
					anun.setTelefono(rs.getString("telefono"));
					anun.setUsuario(rs.getString("usuario"));
					anun.setContrasena(rs.getString("contrasena"));
					
					prop.setNroPropiedad(rs.getInt("nro_propiedad"));
					prop.setAnunciante(anun);
					prop.setDireccion(rs.getString("direccion"));
					prop.setPiso(rs.getInt("piso"));
					prop.setDepto(rs.getString("depto"));
					
					propiedades.add(prop);
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
		return propiedades;
	}
	
}