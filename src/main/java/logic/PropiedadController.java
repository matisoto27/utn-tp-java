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

	public Propiedad getByIdAnunNroProp(Propiedad prop) {
		return pd.getByIdAnunNroProp(prop);
	}

	public void add(Propiedad prop) {
		pd.add(prop);
	}

	public void update(Propiedad prop) {
		pd.update(prop);
	}

	public void delete(Propiedad prop) {
		pd.delete(prop);
	}

}