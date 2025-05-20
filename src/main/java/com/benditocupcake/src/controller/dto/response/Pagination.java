package com.benditocupcake.src.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Pagination {

	private int page;
	private int pageSize;
	private long total;

	public Pagination() {
		this.page = 0;
		this.pageSize = 0;
		this.total = 0;
	}

	public Pagination(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
		this.total = 0;
	}
}
