package com.lms.auth.domain.port;

import com.lms.auth.domain.model.UserModel;

public interface CreateUserModelPort {

  UserModel createUserModel(UserModel user);
}
