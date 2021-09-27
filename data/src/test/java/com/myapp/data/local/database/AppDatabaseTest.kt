package com.myapp.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.myapp.data.local.database.dao.mission_statement.ConstitutionDao
import com.myapp.data.local.database.dao.mission_statement.FuneralDao
import com.myapp.data.local.database.dao.mission_statement.PurposeLifeDao
import com.myapp.data.local.database.dao.report.FfsReportDao
import com.myapp.data.local.database.dao.report.WeatherReportDao
import com.myapp.data.local.database.entity.mission_statement.ConstitutionEntity
import com.myapp.data.local.database.entity.mission_statement.FuneralEntity
import com.myapp.data.local.database.entity.mission_statement.PurposeLifeEntity
import com.myapp.data.local.database.entity.report.FfsReportEntity
import com.myapp.data.local.database.entity.report.WeatherReportEntity
import java.io.IOException
import java.time.LocalDateTime
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * ROOM　CRUDテスト
 *
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var ffsReportDao: FfsReportDao
    private lateinit var weatherReportDao: WeatherReportDao
    private lateinit var funeralDao: FuneralDao
    private lateinit var purposeLifeDao: PurposeLifeDao
    private lateinit var constitutionDao: ConstitutionDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
        ffsReportDao = db.ffsReportDao()
        weatherReportDao = db.emotionReportDao()
        funeralDao = db.funeralDao()
        purposeLifeDao = db.purposeLifeDao()
        constitutionDao = db.constitutionDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        stopKoin()
        db.close()
    }

    // region ffsReportDao(FFSテーブルCRUD)

    /**
     * FFSデータ１件登録
     *
     * 条件：なし
     * 期待結果：登録したデータがそのまま取得できること
     */
    @Test
    fun ffsReportInsert() = runBlocking {
        val data = FfsReportEntity(1, "fact", "find", "learn", "statement", LocalDateTime.now())
        ffsReportDao.insert(data)

        val result = ffsReportDao.getAll()
        assertEquals(1, result.size)
        assertEquals(data, result[0])
    }

    /**
     * 全てのFFSデータ取得
     *
     * 条件：３件登録
     * 期待結果：登録した全てのFFSデーアを取得できること
     */
    @Test
    fun ffsReportGetAll() = runBlocking {
        val data1 = FfsReportEntity(1, "fact", "find", "learn", "statement", LocalDateTime.now())
        val data2 = FfsReportEntity(2, "fact", "find", "learn", "statement", LocalDateTime.now())
        val data3 = FfsReportEntity(3, "fact", "find", "learn", "statement", LocalDateTime.now())
        ffsReportDao.insert(data1)
        ffsReportDao.insert(data2)
        ffsReportDao.insert(data3)

        val result = ffsReportDao.getAll()
        assertEquals(3, result.size)
        assertEquals(data1, result[0])
        assertEquals(data2, result[1])
        assertEquals(data3, result[2])
    }

    // endregion

    // region weatherReportDao(感情日記テーブルCRUD)

    /**
     * 感情日記データ１件登録
     *
     * 条件：なし
     * 期待結果：登録したデータがそのまま取得できること
     */
    @Test
    fun weatherReportInsert() = runBlocking {
        val data = WeatherReportEntity(1, 50, "reason1", "improve1", LocalDateTime.now())
        weatherReportDao.insert(data)

        val result = weatherReportDao.getAll()
        assertEquals(1, result.size)
        assertEquals(data, result[0])
    }

    /**
     * 感情日記データ全権取得
     *
     * 条件：感情日記データ３件登録
     * 期待結果：登録したデータがを全て取得できること
     */
    @Test
    fun weatherReportGetAll() = runBlocking {
        val data1 = WeatherReportEntity(1, 50, "reason1", "improve1", LocalDateTime.now())
        val data2 = WeatherReportEntity(2, 50, "reason2", "improve2", LocalDateTime.now())
        val data3 = WeatherReportEntity(3, 50, "reason3", "improve3", LocalDateTime.now())
        weatherReportDao.insert(data1)
        weatherReportDao.insert(data2)
        weatherReportDao.insert(data3)

        val result = weatherReportDao.getAll()
        assertEquals(3, result.size)
        assertEquals(data1, result[0])
        assertEquals(data2, result[1])
        assertEquals(data3, result[2])
    }

    // endregion

    // region funeralDao(理想の葬式テーブルCRUD)

    /**
     * 理想の葬式データ１件登録
     *
     * 条件：なし
     * 期待結果：登録したデータがそのまま取得できること
     */
    @Test
    fun funeralInsert() = runBlocking {
        val data = FuneralEntity(1, "funeral1", LocalDateTime.now())
        funeralDao.insert(data)

        val result = funeralDao.getAll()
        assertEquals(1, result.size)
        assertEquals(data, result[0])
    }

    /**
     * 理想の葬式データ全権取得
     *
     * 条件：理想の葬式データ３件登録
     * 期待結果：登録したデータがを全て取得できること
     */
    @Test
    fun funeralGetAll() = runBlocking {
        val data1 = FuneralEntity(1, "funeral1", LocalDateTime.now())
        val data2 = FuneralEntity(2, "funeral1", LocalDateTime.now())
        val data3 = FuneralEntity(3, "funeral1", LocalDateTime.now())
        funeralDao.insert(data1)
        funeralDao.insert(data2)
        funeralDao.insert(data3)

        val result = funeralDao.getAll()
        assertEquals(3, result.size)
        assertEquals(data1, result[0])
        assertEquals(data2, result[1])
        assertEquals(data3, result[2])
    }

    /**
     * 理想の葬式データ全検削除
     *
     * 条件：理想の葬式データ３件登録→全権削除
     * 期待結果：取得メソッドで取得結果が0件になること
     */
    @Test
    fun funeralDeleteAll() = runBlocking {
        val data1 = FuneralEntity(1, "funeral1", LocalDateTime.now())
        val data2 = FuneralEntity(2, "funeral1", LocalDateTime.now())
        val data3 = FuneralEntity(3, "funeral1", LocalDateTime.now())
        funeralDao.insert(data1)
        funeralDao.insert(data2)
        funeralDao.insert(data3)
        funeralDao.deleteAll()

        val result = funeralDao.getAll()
        assertEquals(0, result.size)
    }

    // endregion

    // region purposeLifeDao(人生の目的テーブルCRUD)

    /**
     * 人生の目的データ１件登録
     *
     * 条件：なし
     * 期待結果：登録したデータがそのまま取得できること
     */
    @Test
    fun purposeLifeInsert() = runBlocking {
        val data = PurposeLifeEntity(1, "purposeLife", LocalDateTime.now(), LocalDateTime.now())
        purposeLifeDao.insert(data)

        val result = purposeLifeDao.get()
        assertEquals(data, result)
    }

    /**
     * 人生の目的データ更新
     *
     * 条件：なし
     * 期待結果：データが更新されること
     */
    @Test
    fun purposeLifeUpdate() = runBlocking {
        val data = PurposeLifeEntity(1, "purposeLife", LocalDateTime.now(), LocalDateTime.now())
        purposeLifeDao.insert(data)
        val dataAfter = PurposeLifeEntity(1, "purposeLifeAfter", LocalDateTime.now(), LocalDateTime.now())
        purposeLifeDao.update(dataAfter)

        val result = purposeLifeDao.get()
        assertEquals(dataAfter, result)
    }

    // endregion

    // region constitutionDao(憲法テーブルCRUD)

    /**
     * 憲法データ１件登録
     *
     * 条件：なし
     * 期待結果：登録したデータがそのまま取得できること
     */
    @Test
    fun constitutionInsert() = runBlocking {
        val data = ConstitutionEntity(1, "constitution", LocalDateTime.now())
        constitutionDao.insert(data)

        val result = constitutionDao.getAll()
        assertEquals(1, result.size)
        assertEquals(data, result[0])
    }

    /**
     * 憲法データ全権取得
     *
     * 条件：憲法データ３件登録
     * 期待結果：登録したデータがを全て取得できること
     */
    @Test
    fun constitutionGetAll() = runBlocking {
        val data1 = ConstitutionEntity(1, "constitution1", LocalDateTime.now())
        val data2 = ConstitutionEntity(2, "constitution2", LocalDateTime.now())
        val data3 = ConstitutionEntity(3, "constitution3", LocalDateTime.now())
        constitutionDao.insert(data1)
        constitutionDao.insert(data2)
        constitutionDao.insert(data3)

        val result = constitutionDao.getAll()
        assertEquals(3, result.size)
        assertEquals(data1, result[0])
        assertEquals(data2, result[1])
        assertEquals(data3, result[2])
    }

    /**
     * 憲法データ全検削除
     *
     * 条件：憲法データ３件登録→全権削除
     * 期待結果：取得メソッドで取得結果が0件になること
     */
    @Test
    fun constitutionDeleteAll() = runBlocking {
        val data1 = ConstitutionEntity(1, "constitution1", LocalDateTime.now())
        val data2 = ConstitutionEntity(2, "constitution2", LocalDateTime.now())
        val data3 = ConstitutionEntity(3, "constitution3", LocalDateTime.now())
        constitutionDao.insert(data1)
        constitutionDao.insert(data2)
        constitutionDao.insert(data3)
        constitutionDao.deleteAll()

        val result = constitutionDao.getAll()
        assertEquals(0, result.size)
    }

    // endregion
}
