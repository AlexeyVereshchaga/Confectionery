package com.sun.confectionery.auth.domain.usecase

import com.sun.confectionery.auth.domain.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.login(email, password)
}