# Aplikasi Warung Makan Samudra

### Deskripsi

Setelah menyelesaikan pembelajaran tentang Restful API, Anda ditugaskan oleh manajemen Warung Makan Samudra (WMS) yang sedang berkembang pesat untuk menciptakan sebuah
API dengan fitur-fitur yang akan meningkatkan efisiensi dan kualitas layanan mereka.

Fitur-fitur yang diminta oleh manajemen WMS adalah:

1. Manajemen Cabang
2. Manajemen Produk/Menu
3. Manajemen Transaksi
4. Laporan Penjualan

### Cara Menjalankan Test:

1. Pastikan Anda telah menginstal Java Development Kit (JDK) `Minimal Versi 17` dan Maven di komputer Anda.
2. Buka terminal atau command prompt dan arahkan ke direktori proyek `Contoh: warung-makan-bahari-api`.
3. Jalankan perintah berikut untuk menjalankan semua tes:

```shell
mvn test -Dtest=TestSuite
```

### Catatan:

- Pastikan Anda telah melakukan konfigurasi yang tepat pada file `application.properties`.
- Jika test gagal silakan `truncate` semua table di database Anda.

## API Spec

### Branch API

#### Create Branch

Request :

- Method : POST
- Endpoint : `/api/branch`
- Header :
  - Content-Type: application/json
  - Accept: application/json
- Body :

```json 
{
  "branchCode": "string",
  "branchName": "string",
  "address": "string",
  "phoneNumber": "string"
}
```

Response :

```json 
{
  "errors": "string",
  "data": {
    "branchId": "string",
    "branchCode": "string",
    "branchName": "string",
    "address": "string",
    "phoneNumber": "string"
  },
  "paging": null
}
```

#### Get Product

Request :

- Method : GET
- Endpoint : `/api/branch/{id_branch}`
- Header :
  - Accept: application/json

Response :

```json 
{
  "errors": "string",
  "data": {
    "branchId": "string",
    "branchCode": "string",
    "branchName": "string",
    "address": "string",
    "phoneNumber": "string"
  },
  "paging": null
}
```

#### Update Branch

Request :

- Method : PUT
- Endpoint : `/api/branch`
- Header :
  - Content-Type: application/json
  - Accept: application/json
- Body :

```json 
{
  "branchId": "string",
  "branchCode": "string",
  "branchName": "string",
  "address": "string",
  "phoneNumber": "string"
}
```

Response :

```json 
{
  "errors": "string",
  "data": {
    "branchId": "string",
    "branchCode": "string",
    "branchName": "string",
    "address": "string",
    "phoneNumber": "string"
  },
  "paging": null
}
```

#### Delete Branch

Request :

- Method : DELETE
- Endpoint : `/api/branch/{id_branch}`
- Header :
  - Accept: application/json
- Body :

Response :

```json 
{
  "errors": "string",
  "data": "OK",
  "paging": null
}
```

### Product API

#### Create Product

Request :

- Method : POST
- Endpoint : `/api/products`
- Header :
  - Content-Type: application/json
  - Accept: application/json
- Body :

```json 
{
  "productCode": "string",
  "productName": "string",
  "price": "big decimal",
  "branchId": "string"
}
```

Response :

```json 
{
  "errors": "string",
  "data": {
    "productId": "string",
    "productPriceId": "string",
    "productCode": "string",
    "productName": "string",
    "price": "big decimal",
    "branch": {
      "branchId": "string",
      "branchCode": "string",
      "branchName": "string",
      "address": "string",
      "phoneNumber": "string"
    }
  },
  "paging": null
}
```

#### List Product

Request :

- Method : GET
- Endpoint : `/api/products`
- Header :
  - Accept: application/json
- Query Param :
  - size : number,
  - page : number,
  - productCode : string `optional`,
  - productName : string `optional`,
  - minPrice : string `optional`,
  - maxPrice : string `optional`,

Response :

```json 
{
  "errors": "string",
  "data": [
    {
      "productId": "string",
      "productPriceId": "string",
      "productCode": "string",
      "productName": "string",
      "price": "big decimal",
      "branch": {
        "branchId": "string",
        "branchCode": "string",
        "branchName": "string",
        "address": "string",
        "phoneNumber": "string"
      }
    },
    {
      "productId": "string",
      "productPriceId": "string",
      "productCode": "string",
      "productName": "string",
      "price": "big decimal",
      "branch": {
        "branchId": "string",
        "branchCode": "string",
        "branchName": "string",
        "address": "string",
        "phoneNumber": "string"
      }
    }
  ],
  "paging": {
    "count": "number",
    "totalPage": "number",
    "page": "number",
    "size": "number"
  }
}
```

#### List Product By Branch Id

Request :

- Method : GET
- Endpoint : `/api/products/{id_branch}`
- Header :
  - Accept: application/json

Response :

```json 
{
  "errors": "string",
  "data": [
    {
      "productId": "string",
      "productPriceId": "string",
      "productCode": "string",
      "productName": "string",
      "price": "big decimal",
      "branch": {
        "branchId": "string",
        "branchCode": "string",
        "branchName": "string",
        "address": "string",
        "phoneNumber": "string"
      }
    },
    {
      "productId": "string",
      "productPriceId": "string",
      "productCode": "string",
      "productName": "string",
      "price": "big decimal",
      "branch": {
        "branchId": "string",
        "branchCode": "string",
        "branchName": "string",
        "address": "string",
        "phoneNumber": "string"
      }
    }
  ],
  "paging": {
    "count": "number",
    "totalPage": "number",
    "page": "number",
    "size": "number"
  }
}
```

#### Update Product

Request :

- Method : PUT
- Endpoint : `/api/products`
- Header :
  - Content-Type: application/json
  - Accept: application/json
- Body :

```json 
{
  "productId": "string",
  "productCode": "string",
  "productName": "string",
  "price": "big decimal",
  "branchId": "string"
}
```

Response :

```json 
{
  "errors": "string",
  "data": {
    "productId": "string",
    "productPriceId": "string",
    "productCode": "string",
    "productName": "string",
    "price": "big decimal",
    "branch": {
      "branchId": "string",
      "branchCode": "string",
      "branchName": "string",
      "address": "string",
      "phoneNumber": "string"
    }
  },
  "paging": null
}
```

#### Delete Product

Request :

- Method : DELETE
- Endpoint : `/api/products/{id_product}`
- Header :
  - Accept: application/json
- Body :

Response :

```json 
{
  "errors": "string",
  "data": "OK",
  "paging": null
}
```

### Transaction API

#### Create Transaction

Request :

- Method : POST
- Endpoint : `/api/transactions`
- Header :
  - Content-Type: application/json
  - Accept: application/json
- Body :

Request :

Transaction Type :

- "1" : `EAT IN`
- "2" : `ONLINE`
- "3" : `TAKE AWAY`

```json
{
  "transactionType": "string",
  "billDetails": [
    {
      "productPriceId": "string",
      "quantity": "number"
    },
    {
      "productPriceId": "string",
      "quantity": "number"
    }
  ]
}
```

Response :

- Template Receipt Number : `{branchCode}-{year}-{sequenceNumber}`
- Transaction Type : `EAT_IN | TAKE_AWAY | ONLINE`

```json
{
  "errors": "string",
  "data": {
    "billId": "string",
    "receiptNumber": "string",
    "transDate": "string",
    "transactionType": "string",
    "billDetails": [
      {
        "billDetailId": "string",
        "billId": "string",
        "product": {
          "productId": "string",
          "productPriceId": "string",
          "productCode": "string",
          "productName": "string",
          "price": "bigDecimal",
          "branch": {
            "branchId": "string",
            "branchCode": "string",
            "branchName": "string",
            "address": "string",
            "phoneNumber": "string"
          }
        },
        "quantity": "number",
        "totalSales": "bigDecimal"
      }
    ]
  },
  "paging": null
}
```

#### Get Transaction

Request :

- Method : GET
- Endpoint : `/api/transactions/{id_bill}`
- Header :
  - Accept: application/json
- Body :

Response :

- Template Receipt Number : `{branchCode}-{year}-{sequenceNumber}`
- Transaction Type : `EAT_IN | TAKE_AWAY | ONLINE`

```json
{
  "errors": "string",
  "data": {
    "billId": "string",
    "receiptNumber": "string",
    "transDate": "localDateTime",
    "transactionType": "string",
    "billDetails": [
      {
        "billDetailId": "string",
        "billId": "string",
        "product": {
          "productId": "string",
          "productCode": "string",
          "productName": "string",
          "price": "bigDecimal",
          "branch": {
            "branchId": "string",
            "branchCode": "string",
            "branchName": "string",
            "address": "string",
            "phoneNumber": "string"
          }
        },
        "quantity": "number"
      }
    ]
  },
  "paging": null
}
```

#### List Transaction

Pattern string date : `dd-MM-yyyy`

Request :

- Method : GET
- Endpoint : `/api/transactions`
- Header :
  - Accept : application/json
- Query Param :
  - page : number
  - size : number
  - receiptNumber : string `optional`
  - startDate : string `optional`
  - endDate : string `optional`
  - transType : string `optional`
  - productName : string `optional`
- Body :

Response :

- Template Receipt Number : `{branchId}-{year}-{sequenceNumber}`
- Transaction Type : `EAT_IN | TAKE_AWAY | ONLINE`

```json
{
  "errors": "string",
  "data": [
    {
      "billId": "string",
      "receiptNumber": "string",
      "transDate": "localDateTime",
      "transactionType": "string",
      "billDetails": [
        {
          "billDetailId": "string",
          "billId": "string",
          "product": {
            "productId": "string",
            "productCode": "string",
            "productName": "string",
            "price": "bigDecimal",
            "branch": {
              "branchId": "string",
              "branchCode": "string",
              "branchName": "string",
              "address": "string",
              "phoneNumber": "string"
            }
          },
          "quantity": "number"
        }
      ]
    }
  ],
  "paging": null
}
```

#### Get Total Sales

Request :

Pattern string date : `dd-MM-yyyy`

- Method : GET
- Endpoint : `/api/transactions/total-sales`
- Header :
  - Accept: application/json
- Query Param :
  - startDate : string `optional`,
  - endDate : string `optional`,
- Body :

Response :

```json
{
  "errors": "string",
  "data": {
    "eatIn": "bigDecimal",
    "takeAway": "bigDecimal",
    "online": "bigDecimal"
  },
  "paging": null
}
```

