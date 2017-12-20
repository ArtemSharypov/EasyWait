package com.artem.easywait

class Reservation {

    var name: String
    var number: String?
    var numPeople: Int

    constructor(name: String, number: String?, numPeople: Int) {
        this.name = name
        this.number = number
        this.numPeople = numPeople
    }


}