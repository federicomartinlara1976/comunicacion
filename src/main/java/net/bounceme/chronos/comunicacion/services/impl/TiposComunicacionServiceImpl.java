package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.data.dao.TipoComunicacionRepository;
import net.bounceme.chronos.comunicacion.data.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.dto.TipoComunicacionDTO;
import net.bounceme.chronos.comunicacion.services.TiposComunicacionService;
import net.bounceme.chronos.utils.assemblers.BidirectionalAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Implementación del servicio que gestiona los tipos de comunicación aplicables
 * a los clientes
 * 
 * @author frederik
 *
 */
@Service
@Slf4j
public class TiposComunicacionServiceImpl implements TiposComunicacionService {
	
	@Autowired
	private TipoComunicacionRepository tipoComunicacionRepository;
	
	@Autowired
	@Qualifier("tipoComunicacionAssembler")
	private BidirectionalAssembler<TipoComunicacion, TipoComunicacionDTO> tipoComunicacionAssembler;

	@Override
	@Transactional
	@SneakyThrows
	public TipoComunicacionDTO save(TipoComunicacionDTO tipoComunicacionDTO) {
		try {
			TipoComunicacion tipoComunicacion = tipoComunicacionAssembler.reverseAssemble(tipoComunicacionDTO);
			tipoComunicacionRepository.save(tipoComunicacion);
			return tipoComunicacionAssembler.assemble(tipoComunicacion);
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public TipoComunicacionDTO findById(Long id) {
		try {
			Optional<TipoComunicacion> oTipoComunicacion = tipoComunicacionRepository.findById(id);
			return oTipoComunicacion.isPresent() ? tipoComunicacionAssembler.assemble(oTipoComunicacion.get()) : null;
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoComunicacionDTO findByName(String name) {
		try {
			TipoComunicacion tipoComunicacion = tipoComunicacionRepository.findByName(name);
			return !Objects.isNull(tipoComunicacion) ? tipoComunicacionAssembler.assemble(tipoComunicacion) : null;
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		tipoComunicacionRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoComunicacionDTO> list() {
		try {
			List<TipoComunicacion> tipos = tipoComunicacionRepository.findAll();
			return new ArrayList<>(tipoComunicacionAssembler.assemble(tipos));
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}
}
