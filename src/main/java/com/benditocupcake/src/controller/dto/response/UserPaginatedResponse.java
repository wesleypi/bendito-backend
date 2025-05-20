package com.benditocupcake.src.controller.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPaginatedResponse {
    Pagination pagination;

    List<UserResponse> users;
}
