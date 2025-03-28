package logic;

import data.AdministradorData;
import entities.Administrador;

public class AdministradorController {

	private AdministradorData ad;

	public AdministradorController() {
		ad = new AdministradorData();
	}

	public Administrador getByUsuarioContrasena(Administrador adm) {
		return ad.getByUsuarioContrasena(adm);
	}

}