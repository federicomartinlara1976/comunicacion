package net.bounceme.chronos.comunicacion.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.data.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("select c from Cliente c	where c.nombre like :nombre and c.apellidos like :apellidos")
	List<Cliente> findByNombreAndApellidos(@Param("nombre") String nombre, @Param("apellidos") String apellidos);
	
	@Query("select c from Cliente c	where c.nombre like :nombre")
	List<Cliente> findByNombre(@Param("nombre") String nombre);
	
	@Query("select c from Cliente c	where c.apellidos like :apellidos")
	List<Cliente> findByApellidos(@Param("apellidos") String apellidos);
	
	@Query("select c from Cliente c	where c.dni = :dni")
	Cliente findByDni(@Param("dni") String dni);
}
