package io.github.kag0.petstore.payloads

import java.time.OffsetDateTime

case class TokenResponse(
    token: String,
    expiresAfter: OffsetDateTime,
    rateLimit: Int = 10
)
