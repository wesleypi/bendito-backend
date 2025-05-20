package com.benditocupcake.src.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientPaginatedResponse {
    private Pagination pagination;
    private List<ClientResponse> clients = new ArrayList<>();
}