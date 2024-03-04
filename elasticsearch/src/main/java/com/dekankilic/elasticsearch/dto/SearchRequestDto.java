package com.dekankilic.elasticsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private List<String> fieldName;
    private List<String> searchValue;
}
