package com.example.klasha.util;

import com.example.klasha.api.models.dto.CityRecordDto;
import com.example.klasha.api.models.app.response.PopulationCityResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {
    public static List<PopulationCityResponse> filterPopulation(List<CityRecordDto> cityRecord, Long noOfRecords){
        Collections.sort(cityRecord, Comparator.comparingLong(recordOne -> Long.parseLong(recordOne.getPopulationCounts().get(0).getValue())));
        Collections.reverse(cityRecord);
        List<PopulationCityResponse> cityList = cityRecord.stream().map(cityRec -> new PopulationCityResponse(
                cityRec.getCity(), cityRec.getCountry(), cityRec.getPopulationCounts().get(0)
        )).toList();
        List<PopulationCityResponse> limitItems = new ArrayList<>();
        Long noOfItemToGet = cityList.size() > noOfRecords ? noOfRecords : cityList.size();
        for(int i = 0; i < noOfItemToGet; i++) {
            limitItems.add(cityList.get(i));
        }
        return limitItems;
//        Collections.sort(cityList, Comparator.comparingLong(recordOne -> Long.parseLong(recordOne.getPopulationCounts().getValue())));
//        Collections.reverse(cityList);

//        Collections.sort(cityRecord, Comparator.comparingLong(recordOne -> Long.parseLong(recordOne.getPopulationCounts().get(0).getValue())));
//        Collections.reverse(cityRecord);
//        return cityRecord;
//        List<CityRecord> idList = cityRecord.stream().map(cityRec -> new CityRecord(
//                cityRec.getCity(), cityRec.getCountry(), cityRec.getPopulationCounts()
//        )).toList();
    }
}
