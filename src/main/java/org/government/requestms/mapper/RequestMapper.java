package org.government.requestms.mapper;

import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponseForAdmin;
import org.government.requestms.dto.response.RequestResponseForUser;
import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class RequestMapper {
    @Mapping(target = "status", expression = "java(createStatus())")
    @Mapping(target = "lastModified", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Request mapToEntity(RequestDto requestDto);

    public Status createStatus() {
        return Status.GÖNDƏRİLDİ;

    }

    public abstract List<RequestResponseForAdmin> mapToDtoListAdmin(List<Request> requestList);

    public abstract List<RequestResponseForUser> mapToDtoListUser(List<Request> requestList);

    @Mapping(target = "status", expression = "java(createStatus())")
    @Mapping(target = "lastModified", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createDate", ignore = true)
    public abstract Request mapToUpdateEntity(@MappingTarget Request oldRequest, RequestDto requestDto);
}
