package com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ratnavidyakanawade.fetchrewardsandroidproject.FetchRewardsListFragment
import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems
import com.ratnavidyakanawade.fetchrewardsandroidproject.repository.FetchRepository
import com.ratnavidyakanawade.fetchrewardsandroidproject.service.FetchApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.regex.Pattern.matches

class FetchViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Used a test coroutine dispatcher
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var fetchApi: FetchApi

   // private lateinit var fetchRepository: FetchRepository

    @Mock
    private lateinit var mockRepository: FetchRepository

    private lateinit var viewModel: FetchViewModel

    @Before
    fun setup() {

        Dispatchers.setMain(Dispatchers.Unconfined)
        MockitoAnnotations.openMocks(this)
        viewModel = FetchViewModel(mockRepository)
        mockRepository = FetchRepository(fetchApi)

    }

    @Test
    fun `test ViewModel retrieves, filters, and sorts items correctly`() = testDispatcher.runBlockingTest {
        // Mock data
        val mockItems = listOf(
            FetchItems(1, 1, "Item 1"),
            FetchItems(2, 1, ""),
            FetchItems(3, 2, "Item 3"),
            FetchItems(4, 3, null)
        )

        // Stubbing the repository's getItems method to return the mock data
        `when`(mockRepository.getItems()).thenReturn(mockItems)

        // Call the ViewModel method
        viewModel.loadItems()

        // Assert that the LiveData items have the expected values after filtering and sorting
        val observer = Observer<List<FetchItems>> { items ->
            assertEquals(2, items.size)
            assertEquals(1, items[0].id)
            assertEquals(3, items[1].id)
            assertEquals("Item 1", items[0].name)
            assertEquals("Item 3", items[1].name)
        }

        viewModel.items.observeForever(observer)
    }

    @Test
    fun `test ViewModel handles empty list from repository`() = testDispatcher.runBlockingTest {
        // Mock empty data
        val mockItems = emptyList<FetchItems>()

        // Stubbing the repository's getItems method to return the empty list
        `when`(mockRepository.getItems()).thenReturn(mockItems)

        // Call the ViewModel method
        viewModel.loadItems()

        // Observe the LiveData items
        val observer = Observer<List<FetchItems>> { items ->
            assertEquals(0, items.size)
        }

        viewModel.items.observeForever(observer)
    }

    @Test
    fun `test ViewModel retrieves multiple valid items correctly`() = testDispatcher.runBlockingTest {
        // Mock data with multiple valid items
        val mockItems = listOf(
            FetchItems(1, 1, "Item 1"),
            FetchItems(2, 1, "Item 2"),
            FetchItems(3, 2, "Item 3")
        )

        // Stubbing the repository's getItems method to return the mock data
        `when`(mockRepository.getItems()).thenReturn(mockItems)

        // Call the ViewModel method
        viewModel.loadItems()

        // Observe the LiveData items
        val observer = Observer<List<FetchItems>> { items ->
            assertEquals(3, items.size)
            assertEquals(1, items[0].id)
            assertEquals(2, items[1].id)
            assertEquals(3, items[2].id)
        }

        viewModel.items.observeForever(observer)
    }

    @Test
    fun `test ViewModel filters out invalid items`() = testDispatcher.runBlockingTest {
        // Mock data with mixed valid and invalid items
        val mockItems = listOf(
            FetchItems(1, 1, "Item 1"),
            FetchItems(2, 1, ""),
            FetchItems(3, 2, "Item 3"),
            FetchItems(4, 3, null)
        )

        // Stubbing the repository's getItems method to return the mock data
        `when`(mockRepository.getItems()).thenReturn(mockItems)

        // Call the ViewModel method
        viewModel.loadItems()

        // Observe the LiveData items
        val observer = Observer<List<FetchItems>> { items ->
            assertEquals(2, items.size) // Should filter out the invalid ones
            assertTrue(items.all { it.name!!.isNotBlank() }) // Ensure all remaining items have names
        }

        viewModel.items.observeForever(observer)
    }

    @Test
    fun `extractInt should return Int MAX_VALUE for empty string`() {
        val result = viewModel.extractInt("")
        assertEquals(Int.MAX_VALUE, result)
    }

    @Test
    fun `extractInt should return Int MAX_VALUE for non-numeric string`() {
        val result = viewModel.extractInt("Item NotANumber")
        assertEquals(Int.MAX_VALUE, result)
    }

    @Test
    fun `extractInt should return correct integer for valid input`() {
        val result = viewModel.extractInt("Item 5")
        assertEquals(5, result)
    }

    @Test
    fun `test repository retrieves items from API`() = runBlocking {
        // Assume FetchApi returns a predefined list
        val expectedItems = listOf(FetchItems(1, 1, "Item 1"))
        `when`(fetchApi.getItems()).thenReturn(expectedItems)

        val result = mockRepository.getItems()

        assertEquals(expectedItems, result)
    }


}