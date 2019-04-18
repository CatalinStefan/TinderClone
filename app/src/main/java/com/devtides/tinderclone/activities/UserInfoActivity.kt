package com.devtides.tinderclone.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.devtides.tinderclone.R
import com.devtides.tinderclone.User
import com.devtides.tinderclone.util.DATA_USERS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val userId = intent.extras.getString(PARAM_USER_ID, "")
        if(userId.isNullOrEmpty()) {
            finish()
        }

        val userDatabase = FirebaseDatabase.getInstance().reference.child(DATA_USERS)
        userDatabase.child(userId).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)
                userInfoName.text = user?.name
                userInfoAge.text = user?.age
                if(user?.imageUrl != null) {
                    Glide.with(this@UserInfoActivity)
                        .load(user.imageUrl)
                        .into(userInfoIV)
                }
            }
        })
    }

    companion object {
        private val PARAM_USER_ID = "User id"

        fun newIntent(context: Context, userId: String?): Intent {
            val intent = Intent(context, UserInfoActivity::class.java)
            intent.putExtra(PARAM_USER_ID, userId)
            return intent
        }
    }
}
