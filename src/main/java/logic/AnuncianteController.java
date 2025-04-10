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

	public boolean validarNombreUnico(String nombre) {
		String query = "SELECT * FROM anunciantes WHERE nombre = ?";
		return ad.getByQuery(query, nombre) == null;
	}

	public boolean validarUsuarioUnico(String usuario) {
		String query = "SELECT * FROM anunciantes WHERE usuario = ?";
		return ad.getByQuery(query, usuario) == null;
	}

	public Anunciante validarCredenciales(Anunciante anun) {
		String query = "SELECT * FROM anunciantes WHERE usuario = ? AND contrasena = ?";
		return ad.getByQuery(query, anun.getUsuario(), anun.getContrasena());
	}

	public void add(Anunciante anun) {
		ad.add(anun);
	}

	public void update(Anunciante anun) {
		ad.update(anun);
	}

	public void delete(Anunciante anun) {
		ad.delete(anun);
	}

}