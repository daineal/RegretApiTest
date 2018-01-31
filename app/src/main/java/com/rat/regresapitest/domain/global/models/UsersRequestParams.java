package com.rat.regresapitest.domain.global.models;

public class UsersRequestParams {

    private int page;
    private int pageSize;

    public UsersRequestParams(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

}
