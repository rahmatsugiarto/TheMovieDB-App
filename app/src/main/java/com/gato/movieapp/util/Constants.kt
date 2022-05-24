package com.gato.movieapp.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1a753405b7ab04170179b658d5e6bc4e"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val DEFAULT_STARTING_PAGE_INDEX = 1
        const val DEFAULT_QUERY_LANGUAGE = "en-US"

        //API QUERY Keys
        const val QUERY_API_KEY = "api_key"
        const val QUERY_LANGUAGE = "language"


        const val TYPE_MOVIE = "movie"
        const val TYPE_TV = "tv"


        //ROOM DB
        const val DB_NAME = "movie_db"
        const val TRENDING_MOVIE_TABLE = "trending_movie_table"
        const val POPULAR_MOVIE_TABLE = "popular_movie_table"
        const val TOP_RATED_MOVIE_TABLE = "top_rated_movie_table"
        const val UP_COMING_MOVIE_TABLE = "up_coming_movie_table"
        const val FAVORITES_MOVIE_TABLE = "favorites_movie_table"
        const val TRENDING_MOVIE_SA_TABLE = "trending_movie_see_all_table"
        const val POPULAR_MOVIE_SA_TABLE = "popular_movie_see_all_table"
        const val TOP_RATED_MOVIE_SA_TABLE = "top_rated_movie_see_all_table"
        const val TRENDING_MOVIE_REMOTE_KEY_TABLE = "trending_movie_remote_keys_table"
        const val POPULAR_MOVIE_REMOTE_KEY_TABLE = "popular_movie_remote_keys_table"
        const val TOP_RATED_MOVIE_REMOTE_KEY_TABLE = "top_rated_movie_remote_keys_table"

        const val TRENDING_TV_TABLE = "trending_tv_table"
        const val POPULAR_TV_TABLE = "popular_tv_table"
        const val TOP_RATED_TV_TABLE = "top_rated_tv_table"
        const val FAVORITES_TV_TABLE = "favorites_tv_table"
        const val TRENDING_TV_SA_TABLE = "trending_tv_see_all_table"
        const val POPULAR_TV_SA_TABLE = "popular_tv_see_all_table"
        const val TOP_RATED_TV_SA_TABLE = "top_rated_tv_see_all_table"
        const val TRENDING_TV_REMOTE_KEY_TABLE = "trending_tv_remote_keys_table"
        const val POPULAR_TV_REMOTE_KEY_TABLE = "popular_tv_remote_keys_table"
        const val TOP_RATED_TV_REMOTE_KEY_TABLE = "top_rated_tv_remote_keys_table"

        //SEE ALL TYPE
        const val TRENDING_MOVIE_TYPE = "Trending Movies"
        const val POPULAR_MOVIE_TYPE = "Popular Movies"
        const val TOP_RATED_MOVIE_TYPE = "Top Rated Movies"

        const val TRENDING_TV_TYPE = "Trending TV Shows"
        const val POPULAR_TV_TYPE = "Popular TV Shows"
        const val TOP_RATED_TV_TYPE = "Top Rated TV Shows"


        const val DEFAULT_SEARCH_TYPE = "movie"
        const val PREFERENCES_NAME = "preferences"
        const val PREFERENCES_BACK_ONLINE = "backOnline"
        const val PREFERENCES_SEARCH_TYPE = "searchType"
        const val PREFERENCES_SEARCH_TYPE_ID = "searchTypId"

    }
}