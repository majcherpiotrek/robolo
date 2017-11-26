package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.enums.TaskStatus;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import com.ksm.robolo.roboloapp.tos.TaskTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EstimationServiceImplTest {

    EstimationServiceImpl estimationService;

    TestObjectsFactory testObjectsFactory;

    @Test
    public void estimateProjectEndDate() throws Exception {
        estimationService = new EstimationServiceImpl();

        testObjectsFactory = new TestObjectsFactory();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginningDate = sdf.parse(sdf.format(new Date()));
        int firstTaskDuration = 2;
        int secondTaskDuration = 1;
        int projectDurationDays = firstTaskDuration + secondTaskDuration;
        Calendar calendarExpected = Calendar.getInstance();
        calendarExpected.setTime(beginningDate);



        ProjectTO projectTO = new ProjectTO();
        projectTO.setStartDate(beginningDate);

        TaskTO task1 = new TaskTO();
        task1.setEstimatedTaskDurationHours(firstTaskDuration);
        task1.setStatus(TaskStatus.IN_PROGRESS);

        TaskTO task2 = new TaskTO();
        task2.setEstimatedTaskDurationHours(secondTaskDuration);
        task2.setStatus(TaskStatus.TO_DO);

        TaskTO task3 = new TaskTO();
        task3.setEstimatedTaskDurationHours(secondTaskDuration);
        task3.setStatus(TaskStatus.DONE);

        List<TaskTO> taskTOList = new LinkedList<>();
        taskTOList.add(task1);
        taskTOList.add(task2);
        taskTOList.add(task3);

        projectTO.setTaskTOS(taskTOList);

        Date approximateEndDate = estimationService.estimateProjectEndDate(projectTO);

        Calendar calendarTested = Calendar.getInstance();
        calendarTested.setTime(approximateEndDate);

        int actualCalculatedDuration = calendarTested.get(Calendar.DAY_OF_YEAR) - calendarExpected.get(Calendar.DAY_OF_YEAR);

        assertEquals(projectDurationDays, actualCalculatedDuration);
    }
}