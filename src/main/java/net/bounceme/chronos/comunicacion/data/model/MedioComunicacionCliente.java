package net.bounceme.chronos.comunicacion.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una forma de comunicarse con el cliente
 * 
 * @author frederik
 *
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEDIOS_COMUNICACION_CLIENTE")
public class MedioComunicacionCliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2396223420414145945L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * El tipo de comunicación (SMS, FAX, email)
	 */
	@ManyToOne
	@JoinColumn(name="idTipoComunicacion")
	private TipoComunicacion tipoComunicacion;
	
	/**
	 * Cliente al que pertenece este medio
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;

	/**
	 * Valor del medio (un número fijo, móvil o una dirección de correo electrónico)
	 */
	@Column
	private String valor;
}
