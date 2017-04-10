package com.search.bumblebee.entites

import javax.persistence.{Entity, Id}

/**
  * Created by songyaheng on 2017-04-10.
  */
@Entity
class Location {

  @Id
  var id: Long = _
  var lat: Float = _
  var lon: Float = _
}
