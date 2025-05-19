package ru.otus.redirect.rule.condition

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.otus.core.Condition
import java.io.File
import java.net.URLClassLoader
import java.util.*

@RestController
@RequestMapping("/api/v1")
class ConditionController {

    @PostMapping("/condition-upload")
    fun upload(@RequestParam("jar") jarFile: MultipartFile) {
        val tempFile = File.createTempFile("condition-plugin", ".jar")
        jarFile.transferTo(tempFile)
        val classLoader = URLClassLoader(arrayOf(tempFile.toURI().toURL()), Condition::class.java.classLoader)
        val serviceLoader = ServiceLoader.load(Condition::class.java, classLoader)
        val condition = serviceLoader.findFirst().orElseThrow {
            IllegalArgumentException("No Condition implementation found in JAR")
        }
        ConditionRegistry.register(condition)
    }
}