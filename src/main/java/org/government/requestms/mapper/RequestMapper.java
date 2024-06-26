package org.government.requestms.mapper;

import org.government.requestms.dto.request.RequestDto;
import org.government.requestms.dto.response.RequestResponse;
import org.government.requestms.entity.Request;
import org.government.requestms.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public abstract class RequestMapper {
    @Mapping(target = "status", expression = "java(createStatus())")
    public abstract Request mapToEntity(RequestDto requestDto, String categoryName);

    public Status createStatus() {
        return Status.GÖNDƏRİLDİ;

    }


    @Mapping(target = "status", ignore = true)
    public abstract Request mapToUpdateEntity(@MappingTarget Request oldRequest, RequestDto requestDto, String categoryName);

    public abstract List<RequestResponse> mapToDtoList(List<Request> requestList);
}
