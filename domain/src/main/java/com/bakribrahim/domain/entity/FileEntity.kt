package com.bakribrahim.domain.entity

data class FileEntity(
    val id: String,
    val name: String,
    val type: String,
    val url: String,
    var isLoading: Boolean = false,
    var progress:Int=0,
    var state: FileState=FileState.Idle()
)
sealed class FileState{
    class Idle() : FileState()
    class DownLoading () : FileState()
    class FileDownloaded() : FileState()
    class Failure() : FileState()

}