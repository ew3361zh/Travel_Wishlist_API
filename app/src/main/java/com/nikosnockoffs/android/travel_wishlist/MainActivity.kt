package com.nikosnockoffs.android.travel_wishlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnListItemClickedListener, OnDataChangedListener { // MainActivity will be an activity and an onListItemClickedListener

    private lateinit var newPlaceEditText: EditText
    private lateinit var addNewPlaceButton: Button
    private lateinit var placeListRecyclerView: RecyclerView
    private lateinit var reasonToVisitEditText: EditText

    // make reference to adapterView
    private lateinit var placesRecyclerAdapter: PlaceRecyclerAdapter

    private lateinit var wishListContainer: View

    private val placesViewModel: PlacesViewModel by lazy {
        ViewModelProvider(this).get(PlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeListRecyclerView = findViewById(R.id.place_list)
        addNewPlaceButton = findViewById(R.id.add_new_place_button)
        newPlaceEditText = findViewById(R.id.new_place_name)
        reasonToVisitEditText = findViewById(R.id.reason_to_visit)
        wishListContainer = findViewById(R.id.wishlist_container)

        // gets list of places from view model for use here
        // based on function we had to set up in viewmodel
//        val places = placesViewModel.getPlaces() // places is a list of place objects

        // see PlacesViewModel for why this is set up this way
        placesRecyclerAdapter = PlaceRecyclerAdapter(listOf(), this) // placerecycleradapter expects a string so we need to change to an object
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

        val itemSwipeListenerListener = OnListItemSwipeListener(this)
        ItemTouchHelper(itemSwipeListenerListener).attachToRecyclerView(placeListRecyclerView)

        addNewPlaceButton.setOnClickListener {
            addNewPlace()
        }

        placesViewModel.allPlaces.observe(this) { places ->
            placesRecyclerAdapter.places = places
            placesRecyclerAdapter.notifyDataSetChanged()
        }

        placesViewModel.userMessage.observe(this) { message ->
            if (message != null) {
                Snackbar.make(wishListContainer, message, Snackbar.LENGTH_LONG).show()
            }
        }

    }

    private fun addNewPlace() {
        // adding a new place means adding it to the viewmodel
        val name = newPlaceEditText.text.toString().trim()
        val reason = reasonToVisitEditText.text.toString().trim() // get text from new field
        if (name.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_place_name_entered_warning), Toast.LENGTH_SHORT).show()
        } else if (reason.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_reason_warning), Toast.LENGTH_SHORT).show()
        } else {
            val newPlace = Place(name, reason)
            val positionAdded = placesViewModel.addNewPlace(newPlace)
//            if (positionAdded == -1) {
//                Toast.makeText(this, getString(R.string.dupe_place_warning), Toast.LENGTH_SHORT).show()
//            } else {
//                placesRecyclerAdapter.notifyItemInserted(positionAdded) // tell adapter where it's been added
                clearForm()
                hideKeyboard()
//            }
        }
    }

    private fun clearForm() {
        newPlaceEditText.text.clear()
        reasonToVisitEditText.text.clear()
    }

    private fun hideKeyboard() {
        this.currentFocus?.let { view -> // where is the current focus of the user entering data
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager // is it a soft keyboard (screen keyboard)
            imm?.hideSoftInputFromWindow(view.windowToken, 0) // this closes the keyboard
        }
    }

    override fun onMapRequestButtonClicked(place: Place) {
        Toast.makeText(this, getString(R.string.map_icon_clicked_confirmation), Toast.LENGTH_SHORT).show()
        val placeLocationUri = Uri.parse("geo:0,0?q=${place.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
        startActivity(mapIntent)
    }

    override fun onStarredStatusChanged(place: Place, isStarred: Boolean) {
        place.starred = isStarred
        placesViewModel.updatePlace(place)
    }

//    override fun onListItemMoved(from: Int, to: Int) {
//        placesViewModel.movePlace(from, to)
//        placesRecyclerAdapter.notifyItemMoved(from, to)
//    }

    override fun onListItemDeleted(position: Int) {
        val place = placesRecyclerAdapter.places[position]
        placesViewModel.deletePlace(place)

//        placesRecyclerAdapter.notifyItemRemoved(position) // notify recyclerAdapter

//        Snackbar.make(findViewById(R.id.wishlist_container), getString(R.string.place_deleted, deletedPlace.name), Snackbar.LENGTH_LONG)
//            .setActionTextColor(resources.getColor(R.color.delete_message))
//            .setBackgroundTint(resources.getColor(R.color.black))
//            .setAction(getString(R.string.undo_place_deleted)) { // display an undo button
//                placesViewModel.addNewPlace(deletedPlace, position) // this restores deleted place to viewmodel
//                placesRecyclerAdapter.notifyItemInserted(position) // tells the view model place was brought back
//            }
//            .show()
    }
}