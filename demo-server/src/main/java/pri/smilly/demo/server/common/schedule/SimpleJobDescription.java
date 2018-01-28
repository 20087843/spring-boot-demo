package pri.smilly.demo.server.common.schedule;

import org.quartz.Job;
import org.quartz.SimpleScheduleBuilder;

public class SimpleJobDescription extends JobDescription<SimpleJobDescription> {

    private SimpleJobDescription(Class<? extends Job> jobClass) {
        super(jobClass);
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        this.scheduleBuilder(scheduleBuilder);
    }

    public static SimpleJobDescription builder(Class<? extends Job> jobClass) {
        return new SimpleJobDescription(jobClass);
    }

}
