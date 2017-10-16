package com.ksm.robolo.roboloapp.services;
import com.ksm.robolo.roboloapp.tos.ProjectTO;

import java.util.Date;
public interface EstimationService {

    Date estimateProjectEndDate(ProjectTO projectTO);
}
