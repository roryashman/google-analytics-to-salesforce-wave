package models.dao;

import java.util.List;

import models.Job;
import play.db.jpa.JPA;
/**
 * 
 * DAO class for manage of Background Jobs.
 * 
 * @author Igor Ivarov
 * @editor Sergey Legostaev
 *
 */

public class JobDAO extends BaseDAO<Job> {
	
	public static List<Job> getJobs() {
		try {
			return JPA.withTransaction(new play.libs.F.Function0<List<Job>>() {
				@SuppressWarnings("unchecked")
				public List<Job> apply () {
					return (List<Job>) JPA.em().createNamedQuery("Job.findAll").getResultList();
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Job getLastJob() {
		try {
			return JPA.withTransaction(new play.libs.F.Function0<Job>() {
				public Job apply () {
					return (Job) JPA.em().createQuery("select j from Job j ORDER BY j.id DESC", Job.class).setMaxResults(1).getSingleResult();
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Job> getJobsForScheduler() {
		try {
			return JPA.withTransaction(new play.libs.F.Function0<List<Job>>() {
				@SuppressWarnings("unchecked")
				public List<Job> apply () {
					return (List<Job>) JPA.em().createQuery("select j from Job j where j.status = :status or j.repeatPeriod <> null")
							.setParameter("status", "PENDING").getResultList();
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
