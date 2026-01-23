package dotnet.sort.presentation.feature.compare

import dotnet.sort.model.SortType
import dotnet.sort.usecase.ExecuteSortUseCase
import dotnet.sort.usecase.GenerateArrayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CompareViewModelTest {
    private lateinit var viewModel: CompareViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Stubs for use cases that are not used in these basic tests
        val executeSortUseCaseStub = ExecuteSortUseCase()
        val generateArrayUseCaseStub = GenerateArrayUseCase(object : dotnet.sort.generator.ArrayGenerator {
            override fun generate(s: Int, t: dotnet.sort.generator.ArrayGeneratorType) = emptyList<Int>()
            override fun generate(s: Int, t: dotnet.sort.generator.ArrayGeneratorType, r: IntRange) = emptyList<Int>()
        })
        
        viewModel = CompareViewModel(executeSortUseCaseStub, generateArrayUseCaseStub)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `GIVEN initial state WHEN initialized THEN defaults are correct`() {
        val state = viewModel.state.value
        assertEquals(SortType.BUBBLE, state.selectedAlgorithm1)
        assertEquals(SortType.SELECTION, state.selectedAlgorithm2)
        assertEquals(30, state.arraySize)
    }

    @Test
    fun `GIVEN SelectAlgorithm1 intent WHEN dispatched THEN updates algorithm 1`() {
        viewModel.send(CompareIntent.SelectAlgorithm1(SortType.QUICK))
        assertEquals(SortType.QUICK, viewModel.state.value.selectedAlgorithm1)
    }
}
