package com.lms.auth.shared;

public interface EntityToDto<E, O> {

  O toDTO(E e);

  E toEntity(O o);
}
