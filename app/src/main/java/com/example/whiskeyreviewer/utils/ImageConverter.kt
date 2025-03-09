package com.example.whiskeyreviewer.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.whiskeyreviewer.data.ImageData
import java.io.File
import java.io.FileOutputStream

//Android 10 이상에서는 저장소 접근 정책이 변경됨에 따라,
// 사용자가 선택한 파일의 URI에서 파일의 절대 경로를 얻어내는 것이 불가능해졌음.
// 따라서 불러온 이미지의 URI로부터 임시 파일로 복사본을 생성한 뒤,
// 해당 복사본을 서버에 업로드하는 방식으로 구현해야 한다.

object ImageConverter {

    // 임시 파일 생성
    private fun createTempFile(context: Context, fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    // 파일 내용 스트림 복사
    @SuppressLint("Recycle")
    private fun copyToFile(context: Context, uri: Uri, file: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(4 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }

        outputStream.flush()
    }

    // URI -> File 변환
    fun toFile(context: Context, uri: Uri): File {
        val fileName = getFileName(context, uri)
        val file = createTempFile(context, fileName)
        copyToFile(context, uri, file)
        return file
    }

    // 파일 이름 및 확장자 가져오기
    private fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)?.split("/")?.last() ?: "jpg"
        return "$name.$ext"
    }

    // URI 리스트를 파일 리스트로 변환
    fun convertUrisToFiles(context: Context, uriList: List<Uri>): List<File> {
        val fileList = mutableListOf<File>()
        Log.d("이미지 가공 전", uriList.toString())
        for (uri in uriList) {
            try {

                val file = toFile(context, uri)
                fileList.add(file)
            } catch (e: Exception) {
                Log.e("변환 실패", "파일 변환 실패: $uri", e)
            }
        }

        return fileList
    }

    fun convertUrisToFiles(context: Context, uri: Uri): File? {

        val file=try {

                toFile(context, uri)
            } catch (e: Exception) {
                Log.e("변환 실패", "파일 변환 실패: $uri", e)
                null
            }
        return file
    }

    fun castImageFile(imageData: ImageData): Any {
        return when (imageData) {
            is ImageData.StringData -> imageData.name
            is ImageData.ByteArrayData -> BitmapFactory.decodeByteArray(
                imageData.byteArray,
                0,
                imageData.byteArray.size
            )

            is ImageData.UriData -> TODO()
        }
    }

    fun byteArrayToCacheUri(context: Context, byteArrayList: List<ByteArray>?, fileNameList: List<String>?): List<Uri>? {

        if (byteArrayList.isNullOrEmpty()) return null

        val imageUrlList = mutableListOf<Uri>()

        for (i in byteArrayList.indices) {
            val byteArray = byteArrayList[i]
            val fileName = fileNameList?.getOrNull(i) ?: "image_$i.jpg"

            try {

                val file = File(context.cacheDir, fileName)
                FileOutputStream(file).use { fos ->
                    fos.write(byteArray)
                }

                val uri = Uri.fromFile(file)
                imageUrlList.add(uri)
            } catch (e: Exception) {
                Log.d("Uri 가져오기 실패", e.toString())
            }
        }

        return if (imageUrlList.isEmpty()) null else imageUrlList
    }

    fun clearCache(context: Context) {
        val cacheDir = context.cacheDir
        if (cacheDir.isDirectory) {
            val files = cacheDir.listFiles()
            files?.forEach { file ->
                file.delete()
            }
        }
    }
}