package logic;

import java.util.LinkedList;

import data.AnuncianteData;
import entities.Anunciante;

public class AnuncianteController {

	private AnuncianteData ad;

	public AnuncianteController() {
		ad = new AnuncianteData();
	}

	public LinkedList<Anunciante> getAll() {
		return ad.getAll();
	}

	public Anunciante getById(Anunciante anun) {
		String query = "SELECT * FROM anunciantes WHERE id_anunciante = ?";
		return ad.getByQuery(query, anun.getIdAnunciante());
	}

	public boolean validarNombreUnico(Anunciante anun) {
		String query = "SELECT * FROM anunciantes WHERE nombre = ?";
		return ad.getByQuery(query, anun.getNombre()) == null;
	}

	public boolean validarUsuarioUnico(Anunciante anun) {
		String query = "SELECT * FROM anunciantes WHERE usuario = ?";
		return ad.getByQuery(query, anun.getUsuario()) == null;
	}

	public Anunciante validarCredenciales(Anunciante anun) {
		String query = "SELECT * FROM anunciantes WHERE usuario = ? AND contrasena = ?";
		return ad.getByQuery(query, anun.getUsuario(), anun.getContrasena());
	}

	public void add(Anunciante anun) {
		ad.add(anun);
	}

}