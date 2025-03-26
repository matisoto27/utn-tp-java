package logic;

import java.util.LinkedList;

import data.ClienteData;
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

}