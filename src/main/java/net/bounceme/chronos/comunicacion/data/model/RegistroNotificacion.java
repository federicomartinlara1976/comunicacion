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
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REGISTRO_NOTIFICACION")
public class RegistroNotificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8323776740630772201L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idCliente", nullable = true)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idNotificacion", nullable = true)
	private Notificacion notificacion;
	
	@Column
	private String resultado;
	
	@Column
	private Date fechaHoraNotificacion;
}
