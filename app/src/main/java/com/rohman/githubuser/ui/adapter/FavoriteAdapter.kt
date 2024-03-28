package com.rohman.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohman.githubuser.data.response.ItemsItem
import com.rohman.githubuser.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import android.content.Intent
import com.rohman.githubuser.data.response.DetailResponseUser
import com.rohman.githubuser.ui.detail.DetailActivity
import com.rohman.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.annotation.SuppressLint

class FavoriteAdapter(private val listusers: ArrayList<ItemsItem>)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgUser = binding.imageView
        val tvName = binding.tvName
        val tvUsername = binding.tvUsername
        val tvBio= binding.tvBio

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int = listusers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listusers[position]
        Glide.with(holder.itemView.context).load(item.avatarUrl).into(holder.imgUser)
        holder.tvUsername.text=item.login
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER,listusers[holder.adapterPosition])
            holder.itemView.context.startActivity(intent) }

        val client = ApiConfig.getApiService().getDetailUser(item.login)

        client.enqueue(object : Callback<DetailResponseUser> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<DetailResponseUser>, response: Response<DetailResponseUser>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        holder.tvName.text = responseBody.name
                        holder.tvBio.text = "Bio: ${responseBody.bio}"

                    }
                }
            }
            override fun onFailure(call: Call<DetailResponseUser>, t: Throwable) {}
        })
    }
}