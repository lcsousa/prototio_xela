package br.com.samsung.wms.latam.cellowmsestore.schedule.worker;

import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WorkerProcessJob  {

		
	//Some Service to call
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void processJob() throws SchedulingException {
		log.info("-------------------------------- START WORKER PROCESS --------------------------------");


		log.info("-------------------------------- END WORKER PROCESS --------------------------------");

	}



		
}	

