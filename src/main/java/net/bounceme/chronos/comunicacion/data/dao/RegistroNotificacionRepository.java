package net.bounceme.chronos.comunicacion.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;

@Repository
public interface RegistroNotificacionRepository extends JpaRepository<RegistroNotificacion, Long> {

}
