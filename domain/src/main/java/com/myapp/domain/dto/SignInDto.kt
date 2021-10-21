package com.myapp.domain.dto

/**
 * ログイン用InputDto
 *
 * @property email メールアドレス
 * @property password パスワード
 */
data class SignInDto(val email: String, val password: String)