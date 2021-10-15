package com.bakribrahim.domain.usecases

import com.bakribrahim.domain.repository.FileRepository
import javax.inject.Inject


class GetFilesUseCase @Inject constructor(private val attachedRepository: FileRepository) {
      operator fun invoke() = attachedRepository.getFiles()
}