package com.curato.wallpapers.data.handler

import com.curato.wallpapers.domain.common.Result

/**
 * Handler interface — each concrete handler owns one operation type.
 * Req is covariant in input, Res is covariant in output.
 * All network errors are caught and wrapped in Result.Error.
 */
interface WallpaperHandler<in Req : WallpaperRequest, out Res> {
    val handlerType: WallpaperRequest.Type
    suspend fun handle(request: Req): Result<Res>
}
