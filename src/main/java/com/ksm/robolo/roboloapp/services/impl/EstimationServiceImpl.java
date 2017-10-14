package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.enums.TaskStatus;
import com.ksm.robolo.roboloapp.services.EstimationService;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import com.ksm.robolo.roboloapp.tos.TaskTO;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EstimationServiceImpl implements EstimationService{

    private static final Long DAY_IN_MILIS = 1000 * 60 * 60 * 24L;

    @Override
    public Date estimateProjectEndDate(ProjectTO projectTO) {
        /*
        TODO Create smarter estimation algoritm !!
        - maybe sth like avarage between worst and best case scenario?
        - when there are multiple tasks in progress we can sum their duration (what's left = now - taskStart) and multiply by
        some factor to make it a bit smaller
        - How to treat the tasks in TO_DO ? Maybe the same as the ones in in progress, or take avarage between best case (all concurrently)
        and worst case (all in line)
         */

        Date approximateEndDate = new Date();
        Long projectDuration = 0L;
        for (TaskTO taskTO : projectTO.getTaskTOS()) {
            if (!TaskStatus.DONE.equals(taskTO.getStatus())) {
                projectDuration += taskTO.getEstimatedTaskDuration() * DAY_IN_MILIS;
            }
        }
        approximateEndDate.setTime(approximateEndDate.getTime() + projectDuration);
        return approximateEndDate;
    }
}
