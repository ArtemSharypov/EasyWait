package com.artem.easywait

import java.util.*
import kotlin.collections.HashMap

class Reservation {

    var name: String
    var number: String
    var numPeople: Int
    var arrivalTimeHours: Int
    var arrivalTimeMinutes: Int

    constructor(name: String, number: String, numPeople: Int) {
        this.name = name
        this.number = number
        this.numPeople = numPeople

        var c = GregorianCalendar()

        this.arrivalTimeHours = c.get(Calendar.HOUR)
        this.arrivalTimeMinutes = c.get(Calendar.MINUTE)
    }

    constructor() {
        name = ""
        number = ""
        numPeople = 0
        arrivalTimeHours = 0
        arrivalTimeMinutes = 0
    }

    fun toHashMap(): HashMap<String, String> {
        var map =  HashMap<String, String>()

        map.put("name", name)
        map.put("number", number)
        map.put("num_people", numPeople.toString())
        map.put("arrival_time_hours", arrivalTimeHours.toString())
        map.put("arrival_time_minutes", arrivalTimeMinutes.toString())

        return map
    }

}