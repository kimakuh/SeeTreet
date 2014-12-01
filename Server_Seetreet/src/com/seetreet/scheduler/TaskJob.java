package com.seetreet.scheduler;

import com.seetreet.dao.MongoDAO;
import com.seetreet.dao.MongoDB;
import com.seetreet.dao.MongoRecDAO;
import com.seetreet.http.HttpCall;
import com.seetreet.http.HttpControl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mongodb.DBObject;

public class TaskJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException{
		System.out.println("-------------------------------------");
		System.out.println("TaskJob executed");
		/*
		if(MongoDAO.isTaskData()){
			MongoDAO.updateTaskTime();
			List<DBObject> list = MongoDAO.taskTimeCheck();
			//System.out.println(list.size());
			//Todo list in check
			if(list!=null&&list.size()>0){
				MongoDAO.insertQueue(list);
			}
		}
		*/
		MongoRecDAO.updateRecommandValue();
		System.out.println("TaskJob end : ");
		System.out.println("-------------------------------------\n");
		
	}
}
