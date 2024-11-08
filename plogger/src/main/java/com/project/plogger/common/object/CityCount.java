package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.repository.resultset.CityPostCountResultSet;

import lombok.Getter;

@Getter
public class CityCount {
    private String city;
    private Integer postCount;

    private CityCount(CityPostCountResultSet resultSet) {
        this.city = resultSet.getCity();
        this.postCount = resultSet.getPostCount();
    }

    public static List<CityCount> getList(List<CityPostCountResultSet> resultSets) {

        List<CityCount> list = new ArrayList<>();
        for (CityPostCountResultSet resultSet: resultSets) {
            CityCount cityCount = new CityCount(resultSet);
            list.add(cityCount);
        }

        return list;

    }
} 
