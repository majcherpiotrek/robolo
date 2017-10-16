package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.domain.*;
import com.ksm.robolo.roboloapp.repository.ProjectRepository;
import com.ksm.robolo.roboloapp.repository.TaskRepository;
import com.ksm.robolo.roboloapp.services.EstimationService;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {

    private static final Long DAY_IN_MILIS = 1000 * 60 * 60 * 24L;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    TaskRepository taskRepository;

    @Mock
    EstimationService estimationServiceMock;

    TestObjectsFactory testObjectsFactory;

    @Before
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllProjectsNoProjectsInRepository() throws Exception {
        Mockito.when(projectRepository.findAll()).thenReturn(null);
        projectService.getAllProjects();
    }

    @Test
    public void getAllProjectsShouldReturnListWithOneProject() throws Exception {
        testObjectsFactory = new TestObjectsFactory();
        ProjectEntity projectEntity = testObjectsFactory.createProjectEntity();
        TaskEntity taskEntity = testObjectsFactory.createTaskEntityForProjectEntity(projectEntity);

        List<ProjectEntity> projectEntityList = new LinkedList<>();
        projectEntityList.add(projectEntity);

        List<TaskEntity> taskEntityList = new LinkedList<>();
        taskEntityList.add(taskEntity);

        Mockito.when(projectRepository.findAll()).thenReturn(projectEntityList);
        Mockito.when(taskRepository.findByProject_Id(projectEntity.getId())).thenReturn(taskEntityList);
        Mockito.when(estimationServiceMock.estimateProjectEndDate(Mockito.any(ProjectTO.class))).thenReturn(new Date());
        List<ProjectTO> projectTOList = projectService.getAllProjects();

        assertNotNull(projectTOList);
        assertTrue(projectTOList.size() == 1);
        assertEquals(projectEntity.getId(), projectTOList.get(0).getId());
        assertNotNull(projectTOList.get(0).getTaskTOS());
        assertTrue(projectTOList.get(0).getTaskTOS().size() == 1);
        assertEquals(taskEntity.getId(), projectTOList.get(0).getTaskTOS().get(0).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllProjectsTaskRepositoryReturnsNull() throws Exception {
        testObjectsFactory = new TestObjectsFactory();
        ProjectEntity projectEntity = testObjectsFactory.createProjectEntity();

        List<ProjectEntity> projectEntityList = new LinkedList<>();
        projectEntityList.add(projectEntity);

        Mockito.when(projectRepository.findAll()).thenReturn(projectEntityList);
        Mockito.when(taskRepository.findByProject_Id(projectEntity.getId())).thenReturn(null);

        projectService.getAllProjects();
    }

    @Test
    public void getAllProjectsShouldReturnEmptyList() throws Exception {
        List<ProjectEntity> projectEntityList = new LinkedList<>();

        Mockito.when(projectRepository.findAll()).thenReturn(projectEntityList);
        Mockito.verifyZeroInteractions(taskRepository);

        List<ProjectTO> projectTOList = projectService.getAllProjects();
        assertNotNull(projectTOList);
        assertTrue(projectTOList.size() == 0);
    }

    @Test
    public void getAllProjectStubsFromClient() throws Exception {

    }

    @Test
    public void getProject() throws Exception {

    }
}