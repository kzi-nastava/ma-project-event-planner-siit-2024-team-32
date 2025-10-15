package com.example.eventplanner.dto;

import androidx.annotation.NonNull;

import com.example.eventplanner.model.Service;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Page {
    @SerializedName("content")
    @Expose
    private ArrayList<?> content;
    @SerializedName("pageable")
    @Expose
    private Object pageable;
    @SerializedName("last")
    @Expose
    private Boolean last;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("totalElements")
    @Expose
    private Integer totalElements;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("sort")
    @Expose
    private Object sort;
    @SerializedName("numberOfElements")
    @Expose
    private Integer numberOfElements;
    @SerializedName("first")
    @Expose
    private Boolean first;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("empty")
    @Expose
    private Boolean empty;

    public Page() {
    }

    public Page(ArrayList<Service> content, Object pageable, Boolean last, Integer totalPages, Integer totalElements, Integer number, Object sort, Integer numberOfElements, Boolean first, Integer size, Boolean empty) {
        this.content = content;
        this.pageable = pageable;
        this.last = last;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.number = number;
        this.sort = sort;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.size = size;
        this.empty = empty;
    }

    public ArrayList<?> getContent() {
        return content;
    }

    public void setContent(ArrayList<?> content) {
        this.content = content;
    }

    public Object getPageable() {
        return pageable;
    }

    public void setPageable(Object pageable) {
        this.pageable = pageable;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    @NonNull
    @Override
    public String toString() {
        return "Page{" +
                "content=" + content +
                ", pageable=" + pageable +
                ", last=" + last +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", number=" + number +
                ", sort=" + sort +
                ", numberOfElements=" + numberOfElements +
                ", first=" + first +
                ", size=" + size +
                ", empty=" + empty +
                '}';
    }
}
