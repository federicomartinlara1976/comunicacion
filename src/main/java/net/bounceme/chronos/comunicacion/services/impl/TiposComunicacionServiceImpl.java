package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.data.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.data.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.services.TiposComunicacionService;

/**
 * Implementación del servicio que gestiona los tipos de comunicación aplicables
 * a los clientes
 * 
 * @author frederik
 *
 */
@Service(TiposComunicacionService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TiposComunicacionServiceImpl implements TiposComunicacionService {

	@Autowired
	@Qualifier(AppConfig.TIPOS_COMUNICACION_REPOSITORY)
	private DaoPersistence<TipoComunicacion> tiposComunicacionRepository;

	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.bounceme.chronos.comunicacion.services.TiposComunicacionService#nuevo
	 * (java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public TipoComunicacion nuevo(String denominacion, String nombreEmisor) {
		TipoComunicacion tipo = new TipoComunicacion();
		tipo.setDenominacion(denominacion);
		tipo.setNombreClaseEmisora(nombreEmisor);

		return tiposComunicacionRepository.saveObject(tipo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.bounceme.chronos.comunicacion.services.TiposComunicacionService#get(
	 * java.lang.String)
	 */
	@Override
	public TipoComunicacion get(Long id) {
		return tiposComunicacionRepository.getObject(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#
	 * actualizar(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void actualizar(Long id, String nuevaDenominacion, String nuevoEmisor) {
		TipoComunicacion tipoComunicacion = tiposComunicacionRepository.getObject(id);

		if (StringUtils.isNotBlank(nuevaDenominacion)) {
			tipoComunicacion.setDenominacion(nuevaDenominacion);
		}

		if (StringUtils.isNotBlank(nuevoEmisor)) {
			tipoComunicacion.setNombreClaseEmisora(nuevoEmisor);
		}

		tiposComunicacionRepository.updateObject(tipoComunicacion);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#
	 * borrar(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long id) {
		tiposComunicacionRepository.removeObject(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#
	 * listar()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoComunicacion> listar() {
		return new ArrayList<TipoComunicacion>(daoQueries.executeNamedQuery("tiposComunicacion", Boolean.TRUE));
	}
}
