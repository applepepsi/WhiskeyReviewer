package com.example.whiskeyreviewer.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import com.example.whiskeyreviewer.data.ImageData
import com.example.whiskeyreviewer.data.UriData
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

//Android 10 이상에서는 저장소 접근 정책이 변경됨에 따라,
// 사용자가 선택한 파일의 URI에서 파일의 절대 경로를 얻어내는 것이 불가능해졌음.
// 따라서 불러온 이미지의 URI로부터 임시 파일로 복사본을 생성한 뒤,
// 해당 복사본을 서버에 업로드하는 방식으로 구현해야 한다.

object ImageConverter {

    private const val DEFAULT_IMAGE_QUALITY = 80


    // 임시 파일 생성
    private fun createTempFile(context: Context, fileName: String): File? {
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return null
        return File(storageDir, fileName)
    }

    // 파일 이름과 확장자 가져옴
    private fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)?.split("/")?.last() ?: "jpg"
        return "$name.$ext"
    }


    // URI -> File 변환
    fun toFile(context: Context, uriData: UriData,resize:Int=1080,quality:Int=DEFAULT_IMAGE_QUALITY): File? {

        val fileName = getFileName(context, uriData.uri)
        val tempFile = createTempFile(context, fileName) ?:return null
        val bitmap = convertUriToBitmap(context,uriData.uri) ?:return null

        if(uriData.isOldImage){
            copyToFile(context,uriData.uri,tempFile)
            return tempFile
        }else{
            val rotateBitmap = rotateBitmap(context, uriData.uri, bitmap) ?: return null
            val resizedBitmap=resizeBitmap(rotateBitmap, resize = resize) ?:return null

            return try {
                saveBitmapToFile(resizedBitmap, tempFile, quality)
                tempFile
            } catch (e: Exception) {
                Log.e("에러", "파일 변환 중 오류 발생: ${e.message}", e)
                tempFile.delete()
                null
            }
        }
    }

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


    // URI 리스트를 파일 리스트로 변환
    fun convertUrisToFiles(context: Context, uriDataList: List<UriData>): List<File> {
        val fileList = mutableListOf<File>()
        Log.d("이미지 가공 전", uriDataList.toString())
        for (uriData in uriDataList) {
            if(!uriData.isOldImage){
                try {
                    val file = toFile(context, uriData)
                    if(file!=null){
                        fileList.add(file)
                    }

                } catch (e: Exception) {
                    Log.e("변환 실패", "파일 변환 실패: $uriData", e)
                }
            }
        }

        return fileList
    }

    fun isCacheFile(context: Context, filePath: String): Boolean {
        val cacheDir = context.cacheDir
        val file = File(filePath)
        val absolutePath = file.absolutePath

        return absolutePath.startsWith(cacheDir.absolutePath)
    }

    fun convertUrisToFiles(context: Context, uriData: UriData): File? {
        return if(!uriData.isOldImage){
            val file=try {
                toFile(context, uriData)
            } catch (e: Exception) {
                Log.e("변환 실패", "파일 변환 실패: $uriData", e)
                null
            }
            file
        }else{
            null
        }

    }


    fun byteArrayListToCacheUriList(context: Context, byteArrayList: List<ImageData>?, fileNameList: List<String>?): List<UriData>? {

        if (byteArrayList.isNullOrEmpty()) return null

        val imageUrlList = mutableListOf<UriData>()

        for (i in byteArrayList.indices) {
            val byteArray = byteArrayList[i]
            val fileName = fileNameList?.getOrNull(i) ?: "image_$i.jpg"

            try {

                val file = File(context.cacheDir, fileName)
                FileOutputStream(file).use { fos ->
                    fos.write(byteArray.image)
                }

                val uri = Uri.fromFile(file)
                imageUrlList.add(UriData(uri=uri,isOldImage = byteArray.isOldImage))
            } catch (e: Exception) {
                Log.d("Uri 가져오기 실패", e.toString())
            }
        }

        return if (imageUrlList.isEmpty()) null else imageUrlList
    }

    fun byteArrayToCacheUri(context: Context, byteArray: ImageData?, fileName: String?): UriData? {

        if (byteArray==null) return null

        return try {

            val file = File(context.cacheDir, fileName ?: "image_temp.jpg")
            FileOutputStream(file).use { fos ->
                fos.write(byteArray.image)
            }

            UriData(uri=Uri.fromFile(file),isOldImage = byteArray.isOldImage)
        } catch (e: Exception) {
            Log.d("Uri 가져오기 실패", e.toString())
            null
        }

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

    fun convertUriToBitmap(context: Context,uri:Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream).also {
                inputStream?.close()
            }
        } catch (e: IOException) {
            Log.e("비트맵 생성", "생성 실패: ${e.message}", e)
            return null
        }
    }
    private fun resizeBitmap(bitmap: Bitmap,resize: Int,): Bitmap? {

        var resizedBitmap=bitmap


        val width = resizedBitmap.width
        val height = resizedBitmap.height

        // 비율
        val ratio = Math.min(resize.toFloat() / width, resize.toFloat() / height)
        val newWidth = (width * ratio).toInt()
        val newHeight = (height * ratio).toInt()

        // 크기
        return try {
            //todo 산넘어 산 리뷰 수정중 새로운 사진만 제대로 전송되는 문제가 있음 에러 이유 찾아야함
            //java.lang.IllegalStateException: Can't compress a recycled bitmap
            Bitmap.createScaledBitmap(resizedBitmap, newWidth, newHeight, true).apply {
                resizedBitmap.recycle()
            }
        } catch (e: Exception) {
            Log.e("크기조절 실패", "비트맵 크기 조정 실패: ${e.message}", e)
            resizedBitmap.recycle()
            null
        }
    }


    private fun saveBitmapToFile(bitmap: Bitmap, outputFile: File, quality: Int = DEFAULT_IMAGE_QUALITY) {
        FileOutputStream(outputFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
        }
    }

    //계속 리사이즈를 하면 이미지가 돌아가는 문제가 있었는데 리사이즈 하기전에 비트맵을 EXIF정보에 따라서 회전시키는 기능을
    //스택오버플로우에서 찾음
    private fun rotateBitmap(context: Context, uri: Uri, bitmap: Bitmap): Bitmap? {
        var inputStream: InputStream? = null
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            val exifInterface = ExifInterface(inputStream!!)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_NORMAL -> return bitmap
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                    matrix.setRotate(180f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_TRANSPOSE -> {
                    matrix.setRotate(90f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
                ExifInterface.ORIENTATION_TRANSVERSE -> {
                    matrix.setRotate(-90f)
                    matrix.postScale(-1f, 1f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
                else -> return bitmap
            }

            return try {
                val bmRotated = Bitmap.createBitmap(
                    bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
                )
                bitmap.recycle()
                bmRotated
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
                null
            }

        } catch (e: IOException) {
            Log.e("회전실패", "비트맵 회전 실패: ${e.message}", e)
            return bitmap
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                Log.e("인풋스트림 닫기실패", "닫기실패: ${e.message}", e)
            }
        }
    }

}