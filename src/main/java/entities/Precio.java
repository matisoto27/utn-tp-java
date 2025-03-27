package entities;

import java.time.LocalDate;

public class Precio {

	private Propiedad propiedad;
	private LocalDate fecha_desde;
	private double valor;

	public Propiedad getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

	public LocalDate getFechaDesde() {
		return fecha_desde;
	}

	public void setFechaDesde(LocalDate fecha_desde) {
		this.fecha_desde = fecha_desde;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}