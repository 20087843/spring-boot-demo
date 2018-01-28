package pri.smilly.demo.server.common.schedule;

import lombok.Getter;
import org.quartz.*;

public abstract class JobDescription<T extends JobDescription> {
    private final String defJobName = this.getClass().getSimpleName();
    private final String defGroupName = Scheduler.DEFAULT_GROUP;
    private final String defTriggerName = defJobName + "_Trigger";

    private Class<? extends Job> jobClass;
    private String jobName;
    private String groupName;
    private String triggerName;
    private ScheduleBuilder scheduleBuilder;
    private JobDataMap dataMap = new JobDataMap();

    @Getter
    private JobDetail jobDetail;
    @Getter
    private JobKey jobKey;
    @Getter
    private Trigger trigger;
    @Getter
    private TriggerKey triggerKey;

    JobDescription(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
        this.jobName = jobClass.getName();
        this.groupName = Scheduler.DEFAULT_GROUP;
        this.triggerName = jobName + ".Trigger";
    }

    public T jobName(String jobName) {
        this.jobName = jobName;
        return (T) this;
    }

    public T groupName(String groupName) {
        this.groupName = groupName;
        return (T) this;
    }

    public T triggerName(String triggerName) {
        this.triggerName = triggerName;
        return (T) this;
    }

    public T scheduleBuilder(ScheduleBuilder scheduleBuilder) {
        this.scheduleBuilder = scheduleBuilder;
        return (T) this;
    }

    public T jobData(String key, Object value) {
        this.dataMap.put(key, value);
        return (T) this;
    }

    public T jobData(JobDataMap dataMap) {
        this.dataMap.putAll(dataMap);
        return (T) this;
    }

    public T build() {
        jobKey = JobKey.jobKey(defJobName, defGroupName);
        jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .usingJobData(dataMap)
                .build();

        triggerKey = TriggerKey.triggerKey(defTriggerName, defGroupName);
        trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder)
                .withIdentity(triggerKey)
                .build();
        return (T) this;
    }

}
