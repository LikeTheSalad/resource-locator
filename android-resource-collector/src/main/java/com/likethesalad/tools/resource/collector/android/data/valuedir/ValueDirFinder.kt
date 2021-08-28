package com.likethesalad.tools.resource.collector.android.data.valuedir

import com.likethesalad.tools.resource.api.android.environment.Language
import com.likethesalad.tools.resource.collector.android.data.resdir.ResDir
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValueDirFinder @Inject constructor() {

    companion object {
        private val VALUES_FOLDER_NAME_REGEX = Regex("values(-([a-z]{2}(-r[A-Z]{2})*))*")
    }

    fun findValueDirs(resDir: ResDir): List<ValueDir> {
        val valueFolders = resDir.dir.listFiles { _, name ->
            isValueName(name)
        }?.filter { it.isDirectory } ?: emptyList<File>()

        return valueFolders.map {
            ValueDir(resDir, it, getLanguageForValuesFolder(it))
        }
    }

    private fun getLanguageForValuesFolder(folder: File): Language {
        val suffix = getValuesNameSuffix(folder.name)
        if (suffix.isEmpty()) {
            return Language.Default
        }
        return Language.Custom(suffix)
    }

    private fun isValueName(name: String): Boolean {
        return VALUES_FOLDER_NAME_REGEX.matches(name)
    }

    private fun getValuesNameSuffix(valuesFolderName: String): String {
        return VALUES_FOLDER_NAME_REGEX.find(valuesFolderName)!!.groupValues[2]
    }
}