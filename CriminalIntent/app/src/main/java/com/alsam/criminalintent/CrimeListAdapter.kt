package com.alsam.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alsam.criminalintent.databinding.ListItemCrimeBinding
import java.util.Date

class CrimeListAdapter(private val crimes: List<Crime>, private val thresholdDate: Date) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_NORMAL = 1
    private val VIEW_TYPE_SERIOUS = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)

        return when (viewType) {
            VIEW_TYPE_NORMAL -> NormalCrimeHolder(binding)
            VIEW_TYPE_SERIOUS -> SeriousCrimeHolder(binding)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]

        when (holder) {
            is NormalCrimeHolder -> holder.bind(crime)
            is SeriousCrimeHolder -> holder.bind(crime)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val crime = crimes[position]

        return when {
            crime.requiresPolice -> VIEW_TYPE_SERIOUS
            crime.isSerious -> VIEW_TYPE_SERIOUS
            else -> VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int = crimes.size

    inner class NormalCrimeHolder(private val binding: ListItemCrimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(crime: Crime) {
            binding.crimeTitle.text = crime.title

            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "${crime.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.crimeSolved.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    inner class SeriousCrimeHolder(private val binding: ListItemCrimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(crime: Crime) {
            binding.crimeTitle.text = "${crime.title} (Serious)"

            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "Serious: ${crime.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
