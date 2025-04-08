package logic;

import java.util.LinkedList;

import data.AlquilerData;
import entities.Alquiler;
import entities.Anunciante;

public class AlquilerController {

	private AlquilerData ad;

	public AlquilerController() {
		ad = new AlquilerData();
	}

	public Alquiler getUltimoByPropiedad(Alquiler alq) {
		return ad.getUltimoByPropiedad(alq);
	}

	public void add(Alquiler alq) {
		ad.add(alq);
	}

	public void update(Alquiler alq) {
		ad.update(alq);
	}

	public void delete(Alquiler alq) {
		ad.delete(alq);
	}
	
	public LinkedList<Alquiler> getAlquileresEnCursoByAnunciante(Anunciante anun) {
		return ad.getAlquileresEnCursoByAnunciante(anun);
	}

}