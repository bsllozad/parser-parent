package com.ef.service;

import com.ef.config.dto.InitialProccessReqDto;
import com.ef.config.exception.WalletHubException;

/**
 * 
 * @comment Interface with AccessServereLog behavior 
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser-service
 * @class AccessServerLogService
 * @date Jul 7, 2019
 *
 */
public interface AccessServerLogService {
	
	/**
	 * 
	 * @comment main proccess 
	 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
	 * @date Jul 7, 2019
	 * @param parameters
	 * @throws WalletHubException
	 */
	void runProccess(InitialProccessReqDto parameters) throws WalletHubException;

}
