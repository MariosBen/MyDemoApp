package com.benetatos.livepage_domain.di

import com.benetatos.livepage_domain.repository.LiveGamesRepository
import com.benetatos.livepage_domain.use_case.AddFavoriteLiveGameUseCase
import com.benetatos.livepage_domain.use_case.DeleteFavoriteLiveGameUseCase
import com.benetatos.livepage_domain.use_case.GetAllFavoriteLiveGamesUseCase
import com.benetatos.livepage_domain.use_case.GetLiveGamesUseCase
import com.benetatos.livepage_domain.use_case.LiveGamesAllUseCases
import com.benetatos.livepage_domain.use_case.LiveGamesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun providesGetLiveGamesUseCase(repository: LiveGamesRepository): GetLiveGamesUseCase {
        return GetLiveGamesUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun providesGetAllFavoriteLiveGamesUseCase(repository: LiveGamesRepository): GetAllFavoriteLiveGamesUseCase {
        return GetAllFavoriteLiveGamesUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun providesDeleteFavoriteLiveGameUseCase(repository: LiveGamesRepository): DeleteFavoriteLiveGameUseCase {
        return DeleteFavoriteLiveGameUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun providesGetAddFavoriteLiveGameUseCase(repository: LiveGamesRepository): AddFavoriteLiveGameUseCase {
        return AddFavoriteLiveGameUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun providesLiveGamesUseCase(
        getLiveGamesUseCase: GetLiveGamesUseCase,
        getAllFavoriteLiveGamesUseCase: GetAllFavoriteLiveGamesUseCase
    ): LiveGamesUseCase {
        return LiveGamesUseCase(getLiveGamesUseCase, getAllFavoriteLiveGamesUseCase)
    }

    @Provides
    @ViewModelScoped
    fun providesUseCases(
        getLiveGamesUseCase: GetLiveGamesUseCase,
        getAllFavoriteLiveGamesUseCase: GetAllFavoriteLiveGamesUseCase,
        addFavoriteLiveGameUseCase : AddFavoriteLiveGameUseCase,
        deleteFavoriteLiveGameUseCase : DeleteFavoriteLiveGameUseCase,
        liveGamesUseCase : LiveGamesUseCase
    ): LiveGamesAllUseCases {
        return LiveGamesAllUseCases(
            addFavoriteLiveGameUseCase = addFavoriteLiveGameUseCase,
            deleteFavoriteLiveGameUseCase = deleteFavoriteLiveGameUseCase,
            getAllFavoriteLiveGamesUseCase = getAllFavoriteLiveGamesUseCase,
            liveGamesUseCase = liveGamesUseCase,
            getLiveGamesUseCase = getLiveGamesUseCase
        )
    }

}
