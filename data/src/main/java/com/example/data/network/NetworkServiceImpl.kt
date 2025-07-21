package com.example.data.network

import com.example.data.model.DataProductModel
import com.example.domain.model.Product
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import io.ktor.utils.io.errors.IOException

class NetworkServiceImpl (val client : HttpClient): NetworkService {
    private val baseUrl = "https://fakestoreapi.com"
    override suspend fun getProducts(category: String?): ResultWrapper<List<Product>> {
        val url = if (category.isNullOrEmpty()) {
            "$baseUrl/products"
        } else {
            "$baseUrl/products/category/$category"
        }

        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = {dataModels: List<DataProductModel> ->
                dataModels.map {it.toProduct()}
            }
        )
    }

    override suspend fun getCategories(): ResultWrapper<List<String>> {
        val url = "$baseUrl/products/categories"

        return makeWebRequest<List<String>, List<String>>(
            url = url,
            method = HttpMethod.Get,
        )
    }

    @OptIn(InternalAPI::class)
    suspend inline fun <reified T, R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): ResultWrapper<R> {
        return try {
            val response = client.request(url) {
                this.method = method

                url {
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach{ (key, value) ->
                            append(key, value)
                        }
                    })
                }

                headers.forEach { (key, value) ->
                    header(key, value)
                }

                if (body != null) {
                    this.body = body
                }

                contentType(ContentType.Application.Json)
            }.body<T>()
            val result: R = mapper?.invoke(response) ?: response as R
            ResultWrapper.Success(result)
        } catch (e: ClientRequestException) {
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Failure(e)
        } catch (e: IOException) {
            ResultWrapper.Failure(e)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }
}