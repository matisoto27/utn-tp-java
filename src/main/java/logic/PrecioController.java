package logic;

import java.time.LocalDate;
import java.util.LinkedList;

import data.PrecioData;
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
	
	public void add(Precio p) {
		pd.add(p);
	}
	
	public void update(Precio p) {
		pd.update(p);
	}
	
	public void delete(Precio p) {
		pd.deleteByPropiedad(p);
	}

}