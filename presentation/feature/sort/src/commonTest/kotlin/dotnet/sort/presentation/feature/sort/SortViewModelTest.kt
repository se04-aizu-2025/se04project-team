package dotnet.sort.presentation.feature.sort

import app.cash.turbine.test
import dotnet.sort.generator.ArrayGenerator
import dotnet.sort.generator.ArrayGeneratorType
import dotnet.sort.model.SortType
import dotnet.sort.usecase.ExecuteSortUseCase
import dotnet.sort.usecase.GenerateArrayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SortViewModelTest {

    private lateinit var viewModel: SortViewModel
    private lateinit var executeSortUseCase: ExecuteSortUseCase
    private lateinit var generateArrayUseCase: GenerateArrayUseCase

    // Fake implementation for ArrayGenerator
    private val fakeArrayGenerator = object : ArrayGenerator {
        override fun generate(size: Int, type: ArrayGeneratorType): List<Int> {
            return List(size) { it }
        }

        override fun generate(size: Int, type: ArrayGeneratorType, range: IntRange): List<Int> {
            return List(size) { it }
        }
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        executeSortUseCase = ExecuteSortUseCase()
        generateArrayUseCase = GenerateArrayUseCase(fakeArrayGenerator)
        viewModel = SortViewModel(executeSortUseCase, generateArrayUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN initial state WHEN initialized THEN has default values`() = runTest {
        val state = viewModel.state.value
        assertEquals(SortType.BUBBLE, state.algorithm)
        assertEquals(50, state.arraySize)
        assertFalse(state.isLoading)
    }

    @Test
    fun `GIVEN SelectAlgorithm intent WHEN dispatched THEN updates algorithm`() = runTest {
        viewModel.send(SortIntent.SelectAlgorithm(SortType.QUICK))
        assertEquals(SortType.QUICK, viewModel.state.value.algorithm)
    }

    @Test
    fun `GIVEN SetArraySize intent WHEN dispatched THEN updates size and regenerates array`() = runTest {
        viewModel.send(SortIntent.SetArraySize(20))
        
        val state = viewModel.state.value
        assertEquals(20, state.arraySize)
        assertEquals(20, state.currentNumbers.size)
    }

    @Test
    fun `GIVEN StartSort intent WHEN dispatched THEN updates state to sorting and completes`() = runTest {
        // Prepare
        viewModel.send(SortIntent.GenerateArray(ArrayGeneratorType.RANDOM))
        
        // Start Sort
        viewModel.send(SortIntent.StartSort)
        
        // With UnconfinedTestDispatcher, the sort execution (which is on viewModelScope)
        // should start immediately.
        // The delays in startAutoPlay are skipped by runTest?
        // Actually, with Unconfined, we rely on the fact that coroutines run eagerly.
        // We verify that the final state has a result.
        
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertTrue(state.sortResult != null)
    }
}
