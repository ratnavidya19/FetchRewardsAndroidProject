package com.ratnavidyakanawade.fetchrewardsandroidproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems
import com.ratnavidyakanawade.fetchrewardsandroidproject.repository.FetchRepository
import kotlinx.coroutines.Dispatchers
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

class FetchViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Used a test coroutine dispatcher
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var mockRepository: FetchRepository

    private lateinit var viewModel: FetchViewModel

    @Before
    fun setup() {

        Dispatchers.setMain(Dispatchers.Unconfined)
        MockitoAnnotations.openMocks(this)
        viewModel = FetchViewModel(mockRepository)
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
}