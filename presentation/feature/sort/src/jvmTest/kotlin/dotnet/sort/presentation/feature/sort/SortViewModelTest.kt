package dotnet.sort.presentation.feature.sort

import dotnet.sort.domain.generator.ArrayGenerator
import dotnet.sort.domain.generator.ArrayGeneratorType
import dotnet.sort.domain.model.SortType
import dotnet.sort.domain.usecase.ExecuteSortUseCase
import dotnet.sort.domain.usecase.GenerateArrayUseCase
import dotnet.sort.domain.usecase.RecordHistoryEventUseCase
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
class SortViewModelTest {
    private lateinit var viewModel: SortViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val fakeGenerator = object : ArrayGenerator {
            override fun generate(size: Int, type: ArrayGeneratorType): List<Int> = List(size) { it }
            override fun generate(size: Int, type: ArrayGeneratorType, range: IntRange): List<Int> = List(size) { it }
        }
        val recordHistoryStub = RecordHistoryEventUseCase(object : dotnet.sort.repository.AlgorithmHistoryRepository {
            override suspend fun recordEvent(a: SortType?, e: dotnet.sort.model.HistoryEventType, m: String?) = Unit
            override fun observeRecentEvents(limit: Int) = kotlinx.coroutines.flow.flowOf<List<dotnet.sort.model.AlgorithmHistoryEntry>>(emptyList())
        })
        
        viewModel = SortViewModel(
            ExecuteSortUseCase(),
            GenerateArrayUseCase(fakeGenerator),
            recordHistoryStub
        )
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `initial state is correct`() {
        val state = viewModel.state.value
        assertEquals(SortType.BUBBLE, state.algorithm)
        assertEquals(50, state.arraySize)
    }

    @Test
    fun `SelectAlgorithm intent updates state`() {
        viewModel.send(SortIntent.SelectAlgorithm(SortType.QUICK))
        assertEquals(SortType.QUICK, viewModel.state.value.algorithm)
    }
}
