package com.ef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ef.config.dto.InitialProccessReqDto;
import com.ef.config.enums.GenericConstants;
import com.ef.service.AccessServerLogService;


/**
 * 
 * @comment Main class from jar
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser
 * @class Parser
 * @date Jul 7, 2019
 *
 */
@SpringBootApplication(scanBasePackages = "com.ef")
public class Parser implements CommandLineRunner{
	
	@Autowired
	private AccessServerLogService accessServerLogService;
	
	public static void main(String[] args) {
		
		SpringApplication.run(Parser.class, args);
		
	}
	
	public void run(String... args) throws Exception {
		try {
			
			InitialProccessReqDto parameters = new InitialProccessReqDto();
			
			//get parameters from arguments
			for (String parameter : args) {
				if (parameter.contains(GenericConstants.REGEX_ARG_PARAMETER)) {
					String parameterCode = parameter.split(GenericConstants.REGEX_ARG_PARAMETER)[0];
					
					switch (parameterCode) {
					case GenericConstants.ACCES_LOG:
						parameters.setAccesLog(parameter.split(GenericConstants.REGEX_ARG_PARAMETER)[1]);
						break;
					case GenericConstants.DURATION:
						parameters.setDuration(parameter.split(GenericConstants.REGEX_ARG_PARAMETER)[1]);
						break;
					case GenericConstants.START_DATE:
						parameters.setStartDate(parameter.split(GenericConstants.REGEX_ARG_PARAMETER)[1]);
						break;
					case GenericConstants.THRESHOLD:
						parameters.setThreshold(Integer.parseInt(parameter.split(GenericConstants.REGEX_ARG_PARAMETER)[1]));
						break;

					default:
						break;
					}
				} else {
					System.out.println("The parameter " + parameter + " don't contain the regex equals (=)");
				}
			}
			
			accessServerLogService.runProccess(parameters);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
