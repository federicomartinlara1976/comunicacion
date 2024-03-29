package net.bounceme.chronos.comunicacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.model.TipoComunicacion;

@Repository
public interface TipoComunicacionRepository extends JpaRepository<TipoComunicacion, Long> {
	
	@Query("select t from TipoComunicacion t where t.denominacion = :name")
	TipoComunicacion findByName(@Param("name") String name);
}
