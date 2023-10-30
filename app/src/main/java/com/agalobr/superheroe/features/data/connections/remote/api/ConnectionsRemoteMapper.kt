package com.agalobr.superheroe.features.data.connections.remote.api

import com.agalobr.superheroe.features.domain.Connections

fun ConnectionsApiModel.toDomain() = Connections(this.groupAffiliation)
