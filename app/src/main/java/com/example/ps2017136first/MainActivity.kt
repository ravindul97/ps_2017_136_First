package com.example.ps2017136first

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ps2017136first.config.URL_API
import com.example.ps2017136first.model.user
import com.example.ps2017136first.services.UsersService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private fun initComponents() {
        val btnGetData = findViewById<Button>(R.id.btnGetData)
        btnGetData.setOnClickListener {
            this.getDetail()
        }
    }

    private fun getDetail() {
        val txtUserId = findViewById<EditText>(R.id.txtUserId)
        val txtName = findViewById<EditText>(R.id.txtName)
        val txtUsername = findViewById<EditText>(R.id.txtUsername)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtPhone = findViewById<EditText>(R.id.txtPhone)
        val txtAdStreet = findViewById<EditText>(R.id.txtAdStreet)
        val txtAdSuite = findViewById<EditText>(R.id.txtAdSuite)
        val txtAdCity = findViewById<EditText>(R.id.txtAdCity)
        val txtAdZip = findViewById<EditText>(R.id.txtAdZip)

        val retro = Retrofit.Builder()
            .baseUrl(URL_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retro.create(UsersService::class.java)
        val userRequest = service.getUser(txtUserId.text.toString())

        userRequest.enqueue(object: Callback<user> {
            override fun onResponse(call: Call<user>, response: Response<user>) {
                val user = response.body()

                if (user == null) {
                    Toast.makeText(this@MainActivity, "No Such User Found!", Toast.LENGTH_LONG).show()
                }
                else {
                    txtName.setText(user.name)
                    txtUsername.setText(user.username)
                    txtEmail.setText(user.email)
                    txtPhone.setText(user.phone)
                    txtAdStreet.setText(user.address.street)
                    txtAdSuite.setText(user.address.suite)
                    txtAdCity.setText(user.address.city)
                    txtAdZip.setText(user.address.zipcode)
                }
            }

            override fun onFailure(call: Call<user>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to Fetch.", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initComponents()
    }
}
