package com.benetatos.livepage_domain.use_case

import com.benetatos.livepage_domain.model.Sport
import com.benetatos.livepage_domain.repository.LiveGamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLiveGamesUseCase @Inject constructor (
    private val repository: LiveGamesRepository
) {

    operator fun invoke(): Flow<Result<List<Sport>>> = flow {
        emit(repository.getLiveGames())
    }
}