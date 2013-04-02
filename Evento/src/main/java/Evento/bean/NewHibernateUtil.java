/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.bean;

import java.net.URL;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Baro
 */
public class NewHibernateUtil {

    private static final SessionFactory sessionFactory;
    private static Configuration config;
    private static String  path = "hibernate.cfg.xml";
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
          //  sessionFactory = new Configuration().configure().buildSessionFactory();
        	
        	System.out.println("DUPA");
        	
        	
            
            URL url = NewHibernateUtil.class.getResource(path);
            
            System.out.println("AAAAAAAAAA"+url);
            
	        config = new Configuration().configure(url);
	        sessionFactory = config.buildSessionFactory();
 
	      
           
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            
            URL url = NewHibernateUtil.class.getResource(path);
            
        	System.out.println("AAAAAAAAAA"+url);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
