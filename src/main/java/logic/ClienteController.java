package logic;

import java.util.LinkedList;

import data.ClienteData;
import entities.Alquiler;
import entities.Cliente;

public class ClienteController {

	ClienteData cd;

	public ClienteController() {
		cd = new ClienteData();
	}

	public LinkedList<Cliente> getAll() {
		return cd.getAll();
	}

	public Cliente getByDni(Cliente cli) {
		String query = "SELECT * FROM clientes WHERE dni = ?";
		return cd.getByQuery(query, cli.getDni());
	}

	public boolean validarDniUnico(Cliente cli) {
		String query = "SELECT * FROM clientes WHERE dni = ?";
		return cd.getByQuery(query, cli.getDni()) == null;
	}

	public Cliente validarCredenciales(Cliente cli) {
		String query = "SELECT * FROM clientes WHERE dni = ? AND contrasena = ?";
		return cd.getByQuery(query, cli.getDni(), cli.getContrasena());
	}

	public void add(Cliente cli) {
		cd.add(cli);
	}

	public void update(Cliente cli) {
		cd.update(cli);
	}

	public void delete(Cliente cli) {
		cd.delete(cli);
	}
	
	public boolean tieneAlquiler(Cliente c) {
		Alquiler a = cd.getLastAlquiler(c);
		return (a != null && (a.getEstado().equals("Pendiente") || a.getEstado().equals("En curso")));
	}
	
	public boolean tieneAlquilerPendiente(Cliente c) {
		Alquiler a = cd.getLastAlquiler(c);
		return (a != null && a.getEstado().equals("Pendiente"));
	}
	
}