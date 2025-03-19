package entities;

import java.time.LocalDate;

public class Alquiler {

	private int id_alquiler;
	private Cliente cliente;
	private Propiedad propiedad;
	private LocalDate fecha_solicitado;
	private String estado;
	private LocalDate fecha_inicio_contrato;
	private LocalDate fecha_fin_contrato;
	private LocalDate fecha_renuncia;
	private int puntuacion;
	private String comentario;
	
	public int getIdAlquiler() {
		return id_alquiler;
	}
	
	public void setIdAlquiler(int id_alquiler) {
		this.id_alquiler = id_alquiler;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Propiedad getPropiedad() {
		return propiedad;
	}
	
	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}
	
	public LocalDate getFechaSolicitado() {
		return fecha_solicitado;
	}
	
	public void setFechaSolicitado(LocalDate fecha_solicitado) {
		this.fecha_solicitado = fecha_solicitado;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public LocalDate getFechaInicioContrato() {
		return fecha_inicio_contrato;
	}
	
	public void setFechaInicioContrato(LocalDate fecha_inicio_contrato) {
		this.fecha_inicio_contrato = fecha_inicio_contrato;
	}
	
	public LocalDate getFechaFinContrato() {
		return fecha_fin_contrato;
	}
	
	public void setFechaFinContrato(LocalDate fecha_fin_contrato) {
		this.fecha_fin_contrato = fecha_fin_contrato;
	}
	
	public LocalDate getFechaRenuncia() {
		return fecha_renuncia;
	}
	
	public void setFechaRenuncia(LocalDate fecha_renuncia) {
		this.fecha_renuncia = fecha_renuncia;
	}
	
	public int getPuntuacion() {
		return puntuacion;
	}
	
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}