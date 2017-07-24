package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.ComunicacionApplication;
import net.bounceme.chronos.comunicacion.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.exceptions.DataException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.services.TiposComunicacionService;
import net.bounceme.chronos.comunicacion.utils.Finalizer;

/**
 * Implementación del servicio que gestiona los tipos de comunicación aplicables a los clientes
 * 
 * @author frederik
 *
 */
@Service(TiposComunicacionService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TiposComunicacionServiceImpl extends Finalizer implements TiposComunicacionService {
	
	private static final Logger log = Logger.getLogger(TiposComunicacionServiceImpl.class);
	
	@Autowired
	@Qualifier(ComunicacionApplication.TIPOS_COMUNICACION_REPOSITORY)
	private DaoPersistence<TipoComunicacion> tiposComunicacionRepository;
	
	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#nuevo(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public TipoComunicacion nuevo(String denominacion, String nombreEmisor) throws ServiceException {
		try {
			TipoComunicacion tipo = new TipoComunicacion();
			tipo.setDenominacion(denominacion);
			tipo.setNombreClaseEmisora(nombreEmisor);
			
			return tiposComunicacionRepository.saveObject(tipo);
		} catch (DataException exception) {
			log.error(exception);
			throw new ServiceException(exception);
		}
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#get(java.lang.String)
	 */
	@Override
	public TipoComunicacion get(Long id) {
		return tiposComunicacionRepository.getObject(id);
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#actualizar(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void actualizar(Long id, String nuevaDenominacion, String nuevoEmisor) throws ServiceException {
		try {
			TipoComunicacion tipoComunicacion = tiposComunicacionRepository.getObject(id);
			
			if (StringUtils.isNotBlank(nuevaDenominacion)) {
				tipoComunicacion.setDenominacion(nuevaDenominacion);
			}
			
			if (StringUtils.isNotBlank(nuevoEmisor)) {
                tipoComunicacion.setNombreClaseEmisora(nuevoEmisor);
            }
			
			tiposComunicacionRepository.updateObject(tipoComunicacion);
		} catch (DataException exception) {
			log.error(exception);
			throw new ServiceException(exception);
		}
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#borrar(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long id) throws ServiceException {
		try {
			tiposComunicacionRepository.removeObject(id);
		} catch (DataException exception) {
			log.error(exception);
			throw new ServiceException(exception);
		}
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#listar()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoComunicacion> listar() {
		return new ArrayList<TipoComunicacion>(daoQueries.executeNamedQuery("tiposComunicacion", Boolean.TRUE));
	}
}
