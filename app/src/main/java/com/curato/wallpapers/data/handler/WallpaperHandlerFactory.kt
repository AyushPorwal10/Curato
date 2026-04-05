package com.curato.wallpapers.data.handler

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory that resolves the correct WallpaperHandler for a given request type.
 *
 * Uses Hilt multibindings — handlers are injected as a Map<Type, Handler>.
 * MVP2: add new handler class + one @IntoMap binding in HandlerModule. Nothing else changes.
 */
@Singleton
class WallpaperHandlerFactory @Inject constructor(
    private val handlers: Map<WallpaperRequest.Type, @JvmSuppressWildcards WallpaperHandler<*, *>>,
) {
    @Suppress("UNCHECKED_CAST")
    fun <Req : WallpaperRequest, Res> getHandler(
        requestType: WallpaperRequest.Type,
    ): WallpaperHandler<Req, Res> =
        (handlers[requestType] ?: error("No handler registered for $requestType"))
            as WallpaperHandler<Req, Res>
}
