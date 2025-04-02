package entities;

public class Propiedad {

	private Anunciante anunciante;
	private int nro_propiedad;
	private String direccion;
	private Integer piso;
	private String depto;
	private Double precio_actual;
	private String estado;

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

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public Double getPrecioActual() {
		return precio_actual;
	}

	public void setPrecioActual(Double precio_actual) {
		this.precio_actual = precio_actual;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}