package com.seetreet.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


/**
 * Servlet implementation class schedulerServlet
 */
@WebServlet("/schedulerServlet")
public class schedulerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public void init(){
    	try{
    		SchedulerFactory schedFact = new StdSchedulerFactory();
    		Scheduler sched = schedFact.getScheduler();

   		 	sched.start();
   		
   			JobDetail taskJob = org.quartz.JobBuilder.newJob(TaskJob.class)
   				.withIdentity("taskJob", "group1")
   				.build();
   			Trigger trigger = org.quartz.TriggerBuilder.newTrigger()
   				.withIdentity("trigger", "group1")
   				.startNow()
   				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
//   						.withIntervalInHours(12)
   						.withIntervalInSeconds(50)	
   						.repeatForever())            
   						.build();
   			
 		
   		sched.scheduleJob(taskJob, trigger);

    	}catch(SchedulerException e){
    		e.printStackTrace();
    	}
    	
    }

}
