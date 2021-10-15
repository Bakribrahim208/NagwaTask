package com.bakribrahim.domain.usecases

import com.bakribrahim.domain.entity.FileEntity
import com.bakribrahim.domain.repository.FileRepository
import javax.inject.Inject


class DownloadFileUseCase @Inject constructor(private val attachedRepository: FileRepository) {
    operator fun invoke(fileEntity: FileEntity) = attachedRepository.downloadFile(fileEntity)
}