package net.bounceme.chronos.comunicacion.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;

@Repository
public interface MedioComunicacionClienteRepository extends JpaRepository<MedioComunicacionCliente, Long> {

	@Query("select m from MedioComunicacionCliente m where m.cliente = :cliente")
	List<MedioComunicacionCliente> findByCliente(@Param("cliente") Cliente cliente);
	
	@Query("select m from MedioComunicacionCliente m where m.cliente = :cliente and m.tipo.denominacion = :tipo")
	MedioComunicacionCliente findByClienteAndTipo(@Param("cliente") Cliente cliente, @Param("tipo") String tipo);
}
