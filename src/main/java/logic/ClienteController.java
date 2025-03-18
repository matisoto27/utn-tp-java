package logic;

import data.ClienteData;
import entities.Cliente;

public class ClienteController {
	
	ClienteData cd;
	
	public ClienteController() {
		cd = new ClienteData();
	}
	
	public boolean validarDniUnico(Cliente cli) {
		return cd.getByDni(cli) == null;
	}
	
	public Cliente validarCredenciales(Cliente cli) {
		return cd.getByDniContrasena(cli);
	}
	
	public void add(Cliente cli) {
		cd.add(cli);
	}
}