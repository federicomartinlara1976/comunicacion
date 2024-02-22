package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de avisos del cliente, pensada para que el cliente
 * reciba un aviso por cada obra contratada
 * 
 * @author frederik
 *
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AVISOS")
public class Aviso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1518449193073022458L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private Date fechaInicioObra;
	
	@Column
	private String mensaje;
	
	@Column
	private Boolean estaNotificado;
	
	/**
	 * Este es el cliente para el cual se genera este aviso
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idCliente", nullable = true)
	private Cliente cliente;

	/**
	 * Esta es la direccion del cliente para el cual se genera este aviso
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idDireccion", nullable = true)
	private DireccionCliente direccionCliente;
	
	/**
	 * Todas las notificaciones de este aviso. Si un medio fallase,
	 * la notificación indicará el fallo, con lo que se deberá
	 * escoger otro medio y volver a enviar el aviso
	 */
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "NOTIFICACIONES_AVISO")
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "aviso", targetEntity = Notificacion.class)
	private List<Notificacion> notificaciones;
}
