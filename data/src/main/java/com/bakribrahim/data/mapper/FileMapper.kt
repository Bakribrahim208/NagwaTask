package com.bakribrahim.data.mapper


import com.bakribrahim.data.model.FileModel
 import com.bakribrahim.domain.entity.FileEntity
import com.bakribrahim.domain.entity.FileState


fun FileModel.map():FileEntity =FileEntity(id, name, type,url)
