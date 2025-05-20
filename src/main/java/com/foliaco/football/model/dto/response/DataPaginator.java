package com.foliaco.football.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataPaginator <T>{

    private T data;
    private int totalPages;
    private int currentPage;

}
