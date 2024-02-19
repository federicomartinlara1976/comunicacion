package net.bounceme.chronos.comunicacion.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long> {

	@Query("select a from Aviso a where a.cliente = :cliente order by a.fechaInicioObra")
	List<Aviso> findByCliente(@Param("cliente") Cliente cliente);
}
