package net.bounceme.chronos.comunicacion.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.data.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.data.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.dto.TipoComunicacionDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TiposComunicacionService {
	private static final String ERROR = "ERROR: ";

    private static final Logger log = LoggerFactory.getLogger(TiposComunicacionService.class);

	@Autowired
	@Qualifier(AppConfig.TIPOS_COMUNICACION_REPOSITORY)
	private DaoPersistence<TipoComunicacion> tiposComunicacionRepository;

	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;
	
	@Autowired
	@Qualifier("tipoComunicacionAssembler")
	private Assembler<TipoComunicacion, TipoComunicacionDTO> tipoComunicacionAssembler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.bounceme.chronos.comunicacion.services.TiposComunicacionService#nuevo
	 * (java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public TipoComunicacionDTO nuevo(String denominacion, String nombreEmisor) throws ServiceException {
		try {
			TipoComunicacion tipo = new TipoComunicacion();
			tipo.setDenominacion(denominacion);
			tipo.setNombreClaseEmisora(nombreEmisor);

			return tipoComunicacionAssembler.assemble(tiposComunicacionRepository.saveObject(tipo));
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#get(
	 * java.lang.String)
	 */
	public TipoComunicacionDTO get(Long id) {
		try {
			return tipoComunicacionAssembler.assemble(tiposComunicacionRepository.getObject(id));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public TipoComunicacionDTO get(String name) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("denominacion", name);
		Optional<TipoComunicacion> oResult = daoQueries.executeScalarNamedQuery("tipoComunicacion", parameters, Boolean.TRUE);
		
		try {
			return (oResult.isPresent()) ? tipoComunicacionAssembler.assemble(oResult.get()) : null;
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#
	 * actualizar(java.lang.String, java.lang.String)
	 */
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
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#
	 * borrar(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long id) throws ServiceException {
		try {
			tiposComunicacionRepository.removeObject(id);
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.TiposComunicacionService#
	 * listar()
	 */
	@SuppressWarnings("unchecked")
	public List<TipoComunicacionDTO> listar() {
		try {
			return new ArrayList<>(tipoComunicacionAssembler.assemble(
					daoQueries.executeNamedQuery("tiposComunicacion", Boolean.TRUE)));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return Collections.emptyList();
		}
	}
}
