package com.example.klasha.services;

import com.example.klasha.api.clients.okhttp.OkHttpProcessing;
import com.example.klasha.api.models.app.response.CountryDetailResponse;
import com.example.klasha.api.models.app.response.CountryStateCityResponse;
import com.example.klasha.api.models.app.response.CurrencyConversionResponse;
import com.example.klasha.api.models.dto.*;
import com.example.klasha.api.models.thirdparty.request.CityRequest;
import com.example.klasha.api.models.thirdparty.request.PopulationFilterRequest;
import com.example.klasha.api.models.app.response.PopulationCityResponse;
import com.example.klasha.api.models.thirdparty.response.*;
import com.example.klasha.config.CountryProps;
import com.example.klasha.exceptions.NotFoundException;
import com.example.klasha.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;

@Slf4j
public class PopulationService {
    private final CountryProps countryProps;
    private final OkHttpProcessing okHttpProcessing;

    @Value("classpath:exchange_rate.csv")
    Resource resource;

    public static final String MONETARY_EXCHANGE_CACHE = "monetary_exchange_cache";
    ObjectMapper objectMapper = new ObjectMapper();

    public PopulationService(CountryProps countryProps, OkHttpProcessing okHttpProcessing) {
        this.countryProps = countryProps;
        this.okHttpProcessing = okHttpProcessing;
    }

    public List<PopulationCityResponse> filterPopulation(Long noOfRecord){

        try {
            PopulationFilterRequest nigPayload = PopulationFilterRequest.builder()
                    .country(countryProps.getCountries().get(0).toString()).build();
            PopulationFilterRequest ghPayload = PopulationFilterRequest.builder()
                    .country(countryProps.getCountries().get(1).toString()).build();
            PopulationFilterRequest newZPayload = PopulationFilterRequest.builder()
                    .country(countryProps.getCountries().get(2).toString()).build();

            String nigeriaPayload = objectMapper.writeValueAsString(nigPayload); // "{\n\t\"country\": \"nigeria\"\n}";
            String ghanaPayload = objectMapper.writeValueAsString(ghPayload);
            String newZealandPayload = objectMapper.writeValueAsString(newZPayload);

            String path = "/population/cities/filter";

            CompletableFuture<String> c1 = okHttpProcessing.post(nigeriaPayload, path);
            CompletableFuture<String> c2 = okHttpProcessing.post(ghanaPayload, path);
            CompletableFuture<String> c3 = okHttpProcessing.post(newZealandPayload, path);

            CompletableFuture.allOf(c1,c2,c3).join();

            List<CityRecordDto> allRecord = new ArrayList<>();
            PopulationFilterResponse cObject = null;
            cObject = objectMapper.readValue(c1.get(), PopulationFilterResponse.class);
            allRecord.addAll(cObject.getData());
            cObject = objectMapper.readValue(c2.get(), PopulationFilterResponse.class);
            allRecord.addAll(cObject.getData());
            cObject = objectMapper.readValue(c3.get(), PopulationFilterResponse.class);
            allRecord.addAll(cObject.getData());
            return Utils.filterPopulation(allRecord, noOfRecord) ;
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public CountryDetailResponse getCountryDetails(String country){

        try {
            String path = "/population";

            PopulationFilterRequest payLoad = PopulationFilterRequest.builder()
                    .country(country).build();
            String requestBody = objectMapper.writeValueAsString(payLoad);

            // 1. check country exist
            CompletableFuture<String> populationOne = okHttpProcessing.post(requestBody, path);
            CountryPopulationResponse populationOneObject = objectMapper.readValue(populationOne.get(), CountryPopulationResponse.class);
            if(populationOneObject.getError()){
                throw new NotFoundException(new Object[]{country});
            }

            // 1. get country population
            CompletableFuture<String> population = okHttpProcessing.post(requestBody, path);
            // 2. get city capital
            path = "/capital";
            CompletableFuture<String> capital = okHttpProcessing.post(requestBody, path);
            // 3. get location
            path = "/positions";
            CompletableFuture<String> location = okHttpProcessing.post(requestBody, path);
            // 4. get currency
            path = "/currency";
            CompletableFuture<String> currency = okHttpProcessing.post(requestBody, path);

            CompletableFuture.allOf(population,capital,location,currency ).join();


            CountryPopulationResponse populationObject = objectMapper.readValue(population.get(), CountryPopulationResponse.class);
            CityCapitalResponse capitalObject = objectMapper.readValue(capital.get(), CityCapitalResponse.class);
            LocationResponse locationObject = objectMapper.readValue(location.get(), LocationResponse.class);
            CurrencyResponse currencyObject = objectMapper.readValue(currency.get(), CurrencyResponse.class);

            CountryDetailResponse response = new CountryDetailResponse();
            List<PopulationRecordDto> popRec = populationObject.getData().getPopulationCounts();
            response.setPopulation(Long.parseLong(popRec.get(popRec.size() - 1).getValue()));

            response.setCapitalCity(capitalObject.getData().getCapital());
            response.setIso2(capitalObject.getData().getIso2());
            response.setIso3(capitalObject.getData().getIso3());

            response.setCurrency(currencyObject.getData().getCurrency());

            CountryLocationDto countryLocationDto = new CountryLocationDto();
            countryLocationDto.setLatitude(locationObject.getData().getLat());
            countryLocationDto.setLongitude(locationObject.getData().getLongitude());

            response.setLocation(countryLocationDto);

            return response;


        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public CountryStateCityResponse getCountryState(String country){

        try {
            PopulationFilterRequest payload = PopulationFilterRequest.builder()
                    .country(country).build();

            String path = "/states";
            // 1. get all the states in a country
            String requestBody = objectMapper.writeValueAsString(payload);
            CompletableFuture<String> state = okHttpProcessing.post(requestBody, path);
            CountryStateResponse stateObject = objectMapper.readValue(state.get(), CountryStateResponse.class);

            if(stateObject.getError()){
                throw new NotFoundException(new Object[]{country});
            }

            //2. get the cities in a state
            List<CompletableFuture<String>> allCities = new ArrayList<>();
            CompletableFuture<String> city = null;
            List<StateDto> statesRecords = stateObject.getData().getStates();
            List<String> stateList = new ArrayList<>();
            String cityPath = "/state/cities";
            // generate completablefuture async for each state
            for (int i = 0; i < statesRecords.size(); i++) {
                stateList.add(statesRecords.get(i).getName());
                CityRequest cityRequest = CityRequest.builder().country(country).state(statesRecords.get(i).getName()).build();
                String cityRequestBody = objectMapper.writeValueAsString(cityRequest);
                city = okHttpProcessing.post(cityRequestBody, cityPath);
                allCities.add(city);
            }

            // execute all completablefuture
            CompletableFuture<Void> resultantCf = CompletableFuture.allOf(allCities.toArray(new CompletableFuture[allCities.size()]));
            CompletableFuture<List<String>> allFutureResults = resultantCf.thenApply(t -> allCities.stream().map(CompletableFuture::join).collect(Collectors.toList()));

            CountryStateCityResponse countryStateCityResponse = new CountryStateCityResponse();
            countryStateCityResponse.setCountry(stateObject.getData().getName());
            List<StateCity> listOfStateCity = new ArrayList<>();
            StateCity stateCity = null;
            List<String> resolveFutureItems = allFutureResults.get();
            for (int j = 0; j < resolveFutureItems.size(); j++) {
                CityResponse cityResponse = objectMapper.readValue(resolveFutureItems.get(j),CityResponse.class);
                stateCity = new StateCity();
                stateCity.setState(stateList.get(j));
                stateCity.setCity(cityResponse.getData());
                listOfStateCity.add(stateCity);
            }
            countryStateCityResponse.setStateCity(listOfStateCity);
            return countryStateCityResponse;
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public CurrencyConversionResponse convertCurrency(CountryDetailsRequestDto countryDetailsRequestDto){

        try {
            PopulationFilterRequest payload = PopulationFilterRequest.builder()
                    .country(countryDetailsRequestDto.getCountry()).build();

            String path = "/currency";
            // 1. get all the states in a country
            String requestBody = objectMapper.writeValueAsString(payload);
            CompletableFuture<String> currencyRecord = okHttpProcessing.post(requestBody, path);
            CurrencyResponse stateObject = objectMapper.readValue(currencyRecord.get(), CurrencyResponse.class);

            if(stateObject.getError()){
                throw new NotFoundException(new Object[]{countryDetailsRequestDto.getCountry()});
            }

            List<MonetaryExchangeDto> forwardConversion = this.getBankExchangeSetting().stream()
                    .filter(p -> ((p.getSourceCountry().equalsIgnoreCase(stateObject.getData().getCurrency())) &&
                            (p.getTargetCountry().equalsIgnoreCase(countryDetailsRequestDto.getTargetCurrency())))).collect(Collectors.toList());

            List<MonetaryExchangeDto> backwardConversion = this.getBankExchangeSetting().stream()
                    .filter(p -> ((p.getSourceCountry().equalsIgnoreCase(countryDetailsRequestDto.getTargetCurrency())) &&
                            (p.getTargetCountry().equalsIgnoreCase(stateObject.getData().getCurrency())))).collect(Collectors.toList());

            if(forwardConversion.size()>0 || backwardConversion.size()>0){
                DecimalFormat df = new DecimalFormat("###.##");
                String targetCurrency = null;
                Double convertValue = null;
                if(backwardConversion.size()>0){
                    MonetaryExchangeDto monetaryExchangeDto =backwardConversion.get(0);
                    convertValue = countryDetailsRequestDto.getAmount() / monetaryExchangeDto.getRate();
                    targetCurrency = monetaryExchangeDto.getSourceCountry();
                }else{
                    MonetaryExchangeDto monetaryExchangeDto =forwardConversion.get(0);
                    convertValue = countryDetailsRequestDto.getAmount() * monetaryExchangeDto.getRate();
                    targetCurrency = monetaryExchangeDto.getTargetCountry();
                }
                CurrencyConversionResponse currencyConversionResponse = new CurrencyConversionResponse();
                currencyConversionResponse.setAmount(Double.parseDouble(df.format(convertValue)));
                currencyConversionResponse.setCurrency(targetCurrency);

                return currencyConversionResponse;
            }

            throw new NotFoundException(new Object[]{"[No exchange record found for conversion]"});
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Cacheable(value = MONETARY_EXCHANGE_CACHE, sync = true)
    public List<MonetaryExchangeDto> getBankExchangeSetting(){
        try {
            List<MonetaryExchangeDto> monetaryData  = new ArrayList<>();
            InputStream input = resource.getInputStream();
            File file = resource.getFile();
            CSVReader csvReader = new CSVReader(new FileReader(file));
            String[] values = null;
            MonetaryExchangeDto monetaryExchangeDto = null;
            int i = 0;
            while ((values = csvReader.readNext()) != null) {
                if(i > 0){
                    monetaryExchangeDto = new MonetaryExchangeDto();
                    monetaryExchangeDto.setSourceCountry(values[0]);
                    monetaryExchangeDto.setTargetCountry(values[1]);
                    monetaryExchangeDto.setRate(Double.parseDouble(values[2]));
                    monetaryData.add(monetaryExchangeDto);
                }
                i+=1;
            }
            return monetaryData;
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
