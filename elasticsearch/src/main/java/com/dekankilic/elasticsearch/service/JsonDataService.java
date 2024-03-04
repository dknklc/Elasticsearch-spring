package com.dekankilic.elasticsearch.service;

import com.dekankilic.elasticsearch.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonDataService {
    private final ObjectMapper objectMapper;

    public List<Item> readItemsFromJson(){
        try {
            ClassPathResource resource = new ClassPathResource("data/items.json");
            InputStream inputStream = resource.getInputStream();
            return objectMapper.readValue(inputStream, new TypeReference<List<Item>>() {
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
