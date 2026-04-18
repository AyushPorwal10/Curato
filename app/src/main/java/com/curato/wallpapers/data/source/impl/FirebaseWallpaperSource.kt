package com.curato.wallpapers.data.source.impl

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.curato.wallpapers.data.source.SourceType
import com.curato.wallpapers.data.source.SourceWallpaperDto
import com.curato.wallpapers.data.source.SourceWallpapersPage
import com.curato.wallpapers.data.source.WallpaperSource
import com.curato.wallpapers.domain.model.WallpaperCategory
import kotlinx.coroutines.tasks.await
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseWallpaperSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) : WallpaperSource {

    override val sourceType = SourceType.FIREBASE

    /**
     * Cursor map for pagination.
     * Key   → unique query signature (e.g. "curated", "category:AMOLED", "search:dark")
     * Value → last [DocumentSnapshot] returned by that query; null = start from beginning.
     *
     * ConcurrentHashMap because ViewModels can trigger loads from different coroutines.
     * Resetting a cursor (page == 1) removes the key, so `startAfter` is never called.
     */
    private val cursors = ConcurrentHashMap<String, DocumentSnapshot>()

    // ── Collection reference ─────────────────────────────────────────────────

    private val wallpapersCol get() = firestore.collection(COLLECTION_WALLPAPERS)

    // ── Public API ───────────────────────────────────────────────────────────

    override suspend fun getCurated(page: Int, perPage: Int): SourceWallpapersPage {
        val key = "curated"
        return executePagedQuery(
            key = key,
            page = page,
            perPage = perPage,
            baseQuery = wallpapersCol
                .whereEqualTo(FIELD_IS_CURATED, true)
                .whereEqualTo(FIELD_IS_ACTIVE, true)
                .orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING),
        )
    }

    override suspend fun search(query: String, page: Int, perPage: Int): SourceWallpapersPage {
        val normalizedQuery = query.trim().lowercase()
        val key = "search:$normalizedQuery"
        // Firestore array-contains uses a single token; take the most meaningful word.
        val searchToken = normalizedQuery.split(" ").firstOrNull { it.length > 2 } ?: normalizedQuery
        return executePagedQuery(
            key = key,
            page = page,
            perPage = perPage,
            baseQuery = wallpapersCol
                .whereArrayContains(FIELD_TAGS, searchToken)
                .whereEqualTo(FIELD_IS_ACTIVE, true)
                .orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING),
        )
    }

    override suspend fun getByCategory(
        category: WallpaperCategory,
        page: Int,
        perPage: Int,
    ): SourceWallpapersPage {
        val key = "category:${category.firestoreKey}"
        return executePagedQuery(
            key = key,
            page = page,
            perPage = perPage,
            baseQuery = wallpapersCol
                .whereEqualTo(FIELD_CATEGORY, category.firestoreKey)
                .whereEqualTo(FIELD_IS_ACTIVE, true)
                .orderBy(FIELD_CREATED_AT, Query.Direction.DESCENDING),
        )
    }

    override suspend fun getById(id: String): SourceWallpaperDto {
        val snapshot = wallpapersCol.document(id).get().await()
        return snapshot.toSourceDto()
            ?: throw NoSuchElementException("Wallpaper '$id' not found in Firestore")
    }

    // ── Pagination core ──────────────────────────────────────────────────────

    private suspend fun executePagedQuery(
        key: String,
        page: Int,
        perPage: Int,
        baseQuery: Query,
    ): SourceWallpapersPage {
        // Page 1 always restarts from the beginning.
        if (page == 1) cursors.remove(key)

        val pagedQuery = cursors[key]
            ?.let { baseQuery.startAfter(it) }
            ?: baseQuery

        val snapshot = pagedQuery.limit(perPage.toLong()).get().await()

        // Advance cursor only when we got results so we never overshoot.
        if (snapshot.documents.isNotEmpty()) {
            cursors[key] = snapshot.documents.last()
        }

        return SourceWallpapersPage(
            wallpapers = snapshot.documents.mapNotNull { it.toSourceDto() },
            page = page,
            // If the batch is full, assume there's a next page.
            hasNextPage = snapshot.documents.size >= perPage,
            totalResults = -1, // Firestore doesn't expose count without a dedicated query
        )
    }

    // ── Document → DTO ───────────────────────────────────────────────────────

    /**
     * Returns null (and logs nothing) for documents that are missing required fields.
     * This prevents one bad document from crashing the whole page.
     */
    private fun DocumentSnapshot.toSourceDto(): SourceWallpaperDto? {
        if (!exists()) return null
        return try {
            SourceWallpaperDto(
                id = id,
                title = getString(FIELD_TITLE) ?: return null,
                description = getString(FIELD_DESCRIPTION) ?: "",
                authorName = "Curato Studio",
                authorUrl = "",
                thumbnailUrl = getString(FIELD_THUMBNAIL_URL) ?: return null,
                previewUrl = getString(FIELD_PREVIEW_URL) ?: return null,
                fullUrl = getString(FIELD_FULL_URL) ?: return null,
                dominantColor = getString(FIELD_DOMINANT_COLOR) ?: "#000000",
                width = getLong(FIELD_WIDTH)?.toInt() ?: 0,
                height = getLong(FIELD_HEIGHT)?.toInt() ?: 0,
                category = getString(FIELD_CATEGORY),
                tags = (get(FIELD_TAGS) as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                sourceType = SourceType.FIREBASE,
            )
        } catch (_: Exception) {
            null
        }
    }

    // ── Field name constants — change once here if schema evolves ────────────

    private companion object {
        const val COLLECTION_WALLPAPERS = "wallpapers"

        const val FIELD_TITLE = "title"
        const val FIELD_DESCRIPTION = "description"
        const val FIELD_THUMBNAIL_URL = "thumbnailUrl"
        const val FIELD_PREVIEW_URL = "previewUrl"
        const val FIELD_FULL_URL = "fullUrl"
        const val FIELD_DOMINANT_COLOR = "dominantColor"
        const val FIELD_WIDTH = "width"
        const val FIELD_HEIGHT = "height"
        const val FIELD_CATEGORY = "category"
        const val FIELD_TAGS = "tags"
        const val FIELD_IS_CURATED = "isCurated"
        const val FIELD_IS_ACTIVE = "isActive"
        const val FIELD_CREATED_AT = "createdAt"
    }
}
