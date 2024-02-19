package net.bounceme.chronos.comunicacion.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una notificación de aviso al cliente.
 * De esta forma si un medio fallase, se puede elegir otro y volver a
 * notificarle
 * 
 * @author frederik
 *
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTIFICACIONES")
public class Notificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6588580781792975840L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
     * Fecha y hora de creación
     */
    @Column
	private Date fechaHoraCreacion;
	
	/**
	 * Fecha y hora del envío
	 */
	@Column
	private Date fechaHoraEnvio;
	
	/**
	 * Indica si se ha logrado enviar el aviso o no
	 */
	@Column
	private String resultado;
	
	@Column
	private Integer reintentos;
	
	@Column
	private String estado;
	
	/**
	 * El aviso con la fecha de inicio de la obra
	 * y el medio de comunicación
	 */
	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name="idAviso", nullable=false, updatable=false)
	private Aviso aviso;
	
	/**
     * 
     */
    @JsonIgnore
    @OneToOne
    private MedioComunicacionCliente medioComunicacionCliente;
}
