package entities;

import java.time.LocalDate;

public class Precio {

	private int id_anunciante;
	private int nro_propiedad;
	private LocalDate fecha_desde;
	private int valor;
	
	public int getIdAnunciante() {
		return id_anunciante;
	}
	
	public void setIdAnunciante(int id_anunciante) {
		this.id_anunciante = id_anunciante;
	}
	
	public int getNroPropiedad() {
		return nro_propiedad;
	}
	
	public void setNroPropiedad(int nro_propiedad) {
		this.nro_propiedad = nro_propiedad;
	}
	
	public LocalDate getFechaDesde() {
		return fecha_desde;
	}
	
	public void setFechaDesde(LocalDate fecha_desde) {
		this.fecha_desde = fecha_desde;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
}