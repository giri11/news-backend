package com.newsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponseDTO<T> {
    String status;
    Long totalResults;
    Integer totalPages;
    List<T> data;
}
