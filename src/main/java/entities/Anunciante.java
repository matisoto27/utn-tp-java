package entities;

public class Anunciante {
	
	private int id_anunciante;
	private String nombre;
	private String email;
	private String telefono;
	private String usuario;
	private String contrasena;
	
	public int getIdAnunciante() {
		return id_anunciante;
	}
	
	public void setIdAnunciante(int id_anunciante) {
		this.id_anunciante = id_anunciante;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
}