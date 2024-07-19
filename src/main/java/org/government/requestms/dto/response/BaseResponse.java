package org.government.requestms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private T data;
    private String message;
    private boolean success;

    @Builder
    public BaseResponse(String message, boolean success, T data) {
         this.message = message;
         this.success = success;
         this.data = data;
    }

    public static BaseResponse<String> fail(String message) {
        return BaseResponse.<String>builder()
                .message(message)
                .success(false)
                .build();
    }

    public static <T> BaseResponse<T> OK(T data) {
        return BaseResponse.<T>builder()
                .message("SUCCESS")
                .success(true)
                .data(data)
                .build();
    }

    public static BaseResponse<String> message(String message) {
        return BaseResponse.<String>builder()
                .message(message)
                .success(true)
                .build();
    }
}
