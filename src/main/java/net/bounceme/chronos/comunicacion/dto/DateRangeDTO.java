package net.bounceme.chronos.comunicacion.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeDTO implements Serializable {

	
	/**
     * 
     */
    private static final long serialVersionUID = -6588207037035821635L;

    private String from;

	private String to;
}
