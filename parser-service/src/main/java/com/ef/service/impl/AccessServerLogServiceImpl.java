package com.ef.service.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ef.config.dto.InitialProccessReqDto;
import com.ef.config.enums.GenericConstants;
import com.ef.config.exception.WalletHubException;
import com.ef.config.utils.ParserUtils;
import com.ef.dataaccess.model.AccessServerLog;
import com.ef.dataaccess.model.ParserLog;
import com.ef.dataaccess.repository.AccessServerLogRepository;
import com.ef.dataaccess.repository.ParserLogRepository;
import com.ef.service.AccessServerLogService;


/**
 * @comment Implement with AccessServereLog run proccess behavior 
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser-service
 * @class AccessServerLogServiceImpl
 * @date Jul 7, 2019
 *
 */
@Service
@Scope("singleton")
public class AccessServerLogServiceImpl implements AccessServerLogService {

	private List<AccessServerLog> saveLogs = new ArrayList<>();
	private List<ParserLog> blockedIPList;

	@Autowired
	private ParserLogRepository parserLogRepository;
	
	@Autowired
	private AccessServerLogRepository accessServerLogRepository;

	
	/**
	 * @see(com.ef.service.AccessServerLogService.runProccess)
	 */
	@Override
	public void runProccess(InitialProccessReqDto parameters) throws WalletHubException {

		try {
			System.out.println("#############INIT PROCCESS#############");
			
			if (parameters.getAccesLog() == null) {
				saveLogs = accessServerLogRepository.findAll();
			} else {
				loadFile(parameters.getAccesLog());
			}
			
			findAndSaveBlockedIPs(parameters);
			
			System.out.println("#############FINISH PROCCESS#############");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Method for read file and save records in data base
	 * @comment 
	 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
	 * @date Jul 7, 2019
	 * @param pathString
	 */
	private void loadFile(String pathString) {

		List<AccessServerLog> logs = new ArrayList<>();

		try (FileReader reader = new FileReader(pathString)) {

			CSVFormat formatter = CSVFormat.DEFAULT.withDelimiter(GenericConstants.REGEX_SPLIT_FILE);
			CSVParser parser = formatter.parse(reader);
			int pageNumber = 500;

			System.out.println("--> Began to read a file and save in database");
			long start = System.currentTimeMillis();

			for (CSVRecord record : parser) {
				logs.add(new AccessServerLog(ParserUtils.convertStringToTimestamp(record.get(0), 1), record.get(1),
						record.get(2), record.get(3), record.get(4)));
				saveLogs.add(new AccessServerLog(ParserUtils.convertStringToTimestamp(record.get(0), 1), record.get(1),
						record.get(2), record.get(3), record.get(4)));
				if (record.getRecordNumber() == pageNumber) {
					accessServerLogRepository.saveAll(logs);
					logs.clear();
					pageNumber += 500;
					System.out.println("--> Records saved " + pageNumber);
				}
			}
			accessServerLogRepository.saveAll(logs);
			long end = System.currentTimeMillis();
			logs.clear();

			System.out.println("--> Second Duration saving records: " + TimeUnit.MILLISECONDS.toSeconds(end - start));

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
			e.printStackTrace();
		}

	}

	
	/**
	 * @comment Method for find and save blocked ips, each query sent
	 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
	 * @date Jul 7, 2019
	 * @param parameters
	 */
	private void findAndSaveBlockedIPs(InitialProccessReqDto parameters) {

		Map<String, List<AccessServerLog>> resultIps = saveLogs.stream().filter(
				record -> ParserUtils.isBetweenDates(record.getRequestDate(), parameters.getStartDate(), parameters.getDuration()))
				.collect(Collectors.groupingBy(AccessServerLog::getIp));

		blockedIPList = resultIps.entrySet().stream().filter(record -> record.getValue().size() >= parameters.getThreshold()).map(
				record -> new ParserLog(record.getKey(), 
						ParserUtils.convertStringToTimestamp(parameters.getStartDate(), 2), 
						ParserUtils.convertStringToTimestamp(ParserUtils.getStringFinishedDate(parameters.getStartDate(), parameters.getDuration()), 2),
						record.getValue().size(), createCommentBlocked(parameters, record.getValue().size()))).collect(Collectors.toList());
		
		System.out.println("---> Blocked IP's: ");
		blockedIPList.forEach(ip -> {
			System.out.println("IP:" +  ip.getIp() + " ---- Comment: \"" + ip.getCommentsBlocked() + "\"");
		});
		
		parserLogRepository.saveAll(blockedIPList);
	}
	
	/**
	 * @comment Method for create the comment blocked record
	 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
	 * @date Jul 7, 2019
	 * @param parameters
	 * @param numberRecords
	 * @return
	 */
	private String createCommentBlocked(InitialProccessReqDto parameters, int numberRecords) {
		StringBuilder builder = new StringBuilder();
		builder.append("Blocked due to exceed the threshold limit of ( ");
		builder.append(parameters.getThreshold());
		builder.append(" ) with number of records " + numberRecords);
		builder.append(" in the dates between ");
		builder.append(parameters.getStartDate());
		builder.append(" and ");
		builder.append(ParserUtils.getStringFinishedDate(parameters.getStartDate(), parameters.getDuration()));
		return builder.toString();
	}

}
