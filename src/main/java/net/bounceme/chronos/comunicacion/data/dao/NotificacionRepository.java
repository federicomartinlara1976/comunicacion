package net.bounceme.chronos.comunicacion.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

	@Query("select n from Notificacion n where n.estado = :estado order by n.fechaHoraEnvio")
	List<Notificacion> findByEstado(@Param("estado") String estado);
}
