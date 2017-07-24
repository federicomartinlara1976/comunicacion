package net.bounceme.chronos.comunicacion.controllers.params;

/**
 * Clase usada para el paso de parámetros del controller de medios de comunicación del cliente
 * 
 * @author frederik
 *
 */
public class ParamsMedioComunicacion {

	private Long idCliente;
	
	private Long idTipo;
	
	private String valor;

	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the idTipo
	 */
	public Long getIdTipo() {
		return idTipo;
	}

	/**
	 * @param idTipo the idTipo to set
	 */
	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}
