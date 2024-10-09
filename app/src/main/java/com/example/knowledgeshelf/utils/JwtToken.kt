package com.example.knowledgeshelf.utils

import android.content.Context
import com.auth0.android.jwt.JWT
import com.example.knowledgeshelf.data.model.UserProfile

object JwtToken {

     fun getUserProfileFromToken(context: Context): UserProfile? {
        return try {
            val tokenManager = TokenManager(context)
            val accessToken = tokenManager.getAccessToken()

            accessToken?.let { token ->
                val jwt = JWT(token)
                val name = jwt.getClaim("fullName").asString() // Replace with your actual claims
                val email = jwt.getClaim("email").asString()
                val avatar = jwt.getClaim("avatar").asString()
                val role = jwt.getClaim("role").asString()

                UserProfile(fullName = name, email = email, avatar = avatar, role = role) // Create a UserProfile data class to hold this information
            }
        } catch (e: Exception) {
            null
        }
    }

}