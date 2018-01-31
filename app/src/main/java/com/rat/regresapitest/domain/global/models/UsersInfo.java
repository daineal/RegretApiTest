package com.rat.regresapitest.domain.global.models;

import java.util.List;

public class UsersInfo {

    private Integer page;
    private Integer perPage;
    private Integer total;
    private Integer totalPages;
    private List<UserData> data = null;

    public UsersInfo() {
    }

    public UsersInfo(Integer page, Integer perPage, Integer total, Integer totalPages, List<UserData> data) {

        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.totalPages = totalPages;
        this.data = data;

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }

    public static class Builder {
        private Integer page;
        private Integer perPage;
        private Integer total;
        private Integer totalPages;
        private List<UserData> data = null;

        public Builder setPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder setPerPage(Integer perPage) {
            this.perPage = perPage;
            return this;
        }

        public Builder setTotal(Integer total) {
            this.total = total;
            return this;
        }

        public Builder setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder setData(List<UserData> data) {
            this.data = data;
            return this;
        }

        public UsersInfo build() {
            return new UsersInfo(page, perPage, total, totalPages, data);
        }

    }

}