package com.training.user.mapper;

import com.training.user.domain.User;
import com.training.user.dto.UserDto;
import com.training.user.enums.UserType;
import com.training.user.web.resources.UserRequest;
import com.training.user.web.resources.UserResponse;
import com.training.user.web.resources.UserTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class UserMapper {

    private static final String MAP_USER_TYPE = "mapUserType";

    public abstract UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    public abstract User toEntity(UserDto course);

    @Mapping(target = "userType", source = "userTypeId", qualifiedByName = MAP_USER_TYPE)
    public abstract UserResponse toUserResponse(UserDto user);

    @Mapping(target = "id", ignore = true)
    public abstract UserDto toUserDto(UserRequest user);

    @Named(MAP_USER_TYPE)
    UserTypeResponse toUserType(final Short userTypeId) {
        final String description = UserType.getName(userTypeId);
        return new UserTypeResponse(userTypeId, description);
    }

}
