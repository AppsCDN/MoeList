package com.axiel7.moelist.rest

import com.axiel7.moelist.model.*
import retrofit2.Call
import retrofit2.http.*

interface MalApiService {

    // anime

    @GET("/v2/anime")
    fun getAnimeList(@Query("q") search: String,
                     @Query("limit") limit: Int?,
                     @Query("offset") offset: Int?,
                     @Query("nsfw") nsfw: Int?,
                     @Query("fields") fields: String?): Call<AnimeListResponse>

    @GET
    fun getSeasonalAnime(@Url url: String,
                         @Query("sort") sort: String,
                         @Query("fields") fields: String?,
                         @Query("limit") limit: Int?,
                         @Query("nsfw") nsfw: Int?): Call<SeasonalAnimeResponse>

    @GET("/v2/anime/ranking")
    fun getAnimeRanking(@Query("ranking_type") rankingType: String,
                        @Query("fields") fields: String?,
                        @Query("limit") limit: Int?,
                        @Query("nsfw") nsfw: Int?): Call<AnimeRankingResponse>

    @GET("/v2/anime/suggestions")
    fun getAnimeRecommend(@Query("limit") limit: Int?): Call<AnimeListResponse>

    @GET("/v2/users/@me/animelist")
    fun getUserAnimeList(@Query("status") status: String?,
                         @Query("fields") fields: String?,
                         @Query("sort") sort: String,
                         @Query("nsfw") nsfw: Int?): Call<UserAnimeListResponse>

    @GET
    fun getNextSeasonalPage(@Url url: String): Call<SeasonalAnimeResponse>
    @GET
    fun getNextAnimeRankingPage(@Url url: String): Call<AnimeRankingResponse>
    @GET
    fun getNextRecommendPage(@Url url: String): Call<AnimeListResponse>
    @GET
    fun getNextAnimeListPage(@Url url: String): Call<UserAnimeListResponse>

    @GET
    fun getAnimeDetails(@Url url: String, @Query("fields") fields: String): Call<AnimeDetails>


    //TODO (implement: is_rewatching, priority, num_times_rewatched, rewatch_value, tags, comments)
    @FormUrlEncoded
    @PATCH
    fun updateAnimeList(@Url url: String,
                        @Field("status") status: String?,
                        @Field("score") score: Int?,
                        @Field("num_watched_episodes") watchedEpisodes: Int?): Call<MyListStatus>

    @DELETE
    fun deleteEntry(@Url url: String): Call<Void>


    // manga

    @GET("/v2/manga")
    fun getMangaList(@Query("q") search: String,
                     @Query("limit") limit: Int?,
                     @Query("offset") offset: Int?,
                     @Query("nsfw") nsfw: Int?,
                     @Query("fields") fields: String?): Call<MangaListResponse>

    @GET
    fun getMangaDetails(@Url url: String, @Query("fields") fields: String): Call<MangaDetails>

    @GET("/v2/manga/ranking")
    fun getMangaRanking(@Query("ranking_type") rankingType: String,
                        @Query("fields") fields: String?,
                        @Query("nsfw") nsfw: Int?): Call<MangaRankingResponse>

    @GET("/v2/users/@me/mangalist")
    fun getUserMangaList(@Query("status") status: String?,
                         @Query("fields") fields: String?,
                         @Query("sort") sort: String,
                         @Query("nsfw") nsfw: Int?): Call<UserMangaListResponse>
    @GET
    fun getNextMangaListPage(@Url url: String): Call<UserMangaListResponse>
    @GET
    fun getNextMangaRankingPage(@Url url: String): Call<MangaRankingResponse>

    //TODO (implement: is_rereading, priority, num_times_reread, reread_value, tags, comments)
    @FormUrlEncoded
    @PATCH
    fun updateMangaList(@Url url: String,
                        @Field("status") status: String?,
                        @Field("score") score: Int?,
                        @Field("num_chapters_read") readChapters: Int?,
                        @Field("num_volumes_read") readVolumes: Int?): Call<MyMangaListStatus>

    // user
    @GET("/v2/users/@me")
    fun getUserInfo(@Query("fields") fields: String?): Call<User>
}