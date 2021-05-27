package backend;

import backend.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private static final Logger LOG =
      LoggerFactory.getLogger(CommandLineAppStartupRunner.class);
    @Autowired
    private Service service;
    public static int counter;

    @Override
    public void run(String...args) throws Exception {
/*
    	Thread.sleep(10000);
    	for(int i=0;i<5;i++)
        {
    		int x=i+1;
    		this.service.generateNotifications("Hello world number : "+x);
        	Thread.sleep(5000);
        }*/
    }
}