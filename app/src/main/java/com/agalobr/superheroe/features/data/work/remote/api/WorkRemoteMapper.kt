package com.agalobr.superheroe.features.data.work.remote.api

import com.agalobr.superheroe.features.domain.Work

fun WorkApiModel.toDomain() = Work(this.occupation)