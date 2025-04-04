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

	public void add(Propiedad prop) {
		pd.add(prop);
	}

	public LinkedList<Propiedad> getAll() {
		return pd.getAll();
	}
	
	public void update(Propiedad prop) {
		pd.update(prop);
	}

	public void delete(Propiedad prop) {
		pd.delete(prop);
	}
	
	public Propiedad getByIdAnunNroProp(Propiedad prop) {
		return pd.getByIdAnunNroProp(prop);
	}
	
	public LinkedList<Propiedad> getPropiedadesDisponibles() {
		return pd.getPropiedadesDisponibles();
	}

	public LinkedList<Propiedad> getPropiedadesByAnunciante(Anunciante anun) {
		return pd.getPropiedadesByAnunciante(anun);
	}
	
}