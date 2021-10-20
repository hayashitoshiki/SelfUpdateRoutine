package com.myapp.domain.dto

import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password

/**
 * 認証系InputDto
 *
 * @property email メールアドレス
 * @property password パスワード
 */
data class AuthInputDto(val email: Email, val password: Password)