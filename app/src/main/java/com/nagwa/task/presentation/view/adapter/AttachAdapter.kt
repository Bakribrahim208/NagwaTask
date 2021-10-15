package com.nagwa.task.presentation.view.adapter


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bakribrahim.domain.entity.FileEntity
import com.bakribrahim.domain.entity.FileState
import com.nagwa.task.R
import com.nagwa.task.databinding.ItemAttachmentBinding
import kotlinx.android.synthetic.main.item_attachment.view.*


class AttachAdapter(
    val onDownloadFile: (FileEntity) -> Unit
) : RecyclerView.Adapter<AttachAdapter.ViewHolder>() {

    var items: List<FileEntity> = emptyList()

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_attachment,
            parent,
            false
        ) as ItemAttachmentBinding
        context = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items.get(holder.adapterPosition)
        holder.binding.model = item
        handleFileState(holder,item.state)
        holder.binding.imgDownload.setOnClickListener {
            if (item.state is FileState.Idle) {
                holder.binding.imgDownload.isEnabled = false
                onDownloadFile(item)
            }
        }
        holder.binding.imgRetry.setOnClickListener {
            if (item.state is FileState.Failure) {
                onDownloadFile(item)
            }
        }
    }


    private fun handleFileState(holder: ViewHolder,state:FileState)
    {
        when (state) {
            is FileState.FileDownloaded -> bindDownloadedFile(holder)
            is FileState.DownLoading -> bindDownloadingFile(holder)
            is FileState.Failure -> bindFailureFile(holder)
            is FileState.Idle -> bindFileIdle(holder)
        }

    }
    private fun bindDownloadedFile(holder: ViewHolder) {
        holder.binding.imgDone.isVisible = true
        holder.binding.layoutLoader.isVisible = false
        holder.binding.imgDownload.isVisible = false
        holder.binding.imgRetry.isVisible = false
    }

    private fun bindFileIdle(holder: ViewHolder) {
        holder.binding.imgDone.isVisible = false
        holder.binding.layoutLoader.isVisible = false
        holder.binding.imgDownload.isVisible = true
        holder.binding.imgRetry.isVisible = false
    }

    private fun bindDownloadingFile(holder: ViewHolder) {
        holder.binding.imgDone.isVisible = false
        holder.binding.layoutLoader.isVisible = true
        holder.binding.imgDownload.isVisible = false
        holder.binding.imgRetry.isVisible = false
    }

    private fun bindFailureFile(holder: ViewHolder) {
        holder.binding.imgDone.isVisible = false
        holder.binding.layoutLoader.isVisible = false
        holder.binding.imgDownload.isVisible = false
        holder.binding.imgRetry.isVisible = true
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        var item = items.get(holder.adapterPosition)
        handleFileState(holder,item.state)

        if (payloads.firstOrNull() != null) {
            with(holder.itemView) {

                var bundle = payloads.first() as Bundle
                val progress = bundle.getInt("progress")
                if (progress != -1) {
                    pro_downlaod.progress = progress
                    tv_percentage.text = "$progress%"
                } else {
                    pro_downlaod.isIndeterminate = progress == -1

                }
            }
        }
    }


    fun setProgress(fileEntity: FileEntity) {
        var model = getFile(fileEntity)

        notifyItemChanged(items.indexOf(model), Bundle().apply {
            putInt("progress", fileEntity.progress)
        })
    }

    private fun getFile(fileEntity: FileEntity) = items.find { fileEntity.id == it.id }

    fun setList(list: List<FileEntity>) {
        this.items = list
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ItemAttachmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}
