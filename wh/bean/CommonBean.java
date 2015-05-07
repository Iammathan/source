
package wh.bean; 

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
/**
 *
 * @author meganath
 */

public class CommonBean {
    

    private final static String cb = CommonBean.class.getName();
    
    //static SessionFactory sessionFactory;
    static SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();

    /* Method to CREATE values in the database */
    public Long addData(Object tableclass){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Long id = null;
        try{
            
            tx = session.beginTransaction();
            id = (Long) session.save(tableclass);
            tx.commit();
        }catch (HibernateException e) {
            System.out.println("Common bean Error ::::\n");
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            
        }finally {
            session.close();
        }
        return id;
    }
    /* Method to  READ all the details */
    public List getDetails(String tableName){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List details =null;
        try{
            tx = session.beginTransaction();
            details = session.createQuery("FROM "+tableName).list();
            /*for (Iterator iterator = details.iterator(); iterator.hasNext();){
                User usr = (User) iterator.next(); 
                System.out.print("First Name: " + usr.getFirstName()); 
                System.out.print("  Last Name: " + usr.getLastName()); 
                System.out.println("  Salary: " + usr.getSalary()); 
            }*/
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
        }finally {
            session.close();
        }
        return details;
    }
}