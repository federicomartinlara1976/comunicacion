package net.bounceme.chronos.comunicacion.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.data.model.Cliente;
import net.bounceme.chronos.comunicacion.data.model.DireccionCliente;

@Repository
public interface DireccionClienteRepository extends JpaRepository<DireccionCliente, Long> {

	@Query("select d from DireccionCliente d where d.cliente = :cliente")
	List<DireccionCliente> findByCliente(@Param("cliente") Cliente cliente);
}
