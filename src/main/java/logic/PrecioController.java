package logic;

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

	public void add(Precio p) {
		pd.add(p);
	}
	
	public void deleteByPropiedad(Propiedad prop) {
		pd.deleteByPropiedad(prop);
	}

}