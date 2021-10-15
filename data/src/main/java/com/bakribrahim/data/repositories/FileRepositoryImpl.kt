package com.bakribrahim.data.repositories

import com.bakribrahim.data.mapper.map
import com.bakribrahim.data.source.remote.DownloadFileRemoteDataSource
import com.bakribrahim.data.source.remote.DownloadFileRemoteDataSourceImpl
import com.bakribrahim.domain.entity.FileEntity
import com.bakribrahim.domain.repository.FileRepository
import io.reactivex.Observable
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
     private val downloadFileRemoteDataSource: DownloadFileRemoteDataSource
) : FileRepository {


    override fun getFiles():  List<FileEntity>  {
        return downloadFileRemoteDataSource.getFiles().map { it.map() }
    }


     override fun downloadFile(fileEntity: FileEntity) : Observable<FileEntity> {
        return downloadFileRemoteDataSource.downloadFile(fileEntity)
    }

}