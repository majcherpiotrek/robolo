package com.ksm.robolo.roboloapp.services.util;

public interface EntityToStubConverter<S, E> {
    S convertToStub(E entity);
}
