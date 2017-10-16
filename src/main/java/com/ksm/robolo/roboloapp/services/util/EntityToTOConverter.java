package com.ksm.robolo.roboloapp.services.util;

import java.util.List;

public interface EntityToTOConverter<T, E> {

    String NULL_ARGUMENT_ERROR = "The entity to convert cannot be null!";

    T convertToTO(E entity);
    List<T> convertListToTOList(List<E> entityList);
}
