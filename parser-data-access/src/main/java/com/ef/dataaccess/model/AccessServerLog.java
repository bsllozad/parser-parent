package com.ef.dataaccess.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 
 * @comment Entity for save records to Access Server Log
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser-data-access
 * @class AccessServerLog
 * @date Jul 7, 2019
 *
 */
@Data
@Entity
public class AccessServerLog implements Serializable{

	private static final long serialVersionUID = 5940134289468335550L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private Timestamp requestDate;
	private String ip;
	private String requestType;
	private String responseHttpCode;
	private String description;
	
	public AccessServerLog() {
		
	}

	public AccessServerLog(Timestamp requestDate, String ip, String requestType, String responseHttpCode, String description) {
		this.requestDate = requestDate;
		this.ip = ip;
		this.requestType = requestType;
		this.responseHttpCode = responseHttpCode;
		this.description = description;
	}
	
	

}
