package ru.otus.redirect.rule.checker

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.otus.checker.Checker
import java.io.File
import java.net.URLClassLoader
import java.util.*

@RestController("/api/v1")
class CheckerController {

    @PostMapping("/checkers")
    fun upload(@RequestParam("jar") jarFile: MultipartFile) {
        val tempFile = File.createTempFile("checker-plugin", ".jar")
        jarFile.transferTo(tempFile)
        val classLoader = URLClassLoader(arrayOf(tempFile.toURI().toURL()), Checker::class.java.classLoader)
        val serviceLoader = ServiceLoader.load(Checker::class.java, classLoader)
        val checker = serviceLoader.findFirst().orElseThrow {
            IllegalArgumentException("No Checker implementation found in JAR")
        }
        CheckerRegistry.register(checker)
    }
}