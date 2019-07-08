package com.ef.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ef.dataaccess.model.AccessServerLog;

/**
 * 
 * @comment Interface JPS for AccessServerLog Transactions 
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser-data-access
 * @class AccessServerLogRepository
 * @date Jul 7, 2019
 *
 */
public interface AccessServerLogRepository extends JpaRepository<AccessServerLog, Long>{
	

}
