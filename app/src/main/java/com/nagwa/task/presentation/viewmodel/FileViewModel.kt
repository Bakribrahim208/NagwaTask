package com.nagwa.task.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bakribrahim.domain.usecases.GetFilesUseCase
import com.bakribrahim.domain.entity.FileEntity
import com.bakribrahim.domain.usecases.DownloadFileUseCase
import com.nagwa.task.presentation.di.qualifier.IoScheduler
import com.nagwa.task.presentation.di.qualifier.MainScheduler
import javax.inject.Inject
 import io.reactivex.Scheduler

class FileViewModel @Inject constructor(
    private val getFilesUseCase: GetFilesUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    @IoScheduler private val ioScheduler:Scheduler,
    @MainScheduler  private val mainScheduler:Scheduler,

    ) :BaseViewModel() {

    val screenState by lazy { MutableLiveData<FilActivityState>() }

    fun getFiles() {
        screenState.postValue(FilActivityState.FilesLoaded(getFilesUseCase.invoke()))
    }

    fun downloadFile(fileEntity:FileEntity){
        val disposable = downloadFileUseCase.invoke(fileEntity)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .subscribe({
                if(it.isLoading)
                    screenState.postValue(FilActivityState.DownloadingFilActivity(fileEntity))
                else
                    screenState.postValue(FilActivityState.FilActivityDownLoaded(fileEntity))
            }, {
                screenState.postValue(FilActivityState.FilActivityDownLoadFailed("Oops! something went wrong"))
            })
        addDisposable(disposable)
    }
}



sealed class FilActivityState{
    object Loading : FilActivityState()
    class FilesLoaded(val files:List<FileEntity>) : FilActivityState()
    class Failure() : FilActivityState()

    class DownloadingFilActivity(val file:FileEntity) : FilActivityState()
    class FilActivityDownLoaded(val file:FileEntity) : FilActivityState()
    class FilActivityDownLoadFailed(val errorMsg:String) : FilActivityState()
}
