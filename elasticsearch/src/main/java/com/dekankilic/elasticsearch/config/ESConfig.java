package com.dekankilic.elasticsearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.dekankilic.elasticsearch.repository")
@ComponentScan(basePackages = {"com.dekankilic.elasticsearch"})
public class ESConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.url}")
    private String url;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(url)
                /*.usingSsl("6ed41a2c102450cd60a8d976b4d4e646456172f3b806410b16b5f7f94d79ee76") # Open these if you are using Ssl security and BasicAuth for Elasticsearch.
                .withBasicAuth("elastic", "EJdfsHwoIDLu55s2C13c")                               # I mean if you are not disabling them in the docker-compose.yml.     */
                .build();
    }
}
