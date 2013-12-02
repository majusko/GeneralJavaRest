package sk.kapusta.schedule;

import static java.util.concurrent.TimeUnit.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);
	

	/**
	 * This method will initialize scheduler when project will build
	 */
	@PostConstruct
    private void initializeTenSecSchedule() {
		
		List<Runnable> jobs = new ArrayList<Runnable>();
		//add jobs to list
		jobs.add(doSomeTestLogs());
		jobs.add(doSomeTestLogs2());
		//create new the
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(jobs.size());
		
        for(Runnable job : jobs){

            scheduler.scheduleWithFixedDelay(job, 10, 10, SECONDS);
            
        }
        
    }
	
	/**
	 * ---------------------some schedule tasks--------------------------
	 */
	
	private Runnable doSomeTestLogs(){
		
		final Runnable job = new Runnable() {
            public void run() { 
            	
            	LOG.debug("== aaaaaaaaaaaaa SCHEDULE", 1);
                System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());

            }
		};
		
		return job;
		
	}
	
	private Runnable doSomeTestLogs2(){
		
		final Runnable job = new Runnable() {
            public void run() { 
            	
            	LOG.debug("== bbbbbbbbb SCHEDULE", 1);
                System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());

            }
		};
		
		return job;
		
	}
	
}
