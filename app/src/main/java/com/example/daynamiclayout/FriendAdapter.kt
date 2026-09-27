package com.example.daynamiclayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendAdapter(
    private val friends: MutableList<Friend>,
    private val onAction: (Friend, Boolean) -> Unit
) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    class FriendViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val avatar: ImageView =
            view.findViewById(R.id.imgProfile)

        val name: TextView =
            view.findViewById(R.id.txtName)

        val mutual: TextView =
            view.findViewById(R.id.txtMutual)

        val confirm: Button =
            view.findViewById(R.id.btnConfirm)

        val delete: Button =
            view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_friend_request,
                parent,
                false
            )

        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FriendViewHolder,
        position: Int
    ) {

        val friend = friends[position]

        holder.avatar.setImageResource(
            friend.avatar
        )

        holder.name.text =
            friend.name

        holder.mutual.text =
            "${friend.mutualFriends} • ${friend.time}"

        // Confirm
        holder.confirm.setOnClickListener {

            val currentPosition =
                holder.bindingAdapterPosition

            if (currentPosition !=
                RecyclerView.NO_POSITION
            ) {

                val selected =
                    friends.removeAt(currentPosition)

                notifyItemRemoved(
                    currentPosition
                )

                onAction(
                    selected,
                    true
                )
            }
        }

        // Delete
        holder.delete.setOnClickListener {

            val currentPosition =
                holder.bindingAdapterPosition

            if (currentPosition !=
                RecyclerView.NO_POSITION
            ) {

                val selected =
                    friends.removeAt(currentPosition)

                notifyItemRemoved(
                    currentPosition
                )

                onAction(
                    selected,
                    false
                )
            }
        }
    }

    override fun getItemCount(): Int {

        return friends.size
    }
}