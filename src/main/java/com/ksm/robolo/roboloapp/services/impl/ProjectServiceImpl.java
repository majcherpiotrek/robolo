package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.domain.AddressEntity;
import com.ksm.robolo.roboloapp.domain.ClientEntity;
import com.ksm.robolo.roboloapp.domain.ProjectEntity;
import com.ksm.robolo.roboloapp.domain.TaskEntity;
import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.enums.TaskStatus;
import com.ksm.robolo.roboloapp.repository.AddressRepository;
import com.ksm.robolo.roboloapp.repository.ClientRepository;
import com.ksm.robolo.roboloapp.repository.ProjectRepository;
import com.ksm.robolo.roboloapp.repository.TaskRepository;
import com.ksm.robolo.roboloapp.repository.UserRepository;
import com.ksm.robolo.roboloapp.services.EstimationService;
import com.ksm.robolo.roboloapp.services.ProjectService;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.ExceptionUnwrapper;
import com.ksm.robolo.roboloapp.services.exceptions.ProjectServiceException;
import com.ksm.robolo.roboloapp.services.util.impl.ProjectEntityToStubConverter;
import com.ksm.robolo.roboloapp.services.util.impl.ProjectEntityToTOConverter;
import com.ksm.robolo.roboloapp.services.util.impl.TaskToTOConverter;
import com.ksm.robolo.roboloapp.tos.ClientTO;
import com.ksm.robolo.roboloapp.tos.ProjectStubTO;
import com.ksm.robolo.roboloapp.tos.ProjectTO;
import com.ksm.robolo.roboloapp.tos.TaskTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);


    private ProjectRepository projectRepository;
    private AddressRepository addressRepository;
    private TaskRepository taskRepository;
    private ClientRepository clientRepository;
    private ProjectEntityToTOConverter projectEntityToTOConverter;
    private ProjectEntityToStubConverter projectEntityToStubConverter;
    private TaskToTOConverter taskToTOConverter;
    private EstimationService estimationService;
    private UserService userService;
    private UserRepository userRepository;
    private ExceptionUnwrapper exceptionUnwrapper;
    
    @Autowired
    public ProjectServiceImpl(
    		ProjectRepository projectRepository,
    		AddressRepository addressRepository,
    		TaskRepository taskRepository,
    		UserService userService,
    		EstimationService estimationService,
    		UserRepository userRepository,
    		ExceptionUnwrapper exceptionUnwrapper,
    		ClientRepository clientRepository,
    		TaskToTOConverter taskToTOConverter
    		) {
        this.projectRepository = projectRepository;
        this.addressRepository = addressRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectEntityToTOConverter = new ProjectEntityToTOConverter();
        this.projectEntityToStubConverter = new ProjectEntityToStubConverter();
        this.taskToTOConverter = taskToTOConverter;
        this.estimationService = estimationService;
        this.userRepository = userRepository;
        this.exceptionUnwrapper = exceptionUnwrapper;
        this.clientRepository = clientRepository;
    }


    @Override
    public List<ProjectTO> getAllProjects(String username) {
    	List<ProjectTO> projectTOList = new LinkedList<>();
    	UUID userId = userService.getUserId(username);
    	
    	if (userId != null) {
    		List<ProjectEntity> projectEntityList = projectRepository.findAllByUserEntityId(userId);
        
    		if (projectEntityList != null) {
                for (ProjectEntity projectEntity : projectEntityList) {
                    ProjectTO projectTO = projectEntityToTOConverter.convertToTO(projectEntity);
                    List<TaskTO> taskTOList = taskToTOConverter.convertListToTOList(taskRepository.findByProject_Id(projectEntity.getId()));
                    projectTO.setTaskTOS(taskTOList);
                    projectTO.setApproximateEndDate(estimationService.estimateProjectEndDate(projectTO));

                    projectTOList.add(projectTO);
                }
            }
    	}
        
        return projectTOList;
    }

    @Override
    public List<ProjectStubTO> getAllProjectsStubs(String username) {
    	UUID userId = userService.getUserId(username);
    	List<ProjectStubTO> projectStubs = new ArrayList<>();
    	
    	if (userId != null) {
    		List<ProjectEntity> projectEntityList = projectRepository.findAllByUserEntityId(userId);
    		if (projectEntityList != null) {
    			projectStubs = projectEntityToStubConverter.convertListToTOList(projectEntityList);
    		} 
    	}
     
        return projectStubs;
    }

    @Override
    public List<ProjectStubTO> getAllProjectStubsFromClient(String username, Long clientId) {
    	UUID userId = userService.getUserId(username);
    	List<ProjectStubTO> projectStubsWithClientId = new ArrayList<>();
    	
    	if (userId != null) {
    		List<ProjectEntity> projectEntityList = projectRepository.findAllByUserEntityIdAndClientId(userId, clientId);
    		if (projectEntityList != null) {
    			projectStubsWithClientId = projectEntityToStubConverter.convertListToTOList(projectEntityList);
    		} 
    	}
        
        return projectStubsWithClientId;
    }

    @Override
    public ProjectTO getProject(String username, Long projectId) {
    	ProjectTO projectTO = null;
        final ProjectEntity projectEntity = projectRepository.findOne(projectId);
        if (projectEntity != null) {
        	if (projectEntity.getUserEntity().getUsername().equals(username)) {
        		projectTO = projectEntityToTOConverter.convertToTO(projectEntity);
        	}	
        }
        return projectTO;
    }


	@Override
	public void addProject(String username, ProjectTO projectTO) throws ProjectServiceException {
		
		try {
			Assert.notNull(projectTO, "Project cannot be empty!");
			
			AddressEntity address = getAddressEntity(projectTO);
			Date startDate = getStartDate(projectTO);
			String projectName = getProjectName(projectTO);
			UserEntity user = getUser(username);
			
			ProjectEntity project = new ProjectEntity();
			project.setAddress(address);
			project.setStartDate(startDate);
			project.setProjectName(projectName);
			project.setUserEntity(user);
			
			addressRepository.saveAndFlush(project.getAddress());
			projectRepository.saveAndFlush(project);
		} catch (Exception e) {
			String errorMessage = exceptionUnwrapper.getExceptionMessage(e);
			throw new ProjectServiceException(errorMessage);
		}
	}	
	
	@Override
	public void editProject(Long projectId, ProjectTO projectTO) throws ProjectServiceException {
		ProjectEntity projectEntity = projectRepository.findById(projectId);
		
		if (projectEntity == null) {
			throw new ProjectServiceException("Project not found");
		}
		
		try {
			
			AddressEntity addressFromTO = projectTO.getAddress();
			if (addressFromTO != null) {
				AddressEntity address = addressRepository.findById(projectEntity.getAddress().getId());
				address.setStreet(addressFromTO.getStreet());
				address.setHouseNumber(addressFromTO.getHouseNumber());
				address.setApartmentNumber(addressFromTO.getApartmentNumber());
				address.setPostCode(addressFromTO.getPostCode());
				address.setCity(addressFromTO.getCity());
				address.setCountry(addressFromTO.getCountry());
				addressRepository.saveAndFlush(address);
				projectEntity.setAddress(address);
			}
			
			Date startDate = getStartDate(projectTO);
			String projectName = getProjectName(projectTO);
			projectEntity.setStartDate(startDate);
			projectEntity.setProjectName(projectName);
			
			projectRepository.saveAndFlush(projectEntity);
		} catch (Exception e) {
			String errorMessage = exceptionUnwrapper.getExceptionMessage(e);
			throw new ProjectServiceException(errorMessage);
		}
	}
	
	@Override
	public void updateClient(Long projectId, Long clientId) throws ProjectServiceException {
		ProjectEntity projectEntity = projectRepository.findById(projectId);
		if (projectEntity == null) {
			throw new ProjectServiceException("Project not found!");
		}
		
		ClientEntity client = clientRepository.findById(clientId);
		
		if (client == null) {
			throw new ProjectServiceException("Client not found!");
		}
		
		projectEntity.setClient(client);
		
		projectRepository.saveAndFlush(projectEntity);
	}
	
	@Override
	public void deleteProject(Long projectId) {
		ProjectEntity project = projectRepository.findById(projectId);
		if (project != null) {
			taskRepository.deleteByProject(project);
			projectRepository.deleteById(projectId);
		}
	}
	
	private UserEntity getUser(String username) {
		String errorMsg = "Unexpected error - no logged in user!";
		Assert.notNull(username, errorMsg);
		UserEntity user = userRepository.findByUsername(username);
		Assert.notNull(user, errorMsg);
		return user;
	}


	private String getProjectName(ProjectTO projectTO) {
		String projectName = projectTO.getProjectName();
		final String errorMsg = "Project's name cannot be empty!";
		Assert.notNull(projectName, errorMsg);
		Assert.isTrue(projectName.length() > 0, errorMsg);
		return projectName;
	}


	private Date getStartDate(ProjectTO projectTO) {
		Date startDate = projectTO.getStartDate();
		final String errorMsg = "Project's start date cannot be empty!";
		Assert.notNull(startDate, errorMsg);
		return startDate;
	}

	private AddressEntity getAddressEntity(ProjectTO projectTO) {
		AddressEntity address = projectTO.getAddress();
		final String errorMsg = "Address cannot be empty!";
		Assert.notNull(address, errorMsg);
		return address;
	}
}
