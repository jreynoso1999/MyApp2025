package com.example.myapp2025.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2025.data.models.Person
import com.example.myapp2025.databinding.ItemPersonBinding

class PersonAdapter(
    private var personList: List<Person>,
    private val onEdit: (Person) -> Unit,
    private val onDelete: (Person) -> Unit
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.textName.text = person.nombres
            binding.textLastname.text = person.apellidos


            binding.buttonEdit.setOnClickListener {
                onEdit(person)
            }

            binding.buttonDelete.setOnClickListener {
                onDelete(person)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(inflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int = personList.size

    fun updateList(newList: List<Person>) {
        personList = newList
        notifyDataSetChanged()
    }
}

