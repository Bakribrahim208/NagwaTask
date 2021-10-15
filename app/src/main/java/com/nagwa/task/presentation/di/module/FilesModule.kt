package com.nagwa.task.presentation.di.module

import com.bakribrahim.data.repositories.FileRepositoryImpl
import com.bakribrahim.data.source.remote.DownloadFileRemoteDataSource
import com.bakribrahim.data.source.remote.DownloadFileRemoteDataSourceImpl
import com.bakribrahim.domain.repository.FileRepository
import dagger.Module

@Module
interface FilesModule {

    @dagger.Binds
    fun bindDownloadFileRemoteDataSource(
        downloadFileRemoteDataSource: DownloadFileRemoteDataSourceImpl
    ): DownloadFileRemoteDataSource

    @dagger.Binds
    fun bindFileRepository(
        repo: FileRepositoryImpl
    ): FileRepository

}