package com.ef.config.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @comment DTO from build parameter object from commandline execution 
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser-config
 * @class InitialProccessReqDto
 * @date Jul 7, 2019
 *
 */
@Data
public class InitialProccessReqDto implements Serializable{

	private static final long serialVersionUID = 4131159909331423107L;
	
	private String accesLog;
	private String duration;
	private String startDate;
	private Integer threshold;
	
	public InitialProccessReqDto () {
		
	}

}
