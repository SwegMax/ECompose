package com.example.data.network

import com.example.data.model.request.AddToCartRequest
import com.example.data.model.request.AddressDataModel
import com.example.data.model.request.LoginRequest
import com.example.data.model.request.RegisterRequest
import com.example.data.model.response.CartResponse
import com.example.data.model.response.CartSummaryResponse
import com.example.data.model.response.CategoriesListResponse
import com.example.data.model.response.OrdersListResponse
import com.example.data.model.response.PlaceOrderResponse
import com.example.data.model.response.ProductListResponse
import com.example.data.model.response.UserAuthResponse
import com.example.domain.model.AddressDomainModel
import com.example.domain.model.CartItemModel
import com.example.domain.model.CartModel
import com.example.domain.model.CartSummary
import com.example.domain.model.CategoriesListModel
import com.example.domain.model.OrdersListModel
import com.example.domain.model.ProductListModel
import com.example.domain.model.UserDomainModel
import com.example.domain.model.request.AddCartRequestModel
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class NetworkServiceImpl(val client: HttpClient) : NetworkService {
    private val baseUrl = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/v2"
    //fallback = https://fakestoreapi.com

    override suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel> {
        val url =
            if (category != null) "$baseUrl/products/category/$category" else "$baseUrl/products"

        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { dataModels: ProductListResponse ->
                dataModels.toProductList()
            }
        )
    }

    override suspend fun getCategories(): ResultWrapper<CategoriesListModel> {
        val url = "$baseUrl/categories"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = {
                categories: CategoriesListResponse ->
                categories.toCategoriesList()
            }
        )
    }

    override suspend fun addProductToCart(request: AddCartRequestModel, userId: Long): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/${userId}"
        return makeWebRequest(url = url,
            method = HttpMethod.Post,
            body = AddToCartRequest.fromCartRequestModel(request),
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            })
    }

    override suspend fun getCart(userId: Long): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/$userId"
        return makeWebRequest(url = url,
            method = HttpMethod.Get,
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            })
    }

    override suspend fun updateQuantity(cartItemModel: CartItemModel, userId: Long): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/$userId/${cartItemModel.id}"
        return makeWebRequest(url = url,
            method = HttpMethod.Put,
            body = AddToCartRequest(
                productId = cartItemModel.productId,
                quantity = cartItemModel.quantity
            ),
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            })
    }

    override suspend fun deleteItem(cartItemId: Int, userId: Long): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/$userId/$cartItemId"
        return makeWebRequest(url = url,
            method = HttpMethod.Delete,
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            })
    }

    override suspend fun getCartSummary(userId: Long): ResultWrapper<CartSummary> {
        val url = "$baseUrl/checkout/$userId/summary"
        return makeWebRequest(url = url,
            method = HttpMethod.Get,
            mapper = { cartItem: CartSummaryResponse ->
                cartItem.toCartSummary()
            })
    }

    override suspend fun placeOrder(address: AddressDomainModel, userId: Long): ResultWrapper<Long> {
        val dataModel = AddressDataModel.fromDomainAddress(address)
        val url = "$baseUrl/orders/$userId"
        return makeWebRequest(url = url,
            method = HttpMethod.Post,
            body = dataModel,
            mapper = { orderRes: PlaceOrderResponse ->
                orderRes.data.id
            })
    }

    override suspend fun getOrderList(userId: Long): ResultWrapper<OrdersListModel> {
        val url = "$baseUrl/orders/$userId"
        return makeWebRequest(url = url,
            method = HttpMethod.Get,
            mapper = { ordersResponse : OrdersListResponse ->
                ordersResponse.toDomainResponse()
            })
    }

    override suspend fun login(email: String, password: String): ResultWrapper<UserDomainModel> {
        val url = "$baseUrl/login"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Post,
            body = LoginRequest(email, password),
            mapper = { user: UserAuthResponse ->
                user.data.toDomainModel()
            }
        )
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String
    ): ResultWrapper<UserDomainModel> {
        val url = "$baseUrl/signup"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Post,
            body = RegisterRequest(email, password, name),
            mapper = { user: UserAuthResponse ->
                user.data.toDomainModel()
            }
        )
    }

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
                        parameters.forEach { (key, value) ->
                            append(key, value)
                        }
                    })
                }

                headers.forEach { (key, value) ->
                    header(key, value)
                }

                if (body != null) {
                    setBody(body)
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