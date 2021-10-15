package com.nagwa.task.presentation.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakribrahim.domain.entity.FileEntity
import com.nagwa.task.R
import com.nagwa.task.presentation.view.adapter.AttachAdapter
import com.nagwa.task.presentation.viewmodel.FilActivityState
import com.nagwa.task.presentation.viewmodel.FileViewModel
import com.nagwa.task.presentation.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


const val TAG = "MainActivityT"

class DownloadFileActivity : AppCompatActivity() {


     lateinit var attachmentAdapter: AttachAdapter

    @Inject
    lateinit var filesViewModelFactory:ViewModelFactory<FileViewModel>

    private val fileViewModel: FileViewModel by lazy {
        ViewModelProvider(this, filesViewModelFactory).get(FileViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        initViews()
        fileViewModel.getFiles()
        observeOnScreenState()
    }

    private fun initViews() {
        attachmentAdapter = AttachAdapter(::onDownloadFile)
    }

    private fun observeOnScreenState() {
        fileViewModel.screenState.observe(this, Observer {
            it?.let { state -> onScreenStateChanged(state) }
        })
    }

    private fun onScreenStateChanged(state: FilActivityState) {
        when (state) {
            is FilActivityState.FilesLoaded -> handleFilesLoadedState(state.files)
            is FilActivityState.DownloadingFilActivity -> handleDownloadingFileState(state.file)
            is FilActivityState.FilActivityDownLoaded -> handleFileDownLoadedState(state.file)
            is FilActivityState.FilActivityDownLoadFailed -> handleFileDownLoadFailedState(state.errorMsg)
        }
    }

    private fun handleFileDownLoadedState(file: FileEntity) {
        //TODO create function for file download complete in adapter
        attachmentAdapter.setProgress(file)
    }

    private fun handleDownloadingFileState(file: FileEntity) {
         attachmentAdapter.setProgress(file)

    }

    private fun handleFileDownLoadFailedState(errorMsg: String) {
        //TODO show error message
    }

    private fun handleFilesLoadedState(files: List<FileEntity>) {
        Log.d(TAG, "onCreate: ")
        Log.d(TAG, files.toString())

        attachmentAdapter.setList(files)

        rvAttachment.apply {
            adapter = attachmentAdapter
            layoutManager = LinearLayoutManager(this@DownloadFileActivity)
        }
    }

    fun onDownloadFile(item: FileEntity) {
        fileViewModel.downloadFile(item)
    }


}