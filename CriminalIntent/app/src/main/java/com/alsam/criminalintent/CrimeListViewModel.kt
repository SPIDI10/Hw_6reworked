package com.alsam.criminalintent

import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID

class CrimeListViewModel : ViewModel() {

    val crimes = mutableListOf<Crime>()



    init {
        for (i in 0 until 100) {
            val requiresPolice = i % 3 == 0 // Example condition

            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Crime #$i",
                date = Date(),
                isSolved = i % 2 == 0,
                isSerious = i % 2 == 0,
                requiresPolice = requiresPolice // Set requiresPolice property based on condition
            )

            crimes += crime
        }
    }
}