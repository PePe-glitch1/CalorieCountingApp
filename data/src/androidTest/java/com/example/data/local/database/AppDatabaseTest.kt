package com.example.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.local.dao.AllProductsDao
import com.example.data.local.dao.UserDao
import com.example.data.local.entity.AllProductsEntity
import com.example.data.local.entity.UserEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var allProductsDao: AllProductsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
        allProductsDao = database.allProductsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    // ==================== UserDao Tests ====================

    @Test
    fun insertUser_returnsId() = runTest {
        val user = UserEntity(
            id = 0,
            username = "TestUser",
            email = "test@example.com",
            bornData = LocalDate.of(1990, 5, 15)
        )

        val insertedId = userDao.insert(user)

        assertTrue(insertedId > 0)
    }

    @Test
    fun insertAndGetUserById_returnsCorrectUser() = runTest {
        val user = UserEntity(
            id = 0,
            username = "John",
            email = "john@example.com",
            bornData = LocalDate.of(1985, 10, 20)
        )

        val insertedId = userDao.insert(user)
        val retrievedUser = userDao.getUserById(insertedId)

        assertNotNull(retrievedUser)
        assertEquals("John", retrievedUser?.username)
        assertEquals("john@example.com", retrievedUser?.email)
        assertEquals(LocalDate.of(1985, 10, 20), retrievedUser?.bornData)
    }

    @Test
    fun getUserById_nonExistentId_returnsNull() = runTest {
        val result = userDao.getUserById(999L)

        assertNull(result)
    }

    @Test
    fun updateUser_updatesCorrectly() = runTest {
        val user = UserEntity(
            id = 0,
            username = "Original",
            email = "original@example.com",
            bornData = LocalDate.of(1990, 1, 1)
        )

        val insertedId = userDao.insert(user)
        val updatedUser = user.copy(
            id = insertedId,
            username = "Updated",
            email = "updated@example.com"
        )
        userDao.update(updatedUser)

        val retrievedUser = userDao.getUserById(insertedId)

        assertEquals("Updated", retrievedUser?.username)
        assertEquals("updated@example.com", retrievedUser?.email)
    }

    @Test
    fun deleteById_removesUser() = runTest {
        val user = UserEntity(
            id = 0,
            username = "ToDelete",
            email = "delete@example.com",
            bornData = LocalDate.of(2000, 6, 15)
        )

        val insertedId = userDao.insert(user)
        userDao.deleteById(insertedId)

        val result = userDao.getUserById(insertedId)

        assertNull(result)
    }

    // ==================== AllProductsDao Tests ====================

    @Test
    fun getAllProductsByName_existingProduct_returnsProduct() = runTest {
        // Спочатку потрібно додати продукт через SQL, оскільки в DAO немає insert
        database.openHelper.writableDatabase.execSQL(
            """
            INSERT INTO all_products (product_name, caloriesIn1g, proteinsIn1g, fatsIn1g, carbohydratesIn1g)
            VALUES ('Apple', 0.52, 0.003, 0.002, 0.14)
            """
        )

        val product = allProductsDao.getAllProductsByName("Apple")

        assertNotNull(product)
        assertEquals("Apple", product?.name)
        assertEquals(0.52, product?.caloriesIn1g ?: 0.0, 0.001)
    }

    @Test
    fun getAllProductsByName_nonExistentProduct_returnsNull() = runTest {
        val product = allProductsDao.getAllProductsByName("NonExistent")

        assertNull(product)
    }

    // ==================== Database Creation Test ====================

    @Test
    fun database_createdSuccessfully() {
        assertNotNull(database)
        // Перевіряємо що можемо отримати DAO - це означає БД працює
        assertNotNull(database.userDao())
    }

    @Test
    fun allDaos_areAccessible() {
        assertNotNull(database.userDao())
        assertNotNull(database.userParamsDao())
        assertNotNull(database.dailyCaloryDao())
        assertNotNull(database.allProductsDao())
        assertNotNull(database.addedProductsDao())
        assertNotNull(database.caloriesInDayDao())
        assertNotNull(database.voterInDayDao())
        assertNotNull(database.addedVoterDao())
    }
}