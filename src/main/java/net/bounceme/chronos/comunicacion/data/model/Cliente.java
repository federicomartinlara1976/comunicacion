package net.bounceme.chronos.comunicacion.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad cliente
 * 
 * @author frederik
 *
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENTES")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 394031209826995192L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@Column
	private String apellidos;

	@Column
	private String dni;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "cliente")
	private List<Aviso> avisos;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "cliente")
	private List<MedioComunicacionCliente> mediosComunicacion;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "cliente")
	private List<DireccionCliente> direcciones;
}
