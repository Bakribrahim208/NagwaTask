package com.bakribrahim.data.source.remote

import com.bakribrahim.data.model.FileModel
import com.bakribrahim.domain.entity.FileEntity
import io.reactivex.Observable

interface DownloadFileRemoteDataSource {
    fun  getFiles(): List<FileModel>

    fun downloadFile(fileEntity: FileEntity)  : Observable<FileEntity>

}

