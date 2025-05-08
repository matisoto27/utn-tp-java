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
		return ad.getUltimoAlquilerByPropiedad(alq);
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
	
	public Alquiler getUltimoAlquilerCliente(Alquiler alq) {
		return ad.getUltimoAlquilerByCliente(alq);
	}
	
	public LinkedList<Alquiler> getAlquileresPendientesEnCursoByAnunciante(Anunciante anun) {
		return ad.getAlquileresByAnuncianteYEstados(anun, "Pendiente", "En curso");
	}
	
	public LinkedList<Alquiler> getAlquileresPendientesByAnunciante(Anunciante anun) {
		return ad.getAlquileresByAnuncianteYEstados(anun, "Pendiente");
	}
	
	public LinkedList<Alquiler> getAlquileresCanceladosByAnunciante(Anunciante anun) {
		return ad.getAlquileresByAnuncianteYEstados(anun, "Cancelado");
	}
	
	public LinkedList<Alquiler> getAlquileresFinalizadosByAnunciante(Anunciante anun) {
		return ad.getAlquileresByAnuncianteYEstados(anun, "Finalizado");
	}
	
}