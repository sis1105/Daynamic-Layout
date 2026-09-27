package com.example.daynamiclayout

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val allFriends = mutableListOf(
        Friend("K UH U", "1 mutual friend", "1h", R.drawable.avatar_1),
        Friend("Kaberi Das", "0 mutual friends", "6w", R.drawable.avatar_1),
        Friend("Manorama Dinda", "1 mutual friend", "4d", R.drawable.avatar_1),
        Friend("Snehasis Maj", "18 mutual friends", "23w", R.drawable.avatar_1),
        Friend("Babu Sona", "2 mutual friends", "15w", R.drawable.avatar_1),
        Friend("তোর মন পাড়ায়", "23 mutual friends", "5w", R.drawable.avatar_1),
        Friend("Sri Ja", "4 mutual friends", "2d", R.drawable.avatar_1)
    )

    private lateinit var adapter: FriendAdapter
    private lateinit var friendList: MutableList<Friend>
    private lateinit var requestCount: TextView
    private lateinit var searchBox: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        requestCount = findViewById(R.id.txtRequestCount)
        searchBox = findViewById(R.id.searchBox)

        friendList = allFriends.toMutableList()

        val recyclerView =
            findViewById<RecyclerView>(R.id.recyclerFriends)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        adapter = FriendAdapter(friendList) { friend, confirmed ->

            val message = if (confirmed) {
                "${friend.name} added as friend"
            } else {
                "${friend.name} request deleted"
            }

            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            ).show()

            updateCount()
        }

        recyclerView.adapter = adapter

        // Search button
        findViewById<TextView>(R.id.btnSearch).setOnClickListener {

            searchBox.visibility =
                if (searchBox.visibility == View.VISIBLE)
                    View.GONE
                else
                    View.VISIBLE
        }

        // Search
        searchBox.setOnEditorActionListener { _, _, _ ->

            filterFriends(searchBox.text.toString())

            true
        }

        // Suggestions
        findViewById<TextView>(R.id.btnSuggestions)
            .setOnClickListener {

                Toast.makeText(
                    this,
                    "Suggestions selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // Your friends
        findViewById<TextView>(R.id.btnYourFriends)
            .setOnClickListener {

                Toast.makeText(
                    this,
                    "Your friends selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

        updateCount()
    }

    private fun filterFriends(query: String) {

        friendList.clear()

        if (query.isBlank()) {

            friendList.addAll(allFriends)

        } else {

            friendList.addAll(
                allFriends.filter {
                    it.name.contains(
                        query,
                        ignoreCase = true
                    )
                }
            )
        }

        adapter.notifyDataSetChanged()
    }

    private fun updateCount() {

        requestCount.text =
            allFriends.size.toString()
    }
}