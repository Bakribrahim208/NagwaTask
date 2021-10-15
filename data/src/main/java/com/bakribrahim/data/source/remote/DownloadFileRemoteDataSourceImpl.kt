package com.bakribrahim.data.source.remote

import com.bakribrahim.data.fakedata.getFakeList
import com.bakribrahim.data.model.FileModel
import com.bakribrahim.domain.entity.FileEntity
import com.bakribrahim.domain.entity.FileState
import io.reactivex.Observable
import javax.inject.Inject

class DownloadFileRemoteDataSourceImpl @Inject constructor(
    private val downloadFileWithProgress: DownloadFileWithProgress
) : DownloadFileRemoteDataSource {

    override fun getFiles(): List<FileModel> {
        return getFakeList()
    }

    //if file not have content length will download it without progress
    // and send -1 as progress
    override fun downloadFile(fileEntity: FileEntity): Observable<FileEntity> {
        return Observable.create<FileEntity> { emitter ->

            var progressListener = object : DownloadFileWithProgress.ProgressListener {
                var firstUpdate = true
                override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                    if (done) {
                        println("completed")
                        fileEntity.state = FileState.FileDownloaded()
                        emitter.onNext(fileEntity)
                        emitter.onComplete()
                    } else {
                        if (firstUpdate) {
                            firstUpdate = false
                            if (contentLength == -1L) {
                                println("content-length: unknown")
                            } else {
                                System.out.format("content-length: %d\n", contentLength)
                            }
                        }
                        //println(bytesRead)
                        if (contentLength == -1L) {
                            fileEntity.progress = -1
                        } else {
                            var progress = 100 * bytesRead / contentLength
                            fileEntity.progress = progress.toInt()
                        }
                        fileEntity.state = FileState.DownLoading()
                        fileEntity.isLoading = done.not()
                        emitter.onNext(fileEntity)

                    }
                }

                override fun failure(errorMessage: String) {
                    fileEntity.state = FileState.Failure()

                    emitter.onNext(fileEntity)
                }
            }

            downloadFileWithProgress.progressListener = progressListener
            downloadFileWithProgress.invoke(fileEntity.url)

        }


    }


}