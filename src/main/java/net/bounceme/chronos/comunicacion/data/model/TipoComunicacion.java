package net.bounceme.chronos.comunicacion.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad para los tipos de comunicación.
 * 
 * TODO - Se puede usar el nombre de un identificador de Bean para determinar
 * el bean que se usará para enviar un aviso mediante este tipo
 * 
 * @author frederik
 *
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TIPOS_COMUNICACION")
public class TipoComunicacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7342690245310292143L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String denominacion;
	
	/**
	 * Nombre del bean que va a realizar el envío
	 */
	@Column
	private String nombreClaseEmisora;
	
	@Column
	private String icono;
}
