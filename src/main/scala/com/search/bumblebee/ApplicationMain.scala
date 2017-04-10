package com.search.bumblebee

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.jdbc.{DataSourceAutoConfiguration, DataSourceTransactionManagerAutoConfiguration}
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}

/**
  * Created by songyaheng on 2017-04-07.
  */
object ApplicationMain extends App {
  SpringApplication.run(classOf[Application])
  println("service start now!")
}

@SpringBootApplication
@EnableAutoConfiguration(exclude = Array(classOf[DataSourceAutoConfiguration],
  classOf[DataSourceTransactionManagerAutoConfiguration],
  classOf[HibernateJpaAutoConfiguration]))
class Application