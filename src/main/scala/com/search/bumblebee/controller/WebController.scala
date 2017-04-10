package com.search.bumblebee.controller

import org.springframework.stereotype.{Component, Controller}
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * Created by songyaheng on 2017-04-10.
  */
@Component
@Controller
@ResponseBody
class WebController {
  @RequestMapping(value = Array("/hello"))
  def hello(): String = {
    "hello spring scala"
  }
}
