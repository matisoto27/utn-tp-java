package logic;

import java.util.LinkedList;

import data.PropiedadData;
import entities.Propiedad;

public class PropiedadController {

	private PropiedadData pd;
	
	public PropiedadController() {
		pd = new PropiedadData();
	}
	
	public LinkedList<Propiedad> getPropiedadesDisponibles() {
		return pd.getPropiedadesDisponibles();
	}
	
}