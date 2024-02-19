package net.bounceme.chronos.comunicacion.data.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.data.model.Cliente;
import net.bounceme.chronos.comunicacion.data.model.RegistroNotificacion;

@Repository
public interface RegistroNotificacionRepository extends JpaRepository<RegistroNotificacion, Long> {
	
	@Query("select r from RegistroNotificacion r where r.fechaHoraNotificacion between :from and :to order by r.fechaHoraNotificacion")
	List<RegistroNotificacion> findByDateRange(@Param("from") Date dFrom, @Param("to") Date dTo);
	
	@Query("select r from RegistroNotificacion r where r.cliente = :cliente order by r.fechaHoraNotificacion")
	List<RegistroNotificacion> findByCliente(@Param("cliente") Cliente cliente);
}
