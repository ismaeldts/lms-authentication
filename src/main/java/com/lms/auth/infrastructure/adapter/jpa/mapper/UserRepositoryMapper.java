package com.lms.auth.infrastructure.adapter.jpa.mapper;

import com.lms.auth.domain.model.UserModel;
import com.lms.auth.infrastructure.adapter.jpa.entity.UserEntity;
import com.lms.auth.shared.EntityToDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserRepositoryMapper extends EntityToDto<UserModel, UserEntity> {

}
