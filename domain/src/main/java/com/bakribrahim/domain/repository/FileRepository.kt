package com.bakribrahim.domain.repository

 import com.bakribrahim.domain.entity.FileEntity
 import io.reactivex.Observable


interface FileRepository {

      fun  getFiles(): List<FileEntity>

      fun downloadFile(fileEntity: FileEntity)  : Observable<FileEntity>

}