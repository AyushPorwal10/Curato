package com.curato.wallpapers.domain.wallpaper

import com.curato.wallpapers.data.repository.WallpaperRepository
import com.curato.wallpapers.domain.common.PaginatedResult
import com.curato.wallpapers.domain.common.Result
import com.curato.wallpapers.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchWallpapersUseCase @Inject constructor(
    private val repository: WallpaperRepository,
) {
    operator fun invoke(queries: Flow<String>): Flow<Result<PaginatedResult<Wallpaper>>> =
        queries
            .debounce(400)
            .distinctUntilChanged()
            .flatMapLatest { query -> flow { emit(repository.search(query)) } }
}
