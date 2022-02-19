package com.likethesalad.tools.resource.locator.android.extension.configuration.data

import com.likethesalad.tools.resource.locator.android.providers.FileProvider
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import java.io.File

class DirectoryFileProvider(private val directoryProvider: Provider<Directory>) : FileProvider {

    override fun getFile(): File {
        return directoryProvider.get().asFile
    }
}