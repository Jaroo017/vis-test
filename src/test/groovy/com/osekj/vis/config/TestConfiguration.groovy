package com.osekj.vis.config

import com.osekj.vis.dataloader.TestDataLoader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.sql.DataSource

@Configuration
class TestConfiguration {

    @Bean
    TestDataLoader roleTestDataLoader(DataSource dataSource) {
        new TestDataLoader(dataSource)
    }
}
