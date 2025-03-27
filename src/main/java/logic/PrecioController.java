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

	public double getUltimoByPropiedad(Propiedad prop) {
		return pd.getUltimoByPropiedad(prop);
	}

}