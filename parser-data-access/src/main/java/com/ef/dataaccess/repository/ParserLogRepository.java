package com.ef.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ef.dataaccess.model.ParserLog;

/**
 * 
 * @comment Interface JPS for ParserLogRepository Transactions 
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser-data-access
 * @class ParserLogRepository
 * @date Jul 7, 2019
 *
 */
public interface ParserLogRepository extends JpaRepository<ParserLog, Long>{

}
