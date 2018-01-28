package pri.smilly.demo.server.common.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;

public class CronJobDescription extends JobDescription<CronJobDescription> {

    private String cron;

    private CronJobDescription(Class<? extends Job> jobClass, String cron) {
        super(jobClass);
        this.cron = cron;
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        this.scheduleBuilder(scheduleBuilder);
    }

    public static CronJobDescription builder(Class<? extends Job> jobClass, String cron) {
        return new CronJobDescription(jobClass, cron);
    }

}
