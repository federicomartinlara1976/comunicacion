package net.bounceme.chronos.comunicacion.model;

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
@Table(name = "DIRECCIONES_CLIENTE")
public class DireccionCliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2396223420414145945L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Cliente al que pertenece este medio
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;

	@Column
	private String direccion;
	
	@Column
	private String numero;
	
	@Column
	private Integer piso;
	
	@Column
	private String escalera;
	
	@Column
	private String puerta;
	
	@Column
	private String localidad;
	
	@Column
	private String provincia;
	
	@Column
	private String codigoPostal;
}
