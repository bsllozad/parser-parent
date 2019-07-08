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
 * @comment Entity for save requesto from audits queries 
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser-data-access
 * @class ParserLog
 * @date Jul 7, 2019
 *
 */
@Data
@Entity
public class ParserLog implements Serializable{

	private static final long serialVersionUID = 522931782882733293L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private String ip;
	private Timestamp startDate;
	private Timestamp finishDate;
	private Integer numberRecords;
	private String commentsBlocked;
	
	public ParserLog() {
		
	}

	public ParserLog(String ip, Timestamp startDate, Timestamp finishDate, Integer numberRecords, String commentsBlocked) {
		this.ip = ip;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.numberRecords = numberRecords;
		this.commentsBlocked = commentsBlocked;
	}
	
	
}
