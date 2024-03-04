package com.dekankilic.elasticsearch.controller;

import com.dekankilic.elasticsearch.dto.SearchRequestDto;
import com.dekankilic.elasticsearch.model.Item;
import com.dekankilic.elasticsearch.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // TODO createIndex
    @PostMapping
    public Item createIndex(@RequestBody Item item){
        return itemService.createIndex(item);
    }

    // TODO addItemsFromJson
    @PostMapping("/init-index")
    public void addItemsFromJson(){
        itemService.addItemsFromJson();
    }

    // TODO getAllDataFromIndex
    @GetMapping("/getAllDataFromIndex/{indexName}")
    public List<Item> getAllDataFromIndex(@PathVariable String indexName){
        return itemService.getAllDataFromIndex(indexName);
    }

    // TODO searchItemsByFieldAndValue
    @GetMapping("/search")
    public List<Item> searchItemsByFieldAndValue(@RequestBody SearchRequestDto searchRequestDto){
        return itemService.searchItemsByFieldAndValue(searchRequestDto);
    }

    // TODO searchItemsByNameAndBrandWithQuery
    @GetMapping("/search/{name}/{brand}")
    public List<Item> searchItemsByNameAndBrandWithQuery(@PathVariable String name, @PathVariable String brand){
        return itemService.searchItemsByNameAndBrandWithQuery(name, brand);
    }

    // TODO boolQuery
    @GetMapping("/boolQuery")
    public List<Item> boolQuery(@RequestBody SearchRequestDto searchRequestDto){
        return itemService.boolQuery(searchRequestDto);
    }

    // TODO autoSuggestItemsByName
    @GetMapping("autoSuggest/{name}")
    public Set<String> autoSuggestItemsByName(@PathVariable String name){
        return itemService.findSuggestedItemNames(name);
    }

    // TODO autoSuggestItemsByNameWithQuery
    @GetMapping("/suggestionsQuery/{name}")
    public List<String> autoSuggestItemsByNameWithQuery(@PathVariable String name) {
        return itemService.autoSuggestItemsByNameWithQuery(name);
    }

}
