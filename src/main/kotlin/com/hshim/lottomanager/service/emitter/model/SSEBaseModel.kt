package com.hshim.lottomanager.service.emitter.model

import com.fasterxml.jackson.annotation.JsonIgnore

open class SSEBaseModel (
    @JsonIgnore
    val eventName: String,
)