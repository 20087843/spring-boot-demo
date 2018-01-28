package pri.smilly.demo.server.common.schedule;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

public class SchedulerManager {
    @Autowired
    private Scheduler scheduler;

    public void start() throws SchedulerException {
        scheduler.start();
    }

    public void stop() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown(true);
        }
    }

    public void addJob(JobDescription job) throws SchedulerException {
        scheduler.scheduleJob(job.getJobDetail(), job.getTrigger());
    }

    public void pauseJob(JobDescription job) throws SchedulerException {
        scheduler.pauseJob(job.getJobKey());
    }

    public void resumeJob(JobDescription job) throws SchedulerException {
        scheduler.resumeJob(job.getJobKey());
    }

    public boolean isJobExist(JobDescription job) throws SchedulerException {
        return scheduler.checkExists(job.getJobKey());
    }

    public boolean removeJob(JobDescription job) throws SchedulerException {
        return !isJobExist(job) || scheduler.deleteJob(job.getJobKey());
    }

    public boolean interruptJob(JobDescription job) throws SchedulerException {
        return !isJobExist(job) || scheduler.interrupt(job.getJobKey());
    }


}
