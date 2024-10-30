## <img src="https://mlicense.net/logo.webp" width="25" height="25"> Api for mLicense system. 

[![Release](https://img.shields.io/github/v/release/kerpsondev/mLicense-SDK.svg)](https://github.com/kerpsondev/mLicense-SDK/releases)

Java api allows you to connect to the mLicense system and easily use its capabilities from within your app!

Api has few modules:
1. Validity
2. Licenses
3. Products
4. Addons
5. Blacklists
   
All operations can be performed synchronously as well as asynchronously.
<br>

## 💙 Lets start

Add api to your project by maven or gradle.

### Maven

```xml
<repositories>
  <repository>
    <id>minecreators-repository-releases</id>
    <name>MineCreators Repository</name>
    <url>https://repository.minecreators.pl/releases</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>pl.kerpson.utilities.license</groupId>
    <artifactId>mLicense-SDK</artifactId>
    <version>1.0.2-BETA1</version>
  </dependency>
</dependencies>
```

### Gradle
```gradle
maven {
    name = "minecreatorsRepositoryReleases"
    url = uri("https://repository.minecreators.pl/releases")
}

implementation("pl.kerpson.utilities.license:mLicense-SDK:1.0.2-BETA1")
```
<br>

Once mLicense-SDK is added, we can move on to creating an instance of MLicense.
```java
MLicense license = MLicense.builder()
    .secret(SECRET)
    .apiKey(API_KEY)
    .logger(LOGGER_PROVIDER) // Optional
    .produce();
```

- What is secret? This is the key that allows you to use the check validation module.
- What is apiKey? Token allows you to use the other modules (that's it in a nutshell).

Not bad, we created an mLicense instance!
<br>

Now a bit of boring theory, each query returns us an OperationResult object.
<br>

**OperationResult** returns 2 objects.

```java
/* depends on the type of query, it can be a boolean or some other object.
The important thing is that it can be a null. */
T getResult();

/* Returned error when the operation fails, also can be nullable. */
Throwable getThrowable();
```

> [!IMPORTANT]
> Both returned values can be nullable.

I'm not boring anymore, let's move on to examples.

### 💙 Validation module

```java
// Sync operation
OperationResult<LicenseResult> result = license.checkLicense(
    KEY,
    PRODUCT,
    VERSION
);

// Async operation
CompletableFuture<OperationResult<LicenseResult>> resultAsync = license.checkLicenseAsync(
    KEY,
    PRODUCT,
    VERSION
);
```

### 💙 Other modules

As I mentioned, the api currently has 4 modules (including validation).

1. LicenseModule
2. ProductModule
3. BlacklistModule

```java
try {
    LicenseModule licenseModule = license.getModule(LicenseModule.class);
    OperationResult<List<License>> syncLicenses = licenseModule.getAll().complete();
    CompletableFuture<OperationResult<List<License>>> asyncLicenses = licenseModule.getAll().completeAsync();
    // Do somethink
    // Other modules example
    ProductModule productModule = license.getModule(ProductModule.class);
    BlacklistModule blacklistModule = license.getModule(BlacklistModule.class);
} catch (ModuleDisabledException ignored) {} // Exception is triggered when the JWT token is not specified
```

### 💙 License object.

The license object returns information such as id, key, description, customer information, addresses, machine and license time.
<br>

In a situation where you want to create a license through the api, use the License.createLicense() method.

```java
License license = License.createLicense()
    .setId(0) // Set the license id only when you want to update it, not create it.
    .setKey(KEY) // You can use too method License.generateKey()
    .setProductId(PRODUCT_ID)
    .setClientInfo(CLIENT_NAME, EMAIL, DISCORD_ID) // String, String (Nullable), Long
    .setAddressInfo(LIMIT, DURATION, ASSIGNED_TO) // Integer, Long, String (Nullable)
    .setMachineInfo(LIMIT, DURATION, ASSIGNED_TO) // Integer, Long, String (Nullable)
    .setDurationInfo(DURATION, DELETE_AFTER_EXPIRE) // Long, Boolean
    .build();

licenseModule.create(license).complete(); //Create license
licenseModule.update(license).complete(); //Update license
```

### 💙 Product object.
```java
Product product = Product.createProduct()
    .setId(0) // Set the product id only when you want to update it, not create it.
    .setName(PRODUCT_NAME)
    .setVersion(VERSION)
    .build();

productModule.create(product).complete(); //Create product
productModule.update(product).complete(); //Update product
```

### 💙 Addon object.
```java
Addon addon = Addon.createAddon()
    .setId(0) // Set the product id only when you want to update it, not create it.
    .setName(ADDON_NAME)
    .setVersion(VERSION)
    .setProducts(List.of(PRODUCTS_ID))
    .build();

productModule.create(product).complete(); //Create product
productModule.update(product).complete(); //Update product
```

### 💙 Blacklist object.
```java
Blacklist blacklist = Blacklist.createBlacklist()
    .setValue(VALUE) // Ip or HWID address
    .setType(Type.HWID) // Type IP and HWID
    .build();

blacklistModule.create(blacklist).complete(); //Create blacklist
blacklistModule.update(blacklist).complete(); //Update blacklist
```

### 💙 Project status
![Alt](https://repobeats.axiom.co/api/embed/d8dea05a60bd8ad22f39a69a46955d7eceda5f12.svg "Repobeats analytics image")
