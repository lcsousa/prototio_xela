package br.com.samsung.wms.latam.cellowmsestore.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.samsung.wms.latam.cellowmsestore.schedule.worker.WorkerProcessJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Component
public class ScheduleProcessJob {

	@Autowired
	private WorkerProcessJob worker;
	
	private static final boolean PROCESSING 			= true;	
	private static final boolean STOPPED    			= false;
	private boolean processJob		= false;
	
	
	
	@Async
	@Scheduled(cron = "0 0/1 * * * *") //Executa a cada 1 minuto 
	public void processJob() throws SchedulingException {
		if (!this.processJob) {
			long time = System.currentTimeMillis();
			try {
				log.info("Process Picking Complete " + new Date().toString());
				this.processJob = PROCESSING;
				this.worker.processJob();
			}catch (Exception e) {
				log.error("Exception of method processJob() from class " + this.getClass().getName(), e);
			}
			this.processJob = STOPPED;
			log.info("Job processJob finished. Duration [" + ((System.currentTimeMillis() - time) / 1000 ) + "] seconds \n");
		}
		
	}
	

}
