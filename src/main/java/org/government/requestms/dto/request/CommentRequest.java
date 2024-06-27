package org.government.requestms.dto.request;


import lombok.Getter;
import lombok.Setter;
import org.government.requestms.entity.Request;

@Getter
@Setter
public class CommentRequest {
    private String email;
    private String commentText;
    //private Request request;
}