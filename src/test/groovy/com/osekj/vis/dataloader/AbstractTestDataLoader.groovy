package com.osekj.vis.dataloader

import groovy.sql.Sql

import javax.sql.DataSource

abstract class AbstractTestDataLoader {

    protected DataSource dataSource
    protected Sql sql

    protected AbstractTestDataLoader(DataSource dataSource) {
        this.dataSource = dataSource
        this.sql = new Sql(dataSource)
    }
}
