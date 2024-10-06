package com.example.knowledgeshelf.utils

import android.content.Context
import com.auth0.android.jwt.JWT

class JwtToken {

    private fun getUserInfoFromJwt(token: String){
        return try {
            val jwt = JWT(token)

            // Extract user details from the token payload
            val userId = jwt.getClaim("_id").asString()
            val fullName = jwt.getClaim("fullName").asString()
            val email = jwt.getClaim("email").asString()
            val role = jwt.getClaim("role").asString()

        } catch (e: Exception) {
            // Handle any error that might occur while decoding
            println("Token decoding failed: ${e.localizedMessage}")
        }
    }



}