# ECompose - An eCommerce app

An e-Commerce app built with modern frameworks and focuses on demonstrating good fundamentals

---

# Stack/Tech used

- Compose for UI
- Koin for DI
- Ktor for HTTP calls
- Coil for Image Loading
- Both Unit and Automated tests included (JUnit/Espresso/Mockito for mock data)
- MVI architechure

---

## About The Project

This project is a follow up to https://github.com/SwegMax/Shopeee, wherein an older stack and architechure was used. This project aims to improve/address the following:
- Testablity
- Maintainability
- Better Seperation of Concerns, with modules:
  - Backend/DB(Data📁),
  - ViewModel/Business Logic(Domain📁),
  - View(Presentation📁)
- Code Reusability: Enable smoother integration with other platforms
- Dealing with real-world JSON responses: Integration with custom BE(built with Swagger)
- Up-to-date Android tech used: XML -> Compose, Dagger/Hilt -> Koin, Retrofit -> Ktor, Glide -> Coil
  - MVI architechure implemented on top of MVVM principles
- Optimzation: Compose > XML, minimal ViewModelScopes to reduce thread pool usage

---

## Features ✨

Based on recent development cycles, this application includes core e-commerce functionalities and architectural considerations:

* **User Authentication**: User management, including secure **Login and Register** flows.
<img width="329" height="584" alt="Running Devices - ECompose 7_28_2025 7_27_58 PM" src="https://github.com/user-attachments/assets/4243dc36-e653-4870-8ab2-2ea0ccd4b957" />
<img width="329" height="584" alt="Running Devices - ECompose 7_28_2025 7_28_01 PM" src="https://github.com/user-attachments/assets/eec08ca6-28b1-48a5-aca5-f876bc16cb20" />


* **Product Browse**: **Home Screen** displays **Featured and Popular products**. Animation added for seamless customer experience.
  * **Product Details**: **Product Details Page (PDP)** with **Navigation to PDP** from product listings
 
<img width="329" height="584" alt="Running Devices - ECompose 7_28_2025 7_25_30 PM" src="https://github.com/user-attachments/assets/8c43a43c-9d3d-4e0a-9a5f-b2665dedb494" />
<img width="329" height="584" alt="Running Devices - ECompose 7_28_2025 7_25_48 PM" src="https://github.com/user-attachments/assets/105ae683-206b-4935-8b35-eb47b92512e2" />

* **Shopping Cart & Order Management**: **Cart** functionality, with **Cart Summary** showing the detials.
  * **Order Details**: The cart flow seamlessly transitions to an **Orders List**, allowing users to manage their purchases.
  * **Address details** are integrated into orders for complete purchase tracking.
 
<img width="329" height="584" alt="Running Devices - ECompose 7_28_2025 7_26_53 PM" src="https://github.com/user-attachments/assets/95530e16-69bd-4020-853b-5256d8bc228f" />
<img width="329" height="584" alt="Running Devices - ECompose 7_28_2025 7_26_46 PM" src="https://github.com/user-attachments/assets/41d640d7-8a99-415e-ab60-8d3837081c48" />
<img width="329" height="584" alt="Running Devices - ECompose 7_28_2025 7_26_15 PM" src="https://github.com/user-attachments/assets/80a051c5-08e3-4480-9245-178b29869ce0" />
<img width="329" height="584" alt="Running Devices - ECompose 7_28_2025 7_27_01 PM" src="https://github.com/user-attachments/assets/04b680c5-2650-43c5-97d2-a22eba34027b" />

* **API Integration**: Custom RESTful API used for data
* **Testing**: **Unit and Instrumented Tests** included to ensure a robust application and features act as intented.

---

## Project Setup & Running Locally ⚙️

### Versions I used

* **Java/Kotlin Version:** `JVM 1.8(Java 8), Kotlin 1.8`
* Device API `Min API version: 33`

### Installation

1.  **Clone the repository:**
2.  **Backend Setup (if applicable):**
    * Use from: https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/swagger
    * Documentation: https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/swagger/swagger_docs.yaml
3.  **Android App Setup:**
    * Open the project in Android Studio.
    * Sync Gradle files.
    * Ensure all dependencies are resolved.

---

## Custom Backend Response Example 🌐

```json
[
  {
  "msg": "Success",
  "data": [
    {
      "id": 1,
      "title": "Dell XPS 13",
      "description": "High-performance ultrabook",
      "price": 1200,
      "image": "https://i.dell.com/is/image/DellContent/content/dam/ss2/product-images/dell-client-products/notebooks/xps-notebooks/13-9340/media-gallery/silver/touch/notebook-xps-13-9340-t-sl-gallery-2.psd?fmt=png",
      "categoryId": 1
    },
    {
      "id": 2,
      "title": "iPhone 14 Pro",
      "description": "Latest flagship smartphone from Apple",
      "price": 999,
      "image": "https://images.priceoye.pk/apple-iphone-14-pro-pakistan-priceoye-fmnxi-500x500.webp",
      "categoryId": 1
    },
    {
      "id": 3,
      "title": "Sony WH-1000XM4",
      "description": "Noise-canceling wireless headphones",
      "price": 349,
      "image": "https://www.shophive.com/media/catalog/product/cache/3617b85921733ef3774cdbec091e1c0f/7/1/71o8q5xjs5l._ac_sl1500_.jpg",
      "categoryId": 1
    }
  ]
}
]
