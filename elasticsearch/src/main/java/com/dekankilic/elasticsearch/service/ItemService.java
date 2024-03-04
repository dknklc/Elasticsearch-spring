package com.dekankilic.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.dekankilic.elasticsearch.dto.SearchRequestDto;
import com.dekankilic.elasticsearch.model.Item;
import com.dekankilic.elasticsearch.repository.ItemRepository;
import com.dekankilic.elasticsearch.util.ESUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final JsonDataService jsonDataService;
    private final ElasticsearchClient elasticsearchClient; // tüm sorgularımızı bunun üzerinden yapacağız.

    public Item createIndex(Item item) {
        return itemRepository.save(item);
    }

    public void addItemsFromJson() {
        log.info("Adding items from json");
        List<Item> itemList = jsonDataService.readItemsFromJson();
        itemRepository.saveAll(itemList);
    }

    public List<Item> getAllDataFromIndex(String indexName) {
        var query = ESUtil.createMatchAllQuery();
        log.info("Elasticsearch query {}", query.toString());
        SearchResponse<Item> response = null;
        try {
            response = elasticsearchClient.search(
                    q -> q.index(indexName).query(query), Item.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Elasticsearch response {}", response);
        return response.hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public List<Item> searchItemsByFieldAndValue(SearchRequestDto searchRequestDto) {
        Supplier<Query> query = ESUtil.buildQueryForFieldAndValue(searchRequestDto.getFieldName().get(0), searchRequestDto.getSearchValue().get(0));
        log.info("Elasticsearch query {}", query.toString());
        SearchResponse<Item> response = null;
        try{
            response = elasticsearchClient.search(
                    q -> q.index("items_index").query(query.get()), Item.class
            );
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        log.info("Elasticsearch response {}", response);
        return response.hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());


    }

    public List<Item> searchItemsByNameAndBrandWithQuery(String name, String brand) {
        return itemRepository.searchByNameAndBrand(name, brand);
    }

    public List<Item> boolQuery(SearchRequestDto searchRequestDto) {
        var query = ESUtil.createBoolQuery(searchRequestDto);
        log.info("Elasticsearch query {}", query.toString());

        SearchResponse<Item> response = null;
        try{
            response = elasticsearchClient.search(
                    q -> q.index("items_index").query(query.get()), Item.class
            );
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        log.info("Elasticsearch response {}", response);
        return response.hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public Set<String> findSuggestedItemNames(String name) {
        Query query = ESUtil.buildAutoSuggestQuery(name);
        log.info("Elasticsearch query {}", query.toString());

        try {
            return elasticsearchClient.search(q -> q.index("items_index").query(query), Item.class)
                    .hits()
                    .hits()
                    .stream()
                    .map(Hit::source)
                    .map(Item::getName)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> autoSuggestItemsByNameWithQuery(String name) {
        List<Item> items = itemRepository.customAutocompleteSearch(name);
        log.info("Elasticsearch response: {}", items.toString());
        return items
                .stream()
                .map(Item::getName)
                .collect(Collectors.toList());
    }
}
