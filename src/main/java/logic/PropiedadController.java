package logic;

import java.util.LinkedList;

import data.PropiedadData;
import entities.Anunciante;
import entities.Propiedad;

public class PropiedadController {

	private PropiedadData pd;

	public PropiedadController() {
		pd = new PropiedadData();
	}

	public LinkedList<Propiedad> getPropiedadesDisponibles() {
		return pd.getPropiedadesDisponibles();
	}

	public LinkedList<Propiedad> getPropiedadesByAnunciante(Anunciante anun) {
		return pd.getPropiedadesByAnunciante(anun);
	}

	public Propiedad getByNroId(Propiedad prop) {
		return pd.getByNroId(prop);
	}

	public void add(Propiedad prop) {
		pd.add(prop);
	}

}