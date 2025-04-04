package entities;

public class Propiedad {

	private Anunciante anunciante;
	private int nro_propiedad;
	private String direccion;
	private Integer piso;
	private String depto;
	private Double precio_actual;
	private String estado;
	
	public Propiedad() {}
	
	// Delete
	public Propiedad(Anunciante anunciante, int nro_propiedad) {
        this.anunciante = anunciante;
        this.nro_propiedad = nro_propiedad;
    }
	
	// Create
	public Propiedad(Anunciante anunciante, String direccion, Integer piso, String depto) {
        this.anunciante = anunciante;
        this.direccion = direccion;
        this.piso = piso;
        this.depto = depto;
    }
	
	// Update
	public Propiedad(Anunciante anunciante, int nro_propiedad, String direccion, Integer piso, String depto) {
        this.anunciante = anunciante;
        this.nro_propiedad = nro_propiedad;
        this.direccion = direccion;
        this.piso = piso;
        this.depto = depto;
    }
	
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