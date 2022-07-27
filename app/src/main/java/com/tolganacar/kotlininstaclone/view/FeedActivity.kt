package com.tolganacar.kotlininstaclone.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tolganacar.kotlininstaclone.R
import com.tolganacar.kotlininstaclone.adapter.PostAdapter
import com.tolganacar.kotlininstaclone.databinding.ActivityFeedBinding
import com.tolganacar.kotlininstaclone.model.Post

class FeedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var postAdapter : PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList = ArrayList<Post>()

        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(postArrayList)
        binding.recyclerView.adapter = postAdapter
    }

    private fun getData(){
        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if(value != null){
                    if(!value.isEmpty){
                        val documents = value.documents

                        postArrayList.clear()

                        for(document in documents){
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val post = Post(userEmail,comment,downloadUrl)
                            postArrayList.add(post)
                        }

                        postAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.insta_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_post){
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }else if (item.itemId == R.id.signout){
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}