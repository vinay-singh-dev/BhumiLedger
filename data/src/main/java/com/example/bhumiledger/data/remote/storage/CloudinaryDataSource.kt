package com.example.bhumiledger.data.remote.storage

import android.content.Context
import com.cloudinary.android.MediaManager
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import com.example.bhumiledger.data.BuildConfig
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import kotlin.coroutines.resume

class CloudinaryDataSource() {

    suspend fun uploadPdf(
        claimId:String,
        documentPath: String
    ): Result  <String> {

        return suspendCancellableCoroutine { continuation ->

            MediaManager.get()
                .upload(File(documentPath).absolutePath)
                .option("resource_type","raw")
                .option("public_id","claims/$claimId")
                .callback(object : UploadCallback {

                    override fun onStart(requestId: String?) {}
                    override fun onProgress(
                        requestId:String?,
                        bytes: Long,
                        totalBytes : Long
                    ) {}


                    override fun onSuccess(
                        requestId: String?,
                        resultData: MutableMap<Any? , Any?>?
                    ) {

                       val url = resultData?.get("secure_url")?.toString()

                        if (url != null) {
                            continuation.resume(
                                Result.success(url)
                            )
                        } else {
                            continuation.resume(
                                Result.failure(
                                    Exception("Upload URL missing")
                                )
                            )
                        }
                    }
                    override fun onError(
                        requestId : String?,
                        error : ErrorInfo?
                    ) {
                        continuation.resume(
                            Result.failure(
                                Exception(error?.description)
                            )
                        )
                    }

                    override fun onReschedule(
                        requestId:String?,
                        error : ErrorInfo?
                    ) {}

                })
                .dispatch()
        }
    }
}