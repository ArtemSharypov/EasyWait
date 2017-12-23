package com.artem.easywait

import java.util.*

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
}