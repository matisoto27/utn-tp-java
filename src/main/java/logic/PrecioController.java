package logic;

import java.time.LocalDate;
import java.util.LinkedList;

import data.PrecioData;
import entities.Anunciante;
import entities.Precio;
import entities.Propiedad;

public class PrecioController {

	private PrecioData pd;

	public PrecioController() {
		pd = new PrecioData();
	}

	public LinkedList<Precio> getAll() {
		return pd.getAll();
	}
	
	public LinkedList<Precio> getAllByAnunciante(Anunciante anun) {
		return pd.getAllByAnunciante(anun);
	}
	
	public Precio getByPropiedadFechaDesde(Precio pre) {
		return pd.getByPropiedadFechaDesde(pre);
	}
	
	public Precio getUltimoByPropiedad(Propiedad prop) {
		return pd.getUltimoByPropiedad(prop);
	}
	
	public boolean precioAsignadoHoy(Propiedad prop) {
		Precio pre = pd.getUltimoByPropiedad(prop);
		if (pre != null) {
			return pre.getFechaDesde().equals(LocalDate.now()) ? true : false;
		} else {
			return false;
		}
	}
	
	public void add(Precio pre) {
		pd.add(pre);
	}
	
	public void update(Precio pre) {
		pd.update(pre);
	}
	
	public void delete(Precio pre) {
		pd.deleteByPropiedad(pre);
	}
	
}