package com.ksm.robolo.roboloapp.services.util.impl;

import com.ksm.robolo.roboloapp.domain.ClientEntity;
import com.ksm.robolo.roboloapp.services.util.EntityToTOConverter;
import com.ksm.robolo.roboloapp.tos.ClientTO;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class ClientToTOConverter implements EntityToTOConverter<ClientTO, ClientEntity> {
    // this class is porobably relevent
    @Override
    public ClientTO convertToTO(ClientEntity entity) {
        Assert.notNull(entity, NULL_ARGUMENT_ERROR);
        ClientTO clientTO = new ClientTO();
        clientTO.setId(entity.getId());
        clientTO.setName(entity.getName());
        clientTO.setSurname(entity.getSurname());
        clientTO.setTelephoneNumbers(entity.getTelephoneNumbers());
        clientTO.setEmailAddress(entity.getEmailAddress());
        return clientTO;
    }

    @Override
    public List<ClientTO> convertListToTOList(List<ClientEntity> entityList) {
        List<ClientTO> clientTOList = new ArrayList<>();
        if (entityList != null) {
            for (ClientEntity clientEntity : entityList) {
                clientTOList.add(this.convertToTO(clientEntity));
            }
        }
        return clientTOList;
    }
}
