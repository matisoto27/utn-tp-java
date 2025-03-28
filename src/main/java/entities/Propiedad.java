package entities;

public class Propiedad {

	private Anunciante anunciante;
	private int nro_propiedad;
	private String direccion;
	private int piso;
	private String depto;

	public Anunciante getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(Anunciante anunciante) {
		this.anunciante = anunciante;
	}

	public int getNroPropiedad() {
		return nro_propiedad;
	}

	public void setNroPropiedad(int nro_propiedad) {
		this.nro_propiedad = nro_propiedad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

}