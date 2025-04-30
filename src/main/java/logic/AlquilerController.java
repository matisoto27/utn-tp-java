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
	
	public LinkedList<Alquiler> getAll() {
		return ad.getAll();
	}

	public void update(Alquiler alq) {
		ad.update(alq);
	}

	public void delete(Alquiler alq) {
		ad.delete(alq);
	}
	
	public Alquiler getById(Alquiler alq) {
		return ad.getById(alq);
	}
	
	public LinkedList<Alquiler> getAlquileresPendientesEnCursoByAnunciante(Anunciante anun) {
		return ad.getAlquileresPendientesEnCursoByAnunciante(anun);
	}

}