package com.nikosnockoffs.android.travel_wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// another part of the program will implement the interface
interface OnListItemClickedListener {
    fun onListItemClicked(place: Place)
}


class PlaceRecyclerAdapter(private val places: List<Place>,
                           private val onListItemClickedListener: OnListItemClickedListener): // our class PlaceRecyclerAdapter is going to extend built-in RecyclerView.Adapter class
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() { // our adapter works with ViewHolders and we have to be specific about what types of ViewHolders

    // manages one view - or one list item - its actual data in the view
    // in Kotlin there are 2 types of inner classes
    // this is a nested class - self contained
    // there are also inner classes - pretty much the same as nested class except it can access code in containing class
    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) { // View is superclass of all components of things you can show in a layout
        // this will put the correct data in the view component
        fun bind(place: Place) { // this is a string now, but could easily be an object
            // function to handle connecting view components with data that needs to go into them
            val placeNameTextView: TextView = view.findViewById(R.id.place_name)
            placeNameTextView.text = place.name

            // find datecreated textview and setting its text to the place object's date added
            val dateCreatedOnTextView: TextView = view.findViewById(R.id.date_place_added)
            val createdOnText = view.context.getString(R.string.created_on, place.formattedDate())
            dateCreatedOnTextView.text = createdOnText

            val mapIcon: ImageView = view.findViewById(R.id.map_icon) // reference to the map icon
            mapIcon.setOnClickListener {
                onListItemClickedListener.onListItemClicked(place) // because we changed this to an inner class
            }
        }
    }

    // to be a PlaceRecycler Handler/Adapter you have to provide certain functionality
    // RecyclerView will call these methods, we don't call them directly
    // we provide this class with the functions, tell this class to use our instance of the RecyclcerAdapter
    // and the Recycler View will call/use these functions

    // called by the recycler view to create as many view holders that are needed to display list on screen
    // can this bind view holder with data for a specific position
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // returns a ViewHolder
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_list_item, parent, false)
    // inflate has 3 arguments(layout name, container of layout, and attach to root boolean - should this be in a static place in a view?)
        return ViewHolder(view) // create viewholder for that view
    }

    // called by the recycler view to set the data for one list item, by position
    // can this create a viewHolder for a specific position? (combo of view + data)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // where are you in the list
        // called by recycler view, once we've made a ViewHolder in above function, can we set up data in position noted
        val place = places[position] // element position
        holder.bind(place) // this class will create holders to give to recyclerview,
                           // and recyclerview will determine where to display them in order
    }

    // e.g. how does it tell recyclerview how many items are in the list?
    override fun getItemCount(): Int {
        return places.size
    }
}