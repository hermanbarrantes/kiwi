package com.mihtan.kiwi.core

import org.testcontainers.utility.DockerImageName

/**
 *
 * @author herman
 */
interface SpockTestImages {
    DockerImageName MYSQL_IMAGE = DockerImageName.parse("mysql:5.7.34")
    DockerImageName POSTGRES_TEST_IMAGE = DockerImageName.parse("postgres:9.6.12")
}

