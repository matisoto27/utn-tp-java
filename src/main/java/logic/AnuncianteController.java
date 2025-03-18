package logic;

import data.AnuncianteData;
import entities.Anunciante;

public class AnuncianteController {
	
	private AnuncianteData ad;
	
	public AnuncianteController() {
		ad = new AnuncianteData();
	}
	
	public boolean validarNombreUnico(Anunciante anun) {
		return ad.getByNombre(anun) == null;
	}
	
	public boolean validarUsuarioUnico(Anunciante anun) {
		return ad.getByUsuario(anun) == null;
	}
	
	public Anunciante validarCredenciales(Anunciante anun) {
		return ad.getByUsuarioContrasena(anun);
	}
	
	public void add(Anunciante anun) {
		ad.add(anun);
	}
	
}