package com.dekankilic.elasticsearch.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "items_index")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Setting(settingPath = "static/es-settings.json")// kendi analizlerimizin configlerini yapabileceğimizi söylemiştik. Onu Json olarak import ediyoruz. Eğer istersek bunları querylerimizin içerisinde de yapabilirdik.
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    private String id;

    @Field(name = "name", type = FieldType.Text, analyzer = "custom_index", searchAnalyzer = "customer_search")
    private String name;
    @Field(name = "price", type = FieldType.Double)
    private Double price;
    @Field(name = "brand", type = FieldType.Text, analyzer = "custom_index", searchAnalyzer = "customer_search")
    private String brand;
    @Field(name = "category", type = FieldType.Keyword)
    private String category;
}
